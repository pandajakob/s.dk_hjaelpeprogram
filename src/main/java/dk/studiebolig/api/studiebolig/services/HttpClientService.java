package dk.studiebolig.api.studiebolig.services;
import dk.studiebolig.api.studiebolig.VOs.HTTPResponse;
import dk.studiebolig.api.studiebolig.utility.CookieParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HttpClientService {


    public CompletableFuture<HttpResponse<String>> getAsync(String uri, Map<String, List<String>> headers) throws IOException, InterruptedException, ExecutionException {
        // send get request
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();

        HttpRequest.Builder reqBuilder = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .version(HttpClient.Version.HTTP_2);


        List<String> cookieValue = headers.get("Cookie");
        if (cookieValue != null) {
            reqBuilder.header("Cookie", headers.get("Cookie").get(0));
        }


        HttpRequest request = reqBuilder.build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

    }

    public HTTPResponse get(String uri, Map<String, List<String>> headers) throws IOException, InterruptedException, ExecutionException {


        URL url = new URL(uri);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        setConRequestHeaders(conn, headers);



        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("Error get request: "+uri);
        }
        // Get the body
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(conn.getInputStream());
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
        }
        String body = sb.toString();

        // get the headers
        Map<String, List<String>> hf = conn.getHeaderFields();

        // return the headers
        return new HTTPResponse(body,hf);
    };

    public HTTPResponse post(String uri, String urlParameters, Map<String, List<String>> headers) throws IOException{
        //send post request
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );

        URL url = new URL(uri);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);

        // set the custom headers
        setConRequestHeaders(conn, headers);

        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.write(postData);
        }

        // Validate response code
        int responseCode = conn.getResponseCode();
        if (!(responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_MOVED_TEMP)) {
            System.out.println("Login failed: " + conn.getResponseCode());
            throw new RuntimeException("Error sending post request to:" + uri + " Error: " + responseCode);
        }

        // Get the body
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(conn.getInputStream());
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
        }
        String body = sb.toString();

        // get the headers
        Map<String, List<String>> hf = conn.getHeaderFields();

        // return the body + headers
        return new HTTPResponse(body, hf);
    };


    void setConRequestHeaders(HttpURLConnection conn, Map<String, List<String>> headers) {
        if (!headers.isEmpty()) {
            // set the headers
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                List<String> headerValues = entry.getValue();
                String key = entry.getKey();
                boolean first = true;
                for (String val : headerValues) {
                    if (first) {
                        conn.setRequestProperty(key, val);
                        first = false;
                    } else {
                        conn.addRequestProperty(key, val);
                    }
                }

            }
        }

    }
}




import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpClientService {
    HTTPResponse get(String uri, Map<String, List<String>> headers) throws IOException {
        // send get request
        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();


        if (!headers.isEmpty()) {
            // set the headers
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                List<String> headerValues = entry.getValue();
                String key = entry.getKey();
                boolean first = true;
                for (String val : headerValues) {
                    if (first) {
                        con.setRequestProperty(key, val);
                        first = false;
                    } else {
                        con.addRequestProperty(key, val);
                    }
                }
            }
        }

        // get the headers
        Map<String, List<String>> hf = con.getHeaderFields();

        // return the headers
        return new HTTPResponse("body", hf);
    };

    HTTPResponse post(String uri, String urlParameters, Map<String, List<String>> headers) throws IOException{
        //send post request
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );

        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);
        con.setRequestProperty("Origin", "https://mit.s.dk");
        con.setRequestProperty("Referer", "https://mit.s.dk/studiebolig/login/");
        con.setRequestProperty("charset", "utf-8");
        con.setInstanceFollowRedirects(false);
        if (!headers.isEmpty()) {
            // set the headers
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                List<String> headerValues = entry.getValue();
                String key = entry.getKey();
                boolean first = true;
                for (String val : headerValues) {
                    if (first) {
                        con.setRequestProperty(key, val);
                        first = false;
                    } else {
                        con.addRequestProperty(key, val);
                    }
                }

            }
        }

        try (DataOutputStream dos = new DataOutputStream(con.getOutputStream())) {
            dos.write(postData);
        }

        int responseCode = con.getResponseCode();

        if (!(responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_MOVED_TEMP)) {
            System.out.println("Login failed: " + con.getResponseCode());
        }
        // get the headers
        Map<String, List<String>> hf = con.getHeaderFields();

        // return the body + headers
        return new HTTPResponse("body", hf);
    };

    String getValueFromCookieString(String cookieString) {
        return cookieString.substring(0, cookieString.indexOf(";")).substring(cookieString.indexOf("=")+1);
    }
}



class HTTPResponse {
    Map<String, List<String>> headers;
    String body;

    HTTPResponse(String body, Map<String, List<String>> headers) {
        this.body = body;
        this.headers = headers;
    }
}




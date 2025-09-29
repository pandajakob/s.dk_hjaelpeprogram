package dk.studiebolig.api.studiebolig;

import dk.studiebolig.api.studiebolig.VOs.HTTPResponse;
import dk.studiebolig.api.studiebolig.services.HttpClientService;

import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class HttpClientServiceMock extends HttpClientService implements TestSettings{
    @Override
    public CompletableFuture<HttpResponse<String>> getAsync(String uri, Map<String, List<String>> headers) {
        CompletableFuture<HttpResponse<String>> future = new CompletableFuture<>();

        if (uri.contains("https://mit.s.dk/studiebolig/building/5")) {
            HttpResponse<String> response = new HttpResponse<String>() {
                @Override
                public int statusCode() {
                    return 200;
                }

                @Override
                public HttpRequest request() {
                    return null;
                }

                @Override
                public Optional<HttpResponse<String>> previousResponse() {
                    return Optional.empty();
                }

                @Override
                public HttpHeaders headers() {
                    return null;
                }

                @Override
                public String body() {
                    return "\"\\\"<html>\\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy1:  B&nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy2:  B&nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy3:  A&nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy4:  A&nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy5:  A&nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy6:  C&nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy7:  &nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy8:  &nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy9:  &nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"</html>\\\"\"";
                }

                @Override
                public Optional<SSLSession> sslSession() {
                    return Optional.empty();
                }

                @Override
                public URI uri() {
                    return null;
                }

                @Override
                public HttpClient.Version version() {
                    return null;
                }
            };
            future.complete(response);
        } else if(uri.contains("https://mit.s.dk/studiebolig/building/16")) {
            HttpResponse<String> response = new HttpResponse<String>() {
                @Override
                public int statusCode() {
                    return 200;
                }

                @Override
                public HttpRequest request() {
                    return null;
                }

                @Override
                public Optional<HttpResponse<String>> previousResponse() {
                    return Optional.empty();
                }

                @Override
                public HttpHeaders headers() {
                    return null;
                }

                @Override
                public String body() {
                    return "\"\\\"<html>\\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy1:  B&nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy2:  B&nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy3:  B&nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy4:  A&nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy5:  A&nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy6:  C&nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy7:  &nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy8:  &nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"tenancy9:  &nbsp;<i class=\\\\\\\"material-icons\\\\\\\">info_outline</i> \\\" +\\n\" +\n" +
                            "                \"                    \\\"</html>\\\"\"";
                }

                @Override
                public Optional<SSLSession> sslSession() {
                    return Optional.empty();
                }

                @Override
                public URI uri() {
                    return null;
                }

                @Override
                public HttpClient.Version version() {
                    return null;
                }
            };
            future.complete(response);
        }

        return future;


    }

    @Override
    public HTTPResponse get(String uri, Map<String, List<String>> headers) throws IOException {
        Map<String, List<String>> responseHeaders = new HashMap<>();
        String body = "";
        if ("https://mit.s.dk/studiebolig/login/".equals(uri)) {
            responseHeaders.put("Set-Cookie", Arrays.asList(new String[] {"csrftoken="+ csrftoken+";"}));
        } else if ("https://mit.s.dk/api/applicant/".equals(uri)) {
            body="{\"results\":[{\"pk\":"+user.applicant_pk+",\"user\":{\"pk\":"+user.pk+",\"username\":\""+user.username+"\",\"first_name\":\""+user.first_name+"\",\"last_name\":\""+user.last_name+"\",\"applicant_pk\":"+user.applicant_pk+",\"email\":\""+user.email+"\"}}]}";
        } else if (uri.contains("https://mit.s.dk/api/building/?has_application_for="+user.applicant_pk)) {
            System.out.println("getting user applications");
            body = "{ \n" +
                    "    \"count\": 23,\n" +
                    "    \"next\": null,\n" +
                    "    \"previous\": null,\n" +
                    "    \"results\": [\n" +
                    "        {\n" +
                    "            \"pk\": 5,\n" +
                    "            \"latitude\": \"55.68870000\",\n" +
                    "            \"longitude\": \"12.54672800\",\n" +
                    "            \"name\": \"Ågården\",\n" +
                    "            \"desc_address\": \"Kapelvej 52-56\",\n" +
                    "            \"municipality\": \"København\",\n" +
                    "            \"parent\": \"https://mit.s.dk/api/application_target/1/\",\n" +
                    "            \"has_one_room_tenancy\": false\n" +
                    "        }," +
                    "           {\n" +
                    "            \"pk\": 16,\n" +
                    "            \"latitude\": \"55.67637600\",\n" +
                    "            \"longitude\": \"12.59976800\",\n" +
                    "            \"name\": \"Bodenhoffs Plads\",\n" +
                    "            \"desc_address\": \"Bodenhoffs Plads\",\n" +
                    "            \"municipality\": \"København\",\n" +
                    "            \"parent\": \"https://mit.s.dk/api/application_target/1/\",\n" +
                    "            \"has_one_room_tenancy\": false\n" +
                    "        }" +
                    "      ]" +
                    "   }";



        }

        return new HTTPResponse(body, responseHeaders);
    }

    @Override
    public HTTPResponse post(String uri, String urlParameters, Map<String, List<String>> headers) throws IOException {
        Map<String, List<String>> responseHeaders = new HashMap<>();
        String body = "";
        if ("https://mit.s.dk/studiebolig/login/".equals(uri)) {
            if (urlParameters.contains(username) && urlParameters.contains(password)) {
                responseHeaders.put("Set-Cookie", List.of("csrftoken="+csrftoken+";", "sessionid="+sessionid+";"));
            }
        }
        return new HTTPResponse(body, responseHeaders);
    }
}
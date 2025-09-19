package dk.studiebolig.api.studiebolig;

import dk.studiebolig.api.studiebolig.VOs.HTTPResponse;
import dk.studiebolig.api.studiebolig.services.HttpClientService;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientServiceMock extends HttpClientService implements TestSettings{


    @Override
    HTTPResponse get(String uri, Map<String, List<String>> headers) throws IOException {
        Map<String, List<String>> responseHeaders = new HashMap<>();
        String body = "";
        if ("https://mit.s.dk/studiebolig/login/".equals(uri)) {
            responseHeaders.put("Set-Cookie", Arrays.asList(new String[] {"csrftoken="+ csrftoken+";"}));
        } else if ("https://mit.s.dk/api/applicant/".equals(uri)) {

            body="{\"results\":[{\"pk\":"+user.getApplicant_pk()+",\"user\":{\"pk\":"+user.getPk()+",\"username\":\""+user.getUsername()+"\",\"first_name\":\""+user.getFirst_name()+"\",\"last_name\":\""+user.getLast_name()+"\",\"applicant_pk\":"+user.getApplicant_pk()+",\"email\":\""+user.getEmail()+"\"}}]}";
        } else if (uri.contains("https://mit.s.dk/api/building/?has_application_for="+user.getApplicant_pk())) {
            System.out.println("getting user applications");
            body = "{\n" +
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
    HTTPResponse post(String uri, String urlParameters, Map<String, List<String>> headers) throws IOException {
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
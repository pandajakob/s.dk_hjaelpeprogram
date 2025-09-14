import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientServiceMock extends HttpClientService  implements TestSettings{


    @Override
    HTTPResponse get(String uri, Map<String, List<String>> headers) throws IOException {
        Map<String, List<String>> responseHeaders = new HashMap<>();
        String body = "";
        if ("https://mit.s.dk/studiebolig/login/".equals(uri)) {
            responseHeaders.put("Set-Cookie", Arrays.asList(new String[] {"csrftoken="+ csrftoken+";"}));
        } else if ("https://mit.s.dk/api/applicant/".equals(uri)) {

            body="{\"results\":[{\"pk\":"+user.getApplicant_pk()+",\"user\":{\"pk\":"+user.getPk()+",\"username\":\""+user.getUsername()+"\",\"first_name\":\""+user.getFirst_name()+"\",\"last_name\":\""+user.getLast_name()+"\",\"applicant_pk\":"+user.getApplicant_pk()+",\"email\":\""+user.getEmail()+"\"}}]}";
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
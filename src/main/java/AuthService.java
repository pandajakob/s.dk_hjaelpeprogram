import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class AuthService {
    private String sessionID;
    private String csrftoken;
    private HttpClientService client = new HttpClientService();

    public String getCsrftoken() {
        return csrftoken;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        if (csrftoken.length() == 64) {
            this.sessionID = sessionID;
        } else {
            System.out.println("invalid session_id");
        }
    }

    public void setCsrftoken(String csrftoken) {
        if (csrftoken.length() == 64) {
            this.csrftoken = csrftoken;
        } else {
            System.out.println("invalid csrf token");
        }
    }

    public void requestCsrfToken() throws IOException {
        HTTPResponse res = client.get("https://mit.s.dk/studiebolig/login/", new HashMap<String,List<String>>());
        List<String> cookies = res.headers.get("Set-Cookie");

        if (!res.headers.get("Set-Cookie").isEmpty()) {
            for (String cookie : cookies ) {
                if (cookie.contains("csrftoken")) {
                    setCsrftoken(client.getValueFromCookieString(cookie));
                    break;
                }
            }
        };

        /*
        for (Map.Entry<String, List<String>> entry : res.headers.entrySet()) { {
            List<String> headerValues = entry.getValue();
            String key = entry.getKey();
            System.out.println(key+ ": ");
            for (String val : headerValues) {
                System.out.println(val);
            }
            }
        }
         */

    };

    public void login(String username, String password) throws IOException {
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Cookie", Arrays.asList(new String[] {"csrftoken="+csrftoken}));
        headers.put("Origin", Arrays.asList(new String[] {"https://mit.s.dk"}));
        headers.put("Referer", Arrays.asList(new String[] {"https://mit.s.dk/studiebolig/login/"}));

        String urlParameters = "csrfmiddlewaretoken="+csrftoken+"&username="+username+"&password="+password;

        HTTPResponse res = client.post("https://mit.s.dk/studiebolig/login/", urlParameters, headers);
        List<String> cookies = res.headers.get("Set-Cookie");

        if (!res.headers.get("Set-Cookie").isEmpty()) {
            for (String cookie : cookies ) {
                if (cookie.contains("sessionid")) {
                    setSessionID(client.getValueFromCookieString(cookie));
                    break;
                }
            }
        } else {
            System.out.println("no session cookie");
        };

    }







}

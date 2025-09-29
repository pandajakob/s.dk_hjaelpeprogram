package dk.studiebolig.api.studiebolig.services;

import dk.studiebolig.api.studiebolig.utility.CookieParser;
import dk.studiebolig.api.studiebolig.VOs.HTTPResponse;
import dk.studiebolig.api.studiebolig.VOs.Session;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class AuthService {
    private final HttpClientService httpClient;

    public AuthService(HttpClientService httpClient) {
        this.httpClient = httpClient;

    }
    private String requestCsrfToken() throws IOException, InterruptedException, ExecutionException {
        HTTPResponse res = httpClient.get("https://mit.s.dk/studiebolig/login/", new HashMap<String,List<String>>());

        CookieParser cp = new CookieParser();
        Map<String, String> cookies = cp.getAllResponseCookiesFromHeaders(res.headers);

        if (cookies.containsKey("csrftoken")) { return cookies.get("csrftoken"); }

        throw new RuntimeException("Couldn't get csrf token");
    };

    /**
     * Calls the api endpoint for logging in
     * @param username
     * @param password
     * @return Session object with CSRF token and Session token;
     * @throws IOException
     */
    public Session login(String username, String password) throws IOException, InterruptedException, ExecutionException {
        String csrftoken = requestCsrfToken();
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Cookie", Arrays.asList(new String[] {"csrftoken="+csrftoken}));
        headers.put("Origin", Arrays.asList(new String[] {"https://mit.s.dk"}));
        headers.put("Referer", Arrays.asList(new String[] {"https://mit.s.dk/studiebolig/login/"}));
        headers.put("Content-Type", Arrays.asList(new String[] {"application/x-www-form-urlencoded"}));

        String urlParameters = "csrfmiddlewaretoken="+csrftoken+"&username="+username+"&password="+password;

        HTTPResponse res = httpClient.post("https://mit.s.dk/studiebolig/login/", urlParameters, headers);

        CookieParser cp = new CookieParser();
        Map<String, String> cookies = cp.getAllResponseCookiesFromHeaders(res.headers);

        if (cookies.containsKey("sessionid")) {
            String sessionId = cookies.get("sessionid");
            return new Session(csrftoken, sessionId);
        }


        throw new RuntimeException("Error logging in");
    }


}



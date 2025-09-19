package dk.studiebolig.api.studiebolig.VOs;


import java.util.List;
import java.util.Map;

public class HTTPResponse {
    public final Map<String, List<String>> headers;
    public final String body;

    public HTTPResponse(String body, Map<String, List<String>> headers) {
        this.body = body;
        this.headers = headers;
    }
}


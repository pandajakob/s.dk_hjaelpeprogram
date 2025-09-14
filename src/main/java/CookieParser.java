import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookieParser {

    String getValueFromCookieString(String cookieString) {
        String cookieValue = cookieString.substring(0, cookieString.indexOf(";")).substring(cookieString.indexOf("=")+1);
        return cookieValue;
    }

    Map<String, String> getAllResponseCookiesFromHeaders(Map<String, List<String>> headers) {
        Map<String, String> cookies = new HashMap<>();

        List<String> responseCookies = headers.get("Set-Cookie");
        
        if (responseCookies == null) {
            throw new RuntimeException("No response cookies found");
        }

        for (String cookieString : responseCookies ) {
            System.out.println(cookieString);

            cookies.putAll(getCookieFromCookieString(cookieString));
        }

        return cookies;
    };

    Map<String,String> getCookieFromCookieString(String cookieString) {
        String cookieValue = cookieString.substring(0, cookieString.indexOf(";")).substring(cookieString.indexOf("=")+1);
        String cookieName = cookieString.substring(0, cookieString.indexOf('='));
        Map<String, String> cookie = new HashMap<>();
        cookie.put(cookieName,cookieValue);
        return cookie;
    }
}
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CookieParser
 */
class CookieParserTest {
    CookieParser cp = new CookieParser();

    @Test
    void testGetValueFromCookieString() {
        String cookie = cp.getValueFromCookieString("username=JohnDoe; expires=Thu, 18 Dec 2013 12:00:00 UTC");
        assertEquals("JohnDoe", cookie);
    }

    @Test
    void testGetAllResponseCookiesFromHeaders() {
        Map<String,List<String>> headers = new HashMap<>();
        headers.put("Set-Cookie", Arrays.asList(new String[] {"cookie1=value1;", "cookie2=value2;"}));

        Map<String,String> cookies = cp.getAllResponseCookiesFromHeaders(headers);

        Map<String,String> expectedCookies = new HashMap<>();
        expectedCookies.put("cookie1", "value1");
        expectedCookies.put("cookie2", "value2");

        assertEquals(expectedCookies, cookies);
    }


    @Test void testGetCookieFromCookieString() {
        String cookieString = "cookie1=value1;";
        Map<String,String> cookie = cp.getCookieFromCookieString(cookieString);

        assertEquals("value1", cookie.get("cookie1"));
    }
}
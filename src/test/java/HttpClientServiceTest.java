import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientServiceTest {
    HttpClientService client = new HttpClientService();

    @Test
    void testGetCookieFromCookieString() {
        String cookie = client.getValueFromCookieString("username=JohnDoe; expires=Thu, 18 Dec 2013 12:00:00 UTC");
        assertEquals("JohnDoe", cookie);
    }
}
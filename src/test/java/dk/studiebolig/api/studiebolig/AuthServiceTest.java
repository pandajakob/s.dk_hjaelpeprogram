package dk.studiebolig.api.studiebolig;

import dk.studiebolig.api.studiebolig.VOs.Session;
import dk.studiebolig.api.studiebolig.services.AuthService;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest  implements TestSettings{

    AuthService auth = new AuthService(new HttpClientServiceMock());

    @Test
    void testLogin() throws IOException {
        Session session = auth.login(username,password);

        assertEquals(csrftoken, session.getCsrftoken());
        assertEquals(sessionid, session.getSessionId());

    }

    @Test
    void testLoginWithWrongPassword() throws IOException {
        assertThrows(RuntimeException.class, ()->auth.login("",""));
    }
}
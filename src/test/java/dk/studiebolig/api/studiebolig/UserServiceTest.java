package dk.studiebolig.api.studiebolig;

import dk.studiebolig.api.studiebolig.VOs.Session;
import dk.studiebolig.api.studiebolig.VOs.User;
import dk.studiebolig.api.studiebolig.services.UserService;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest implements TestSettings {
    UserService userService = new UserService(new Session("", ""), new HttpClientServiceMock());

    @Test
    void testRetrieveUser() throws IOException, InterruptedException {
        User testUser = userService.retrieveUser();

        assertEquals(user.email, testUser.email);
        assertEquals(user.username, testUser.username);
        assertEquals(user.getClass(), testUser.getClass());
        assertEquals(user.applicant_pk, testUser.applicant_pk);
        assertEquals(user.first_name, testUser.first_name);
        assertEquals(user.last_name, testUser.last_name);
    }



}
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest implements TestSettings {
    UserService userService = new UserService(new Session("", ""), new HttpClientServiceMock());

    @Test
    void testRetrieveUser() throws IOException {
        User testUser = userService.retrieveUser();

        assertEquals(user.getEmail(), testUser.getEmail());
        assertEquals(user.getUsername(), testUser.getUsername());
        assertEquals(user.getClass(), testUser.getClass());
        assertEquals(user.getApplicant_pk(), testUser.getApplicant_pk());
        assertEquals(user.getFirst_name(), testUser.getFirst_name());
        assertEquals(user.getLast_name(), testUser.getLast_name());
    }



}
package dk.studiebolig.api.studiebolig;

import dk.studiebolig.api.studiebolig.VOs.Session;

public interface TestSettings {
    final public String sessionid = "5678";
    final public String csrftoken = "1234";
    final public String username = "username";
    final public String password = "testpassword123";
    final public User user = new User(
            new Session(csrftoken, sessionid),
            username,
            123456789,
            username,
            987654321,
            "test",
            "user");
}

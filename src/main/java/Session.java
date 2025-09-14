public class Session {
    private final String csrftoken;
    private final String sessionId;

    public String getCsrftoken() { return this.csrftoken; }
    public String getSessionId() { return this.sessionId; }

    Session(String csrftoken, String sessionId) {
        this.csrftoken = csrftoken;
        this.sessionId = sessionId;
    }
}

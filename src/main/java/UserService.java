import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    Session session;
    HttpClientService httpClient;

    UserService(Session session, HttpClientService httpClient) {
        this.session = session;
        this.httpClient = httpClient;
    }

    public User retrieveUser() throws IOException {

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Cookie", Arrays.asList(new String[] {"csrftoken="+session.getCsrftoken()+";"+"sessionid="+session.getSessionId()+";"}));

        HTTPResponse res = httpClient.get("https://mit.s.dk/api/applicant/", headers);


        ObjectMapper objectMapper = new ObjectMapper();

        ResultContainer resultContainer = objectMapper.readValue(res.body, new TypeReference<ResultContainer>() {});

        ApiUser user = resultContainer.results[0].user;
        return new User(session, user.username, user.pk, user.email, user.applicant_pk, user.first_name, user.last_name);
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ResultContainer {
    public Applicant[] results;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Applicant {
    public ApiUser user;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ApiUser {
    public int pk;
    public int applicant_pk;
    public String username;
    public String email;
    public String first_name;
    public String last_name;
}

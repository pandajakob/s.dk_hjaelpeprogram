package dk.studiebolig.api.studiebolig.services;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.studiebolig.api.studiebolig.DTOs.ApiUser;
import dk.studiebolig.api.studiebolig.DTOs.ResultContainer;
import dk.studiebolig.api.studiebolig.VOs.HTTPResponse;
import dk.studiebolig.api.studiebolig.VOs.Session;
import dk.studiebolig.api.studiebolig.VOs.User;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserService {
    private final Session session;
    private final HttpClientService httpClient;

    public UserService(Session session, HttpClientService httpClient) {
        this.session = session;
        this.httpClient = httpClient;
    }

    public User retrieveUser() throws IOException, InterruptedException, ExecutionException {

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Cookie", Arrays.asList(new String[] {"csrftoken="+session.getCsrftoken()+";"+"sessionid="+session.getSessionId()+";"}));
        headers.put("Accept", Arrays.asList(new String[] {"application/json"}));
        
        HTTPResponse res = httpClient.get("https://mit.s.dk/api/applicant/", headers);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultContainer resultContainer = objectMapper.readValue(res.body, new TypeReference<ResultContainer>() {});

        ApiUser user = resultContainer.results[0].user;
        return new User(session, user.username, user.pk, user.email, user.applicant_pk, user.first_name, user.last_name);
    }
}


package dk.studiebolig.api.studiebolig.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiUser {
    public int pk;
    public int applicant_pk;
    public String username;
    public String email;
    public String first_name;
    public String last_name;
}

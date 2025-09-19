package dk.studiebolig.api.studiebolig.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Applicant {
    public ApiUser user;
}


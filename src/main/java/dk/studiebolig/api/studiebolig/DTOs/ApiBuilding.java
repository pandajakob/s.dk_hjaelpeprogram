package dk.studiebolig.api.studiebolig.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiBuilding {
    public int pk;
    public String latitude;
    public String longitude;
    public String name;
    public String desc_address;
    public String municipality;
}
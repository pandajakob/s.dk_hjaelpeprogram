package dk.studiebolig.api.studiebolig.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiBuildingList {
    public int count;
    public String next;
    public String previous;
    public ApiBuilding[] results;
}

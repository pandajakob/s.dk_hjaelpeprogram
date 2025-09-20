package dk.studiebolig.api.studiebolig.DTOs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.studiebolig.api.studiebolig.Ranking;
import dk.studiebolig.api.studiebolig.VOs.Building;
import dk.studiebolig.api.studiebolig.VOs.HTTPResponse;
import dk.studiebolig.api.studiebolig.VOs.Session;
import dk.studiebolig.api.studiebolig.VOs.User;
import dk.studiebolig.api.studiebolig.services.HttpClientService;
import dk.studiebolig.api.studiebolig.utility.HTMLBuildingRankingParser;

import java.io.IOException;
import java.util.*;

public class ApiBuildingRepository {
    private final User user;
    public List<Building> buildings = new ArrayList<Building>();

    public ApiBuildingRepository(User user, List<Building> buildings) {
        this.user = user;
        this.buildings = buildings;
    }
}
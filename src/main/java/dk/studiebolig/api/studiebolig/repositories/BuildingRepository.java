package dk.studiebolig.api.studiebolig.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.studiebolig.api.studiebolig.*;
import dk.studiebolig.api.studiebolig.DTOs.ApiBuilding;
import dk.studiebolig.api.studiebolig.DTOs.ApiBuildingList;
import dk.studiebolig.api.studiebolig.VOs.Building;
import dk.studiebolig.api.studiebolig.VOs.HTTPResponse;
import dk.studiebolig.api.studiebolig.VOs.Session;
import dk.studiebolig.api.studiebolig.VOs.User;
import dk.studiebolig.api.studiebolig.services.HttpClientService;
import dk.studiebolig.api.studiebolig.utility.HTMLBuildingRankingParser;

import java.io.IOException;
import java.util.*;

public class BuildingRepository {
    private final Session session;
    private final HttpClientService httpClient;
    private final User user;
    private int appliedBuidlingCount;
    public List<Building> buildings = new ArrayList<Building>();

    public BuildingRepository(Session session, User user, HttpClientService httpClient) {
        this.session = session;
        this.user = user;
        this.httpClient = httpClient;
    }

    public List<Building> retrieveAllAppliedBuildings() throws IOException {
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Cookie", Arrays.asList(new String[] {"csrftoken="+session.getCsrftoken()+";"+"sessionid="+session.getSessionId()+";"}));

        int page = 1;
        boolean next = true;

        while(next) {
            System.out.println("loading page " + page +"...");
            HTTPResponse res = httpClient.get("https://mit.s.dk/api/building/?has_application_for="+ user.applicant_pk + "&page=" + page, headers);

            if (res.body.length() < 1) { throw new RuntimeException("no buildings found"); }
            ObjectMapper objectMapper = new ObjectMapper();
            ApiBuildingList apiBuildingList = objectMapper.readValue(res.body, new TypeReference<ApiBuildingList>() {});

            for (ApiBuilding b : apiBuildingList.results) {
                System.out.println("getting building html...");
                String html = httpClient.get("https://mit.s.dk/studiebolig/building/" + b.pk, headers).body;
                if (html != null) {

                    HTMLBuildingRankingParser htmlBuildingRankingParser = new HTMLBuildingRankingParser();
                    Ranking ranking = htmlBuildingRankingParser.extractRanking(html);

                    buildings.add(new Building(b.pk, b.latitude, b.longitude, b.name, b.desc_address, b.municipality, ranking));
                }
            }

            if (apiBuildingList.next == null) { next = false; System.out.println("done");}
            page++;
        }
        return buildings;
    }
    public void sortBuildingsByRankings()  {
        buildings.sort( Comparator.comparingInt( d-> -d.getRanking('A')));
    }
}



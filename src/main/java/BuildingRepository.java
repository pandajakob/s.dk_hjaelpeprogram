import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.IIOException;
import java.io.IOException;
import java.lang.classfile.Attribute;
import java.util.*;

public class BuildingRepository {
    private final Session session;
    private final HttpClientService httpClient;
    private final User user;
    private int appliedBuidlingCount;
    List<Building> buildings = new ArrayList<Building>();

    BuildingRepository(Session session, User user, HttpClientService httpClient) {
        this.session = session;
        this.user = user;
        this.httpClient = httpClient;
    }

    List<Building> retrieveAllAppliedBuildings() throws IOException {
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Cookie", Arrays.asList(new String[] {"csrftoken="+session.getCsrftoken()+";"+"sessionid="+session.getSessionId()+";"}));

        int page = 1;
        boolean next = true;

        while(next) {
            HTTPResponse res = httpClient.get("https://mit.s.dk/api/building/?has_application_for="+ user.getApplicant_pk() + "&page=" + page, headers);

            if (res.body.length() < 1) {
                throw new RuntimeException("no buildings found");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            ApiBuildingList apiBuildingList = objectMapper.readValue(res.body, new TypeReference<ApiBuildingList>() {});

            for (ApiBuilding b : apiBuildingList.results) {

                String html = httpClient.get("https://mit.s.dk/studiebolig/building/" + b.pk, headers).body;
                if (html != null) {
                    HTMLBuildingRankingParser htmlBuildingRankingParser = new HTMLBuildingRankingParser();
                    Ranking ranking = htmlBuildingRankingParser.extractRanking(html);

                    buildings.add(new Building(b.pk, b.latitude, b.longitude, b.name, b.desc_address, b.municipality, ranking));
                }
            }

            if (apiBuildingList.next == null) { next = false; }
            page++;
        }

        return buildings;
    }
    public void sortBuildingsByRankings()  {
        buildings.sort( Comparator.comparingInt( d-> -d.getRanking('A')));
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ApiBuildingList {
    public int count;
    public String next;
    public String previous;
    public ApiBuilding[] results;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ApiBuilding {
    public int pk;
    public String latitude;
    public String longitude;
    public String name;
    public String desc_address;
    public String municipality;
}
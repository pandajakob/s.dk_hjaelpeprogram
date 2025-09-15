import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildingRepositoryTest implements TestSettings {
    HttpClientService httpClient = new HttpClientServiceMock();
    BuildingRepository br = new BuildingRepository(new Session(csrftoken,sessionid),user, httpClient);
    @Test
    void testRetrieveAllAppliedBuildings() throws IOException {
        List<Building> buildings = br.retrieveAllAppliedBuildings();
        assertEquals(5, buildings.getFirst().getPk());
    }

    @Test void testSortBuildingsByRankings() {

    }
}
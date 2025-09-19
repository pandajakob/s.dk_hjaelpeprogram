package dk.studiebolig.api.studiebolig;

import dk.studiebolig.api.studiebolig.VOs.Session;
import dk.studiebolig.api.studiebolig.repositories.services.BuildingRepository;
import dk.studiebolig.api.studiebolig.services.HttpClientService;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.List;

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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User u = new User("test", "testpassword");

    @Test
    void getPk() {
        u.setPk(123);
        assertEquals(123,u.getPk());
    }
    @Test
    void sortBuildings() {
        Building[] buildings = {new Building(), new Building(), new Building(), new Building()};

        buildings[0].setPk(0);
        buildings[1].setPk(1);
        buildings[2].setPk(2);
        buildings[3].setPk(3);

        buildings[0].ranking.incrementA();
        buildings[1].ranking.incrementA();
        buildings[1].ranking.incrementA();
        buildings[1].ranking.incrementA();
        buildings[1].ranking.incrementA();
        buildings[2].ranking.incrementB();
        buildings[2].ranking.incrementB();
        buildings[2].ranking.incrementC();
        buildings[2].ranking.incrementC();
        buildings[3].ranking.incrementA();
        buildings[3].ranking.incrementA();

        u.setBuildings(buildings);
        u.sortBuildings();

        assertEquals(1,u.getBuildings()[0].getPk());
        assertEquals(3,u.getBuildings()[1].getPk());
        assertEquals(0,u.getBuildings()[2].getPk());
        assertEquals(2,u.getBuildings()[3].getPk());
    }

}
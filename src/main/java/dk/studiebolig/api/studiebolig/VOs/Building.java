package dk.studiebolig.api.studiebolig.VOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dk.studiebolig.api.studiebolig.Ranking;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Building {
    final public int pk;
    final public String latitude;
    final public String longitude;
    final public String name;
    final public String desc_address;
    final public String municipality;
    final public Ranking ranking;

    public Building(int pk, String latitude, String longitude, String name, String desc_address, String municipality, Ranking ranking) {
        this.pk = pk;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.desc_address = desc_address;
        this.municipality = municipality;
        this.ranking = ranking;
    }

    public int getRanking(char rank) {
        if (rank == 'A') {
            return ranking.getA();
        } else if (rank == 'B') {
            return ranking.getB();
        } else if (rank == 'C') {
            return ranking.getC();
        }
        return 0;
    }

    public String getCoordinateString() {
        return this.latitude + "," + this.longitude;
    };
    public String getGoogleMapsUrl() {
        return "https://www.google.com/maps?q="+this.getCoordinateString();
    };
    public String getLinkToBuilding() {
        return "https://mit.s.dk/studiebolig/building/" + this.pk;
    }

}


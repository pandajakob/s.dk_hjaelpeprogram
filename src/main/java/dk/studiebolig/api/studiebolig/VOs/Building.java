package dk.studiebolig.api.studiebolig.VOs;

import dk.studiebolig.api.studiebolig.Ranking;

public class Building {
    final private int pk;
    final private String latitude;
    final private String longitude;
    final private String name;
    final private String desc_address;
    final private String municipality;
    final private Ranking ranking;


    public int getPk() { return this.pk; }
    public String getLatitude() { return this.latitude; }
    public String getLongitude() { return this.longitude; }
    public String getMunicipality() { return this.municipality; }
    public String getName() { return this.name; }
    public String getDesc_address() { return this.desc_address; }

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
        return getLatitude() + "," + getLongitude();
    };
    public String getGoogleMapsUrl() {
        return "https://www.google.com/maps?q="+getLatitude() + "," + getLongitude();
    };
    public String getLinkToBuilding() {
        return "https://mit.s.dk/studiebolig/building/" + getPk();
    }

}


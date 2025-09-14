import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

class Building  {
    final private int pk;
    final private String latitude;
    final private String longitude;
    final private String name;
    final private String desc_address;
    final private String municipality;
    final private Ranking ranking = new Ranking();


    public int getPk() {
        return pk;
    }


    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }
    public String getDesc_address() {
        return desc_address;
    }

    Building(int pk, String latitude, String longitude, String name, String desc_address, String municipality) {
        this.pk = pk;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.desc_address = desc_address;
        this.municipality = municipality;
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

    public void extractRankings(String CSRFToken, String sessionID) throws IOException {
        URL url = new URL("https://mit.s.dk/studiebolig/building/" + pk);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Cookie", "csrftoken=" + CSRFToken + ";" + "sessionid=" + sessionID);

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(con.getInputStream());
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }

            String[] splitString = sb.toString().split("(;|\\s|<>|/)+");

            for (String split : splitString) {

                if (split.equals("B&nbsp")) {
                    ranking.incrementB();
                } else if (split.equals("A&nbsp")) {
                    ranking.incrementA();
                } else if (split.equals("C&nbsp")) {
                    ranking.incrementC();
                }
            }

        }
    }
}


class Ranking {
    int A = 0;
    int B = 0;
    int C = 0;

    public void incrementA() {
        this.A++;
    }
    public void incrementB() {
        this.B++;
    }
    public void incrementC() {
        this.C++;
    }

    public int getA() {
        return A;
    }
    public int getB() {
        return B;
    }
    public int getC() {
        return C;
    }
}

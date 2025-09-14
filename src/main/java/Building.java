import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@JsonIgnoreProperties(ignoreUnknown = true)
class Building  {
    int pk;
    String latitude;
    String longitude;
    String name;
    String desc_address;
    String municipality;
    Ranking ranking = new Ranking();

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getPk() {
        return pk;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDesc_address(String desc_address) {
        this.desc_address = desc_address;
    }
    public String getDesc_address() {
        return desc_address;
    }

    public void setMunicipality(String municipality) {
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

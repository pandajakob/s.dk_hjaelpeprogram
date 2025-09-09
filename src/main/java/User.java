import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class User {
    private int pk;
    final private String username;
    final private String password;
    private boolean loggedIn = false;
    private String CSRFToken;
    private String sessionID;
    private Building[] buildings;


    public void setPk(int pk) {
        this.pk = pk;
    }
    public boolean getLoggedIn() {
        return loggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getSessionID() {
        return sessionID;
    }

    public String getCSRFToken() {
        return CSRFToken;
    }

    public Building[] getBuildings() {
        return buildings;
    }

    public void generateCSRFToken() throws IOException {
        // get CSRF
        URL url = new URL("https://mit.s.dk/studiebolig/login/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        String CSRF = con.getHeaderField("set-Cookie");
        CSRFToken = CSRF.substring(0, CSRF.indexOf(";")).substring(CSRF.indexOf("=")+1);
    }

    public void login() throws IOException {
        String urlParameters = "csrfmiddlewaretoken="+this.CSRFToken+"&username="+this.username+"&password="+this.password ;
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int postDataLength = postData.length;
        URL url = new URL("https://mit.s.dk/studiebolig/login/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Accept", "*/*");
        con.setDoOutput(true);
        con.setRequestProperty("Origin", "https://mit.s.dk");
        con.setRequestProperty("Referer", "https://mit.s.dk/studiebolig/login/");
        con.setRequestProperty("Cookie", "csrftoken="+this.CSRFToken);
        con.setRequestProperty("charset", "utf-8");
        con.setUseCaches(false);
        con.setInstanceFollowRedirects(false);
        con.setRequestProperty("Content-Length", Integer.toString(postDataLength));



        try (DataOutputStream dos = new DataOutputStream(con.getOutputStream())) {
            dos.write(postData);
        }
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
            String sid = con.getHeaderField("Set-Cookie").split(";")[0];
            this.sessionID = sid.substring(sid.indexOf("=") + 1);
            if (sessionID != null) {
                setLoggedIn(true);
            }

        } else {
            System.out.println("Login failed: " + con.getResponseCode());
        }

    }


    public void fetchApplicantPk() throws IOException {
        URL url = new URL("https://mit.s.dk/api/applicant/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Cookie", "csrftoken=" + getCSRFToken() + ";" + "sessionid=" + getSessionID());

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(con.getInputStream());
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }

            ObjectMapper objectMapper = new ObjectMapper();

            Applicant applicant = objectMapper.readValue(String.valueOf(sb), new TypeReference<Applicant>() {});

            setPk(applicant.getResults()[0].pk);
            if (pk == 0){
                System.out.println("No Applicant found");
            }

        } else {
            System.out.println("HTTP  error: " + responseCode);
        }
    }


    public void fetchBuildings() throws IOException {
        int page = 1;
        int buildingIndex = 0;
        boolean next = true;
        while (next) {
            System.out.print("Fetching buildings for page " + page + "...\n");
            URL url = new URL("https://mit.s.dk/api/building/?has_application_for=" + this.pk + "&page=" + page + "&parent=1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Cookie", "csrftoken=" + this.getCSRFToken() + ";" + "sessionid=" + this.getSessionID());

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                Scanner scanner = new Scanner(con.getInputStream());
                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine());
                }

                ObjectMapper objectMapper = new ObjectMapper();

                BuildingList bl = objectMapper.readValue(String.valueOf(sb), new TypeReference<BuildingList>() {
                });

                if (buildings == null) {
                    buildings = new Building[bl.getCount()];
                }
                for (Building building : bl.getResults()) {
                    building.extractRankings(this.CSRFToken, this.sessionID);
                    buildings[buildingIndex] = building;
                    buildingIndex++;
                }

                if (bl.getNext() == null) {
                    next = false;
                }
                page++;


            } else {
                System.out.println("HTTP  error: " + responseCode);
            }
        }
    }
    public void sortBuildings()  {

        Arrays.sort(this.buildings, Comparator.comparingInt(d-> -d.getRanking('A')));
    }

}

@JsonIgnoreProperties(ignoreUnknown = true)
class BuildingList {
    int count;
    String next;
    Building[] results;

    int getCount() {
        return this.count;
    }
    void setCount(int count) {
        this.count = count;
    }
    Building[] getResults() {
        return this.results;
    }
    void setResults(Building[] results) {
        this.results = results;
    }

    public void setNext(String next) {
        this.next = next;
    }
    public String getNext() {
        return this.next;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Applicant {
    int count;
    ApiUser[] results;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
    public void setResults(ApiUser[] results) {
        this.results = results;
    }
    public ApiUser[] getResults() {
        return this.results;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ApiUser {
    public int pk;
}
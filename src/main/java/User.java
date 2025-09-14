public class User {
    private int pk;
    private int applicant_pk;
    private String username;
    private String email;
    private String first_name;
    private String last_name;

    public int getPk() { return pk; }
    public String getUsername() { return this.username; }
    public String getEmail() { return this.email; }
    public String getFirst_name() { return this.first_name; }
    public String getLast_name() { return this.last_name; }
    public int getApplicant_pk() { return applicant_pk; }

    User(Session session, String username, int pk, String email, int applicant_pk, String first_name, String last_name) {
        this.username = username;
        this.pk = pk;
        this.email = email;
        this.applicant_pk = applicant_pk;
        this.first_name = first_name;
        this.last_name = last_name;
    }
}
    /*


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

     */




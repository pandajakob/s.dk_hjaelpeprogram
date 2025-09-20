package dk.studiebolig.api.studiebolig.VOs;

public class User {
    public final int pk;
    public final int applicant_pk;
    public final String username;
    public final String email;
    public final String first_name;
    public final String last_name;


    public User(Session session, String username, int pk, String email, int applicant_pk, String first_name, String last_name) {
        this.username = username;
        this.pk = pk;
        this.email = email;
        this.applicant_pk = applicant_pk;
        this.first_name = first_name;
        this.last_name = last_name;
    }
    public String getFullName() {
        return this.first_name + " " + this.last_name;
    }

}
    /*


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




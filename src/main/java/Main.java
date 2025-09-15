import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpClientService httpClient = new HttpClientService();
        AuthService auth = new AuthService(httpClient);

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Enter email: ");
        String email = myObj.nextLine();  // Read user input
        System.out.print("Enter password: ");
        String password = myObj.nextLine();


        Session session = auth.login(email, password);

        System.out.println("\rSuccessfully logged in!\n");
        UserService userService = new UserService(session, httpClient);
        User user = userService.retrieveUser();
        System.out.println("Welcome " + user.getFullName() + "!\n");
        BuildingRepository buildingsList = new BuildingRepository(session,user,httpClient);

        System.out.println("Retrieving all applied buildings...");
        buildingsList.retrieveAllAppliedBuildings();

        System.out.println("Sorting buildings by 'A' Rankings...");

        buildingsList.sortBuildingsByRankings();

        System.out.println("Listing Buildings:\n");
        for (Building b : buildingsList.buildings) {
            System.out.println(b.getName() + " - " + b.getDesc_address());
            System.out.println(b.getGoogleMapsUrl());
            System.out.println("A: " +b.getRanking('A'));
            System.out.println("B: " +b.getRanking('B'));
            System.out.println("C: " +b.getRanking('C'));
        }
    }
}
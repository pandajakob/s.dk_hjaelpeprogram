package dk.studiebolig.api.studiebolig;
import java.io.IOException;
import java.util.Scanner;
import java.util.Map;

import dk.studiebolig.api.studiebolig.VOs.Building;
import dk.studiebolig.api.studiebolig.VOs.Session;
import dk.studiebolig.api.studiebolig.VOs.User;
import dk.studiebolig.api.studiebolig.repositories.BuildingRepository;
import dk.studiebolig.api.studiebolig.services.AuthService;
import dk.studiebolig.api.studiebolig.services.HttpClientService;
import dk.studiebolig.api.studiebolig.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudieboligApplication {

    public static void main(String[] args) throws IOException{

        SpringApplication.run(StudieboligApplication.class, args);
        HttpClientService httpClient = new HttpClientService();
        AuthService auth = new AuthService(httpClient);

        String email = "";
        String password = "";
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            if (envName.equals("EMAIL")) {
                email = env.get(envName);
            } else if (envName.equals("PASSWORD")) {
                password = env.get(envName);
            }

        }

        if (email.equals("") || password.equals("")) {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.print("Enter email: ");
            email = myObj.nextLine();  // Read user input
            System.out.print("Enter password: ");
            password = myObj.nextLine();
        }


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
            System.out.println(b.getLinkToBuilding());
            System.out.println(b.getGoogleMapsUrl());
            if ((b.getRanking('A')) > 0) {
                System.out.println("A: " + b.getRanking('A'));
            }
            if (b.getRanking('B')>0) {
                System.out.println("B: " + b.getRanking('B'));
            }
            if (b.getRanking('C')>0) {
                System.out.println("C: " + b.getRanking('C'));
            }
            System.out.println("");
        }

    }

}

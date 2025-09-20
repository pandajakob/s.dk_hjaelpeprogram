package dk.studiebolig.api.studiebolig.controllers;

import dk.studiebolig.api.studiebolig.VOs.Building;
import dk.studiebolig.api.studiebolig.VOs.Session;
import dk.studiebolig.api.studiebolig.VOs.User;
import dk.studiebolig.api.studiebolig.repositories.BuildingRepository;
import dk.studiebolig.api.studiebolig.services.AuthService;
import dk.studiebolig.api.studiebolig.services.HttpClientService;
import dk.studiebolig.api.studiebolig.services.UserService;
import jdk.jfr.ContentType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;


@org.springframework.stereotype.Controller
@RestController
@CrossOrigin(origins = {"https://studiebolig.jakobmichaelsen.dk", "http://127.0.0.1:5500"})
public class Controller {
    UserData userData = new UserData("user", "user");

    @GetMapping("/")
    String sendHtml() {
        System.out.println("SendHTML");
        return "index.html";
    }
    @CrossOrigin(origins = {"https://studiebolig.jakobmichaelsen.dk", "http://127.0.0.1:5500", "http://127.0.0.1:5500/index.html"})
    @RequestMapping(path = "/login")
    BuildingRepository sendData(@RequestBody UserData userData) throws IOException {
        System.out.println("Login" + userData.password);

        HttpClientService httpClient = new HttpClientService();
        AuthService auth = new AuthService(httpClient);

        Session session = auth.login(userData.username, userData.password);


        System.out.println("\rSuccessfully logged in!\n");
        UserService userService = new UserService(session, httpClient);
        User user = userService.retrieveUser();
        System.out.println("Welcome " + user.getFullName() + "!\n");
        BuildingRepository buildingsList = new BuildingRepository(session, user, httpClient);


        buildingsList.retrieveAllAppliedBuildings();
        buildingsList.sortBuildingsByRankings();

        return buildingsList;
    }

    public void run() throws IOException {
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
        BuildingRepository buildingsList = new BuildingRepository(session, user, httpClient);

        System.out.println("Retrieving all applied buildings...");
        buildingsList.retrieveAllAppliedBuildings();

        System.out.println("Sorting buildings by 'A' Rankings...");
        buildingsList.sortBuildingsByRankings();

        System.out.println("Listing Buildings:\n");
        for (Building b : buildingsList.buildings) {
            System.out.println(b.name + " - " + b.desc_address);
            System.out.println(b.getLinkToBuilding());
            System.out.println(b.getGoogleMapsUrl());
            if ((b.getRanking('A')) > 0) {
                System.out.println("A: " + b.getRanking('A'));
            }
            if (b.getRanking('B') > 0) {
                System.out.println("B: " + b.getRanking('B'));
            }
            if (b.getRanking('C') > 0) {
                System.out.println("C: " + b.getRanking('C'));
            }
            System.out.println("");
        }
    }


}

class UserData {
    public String username;
    public String password;
    UserData(String username, String password) {
        this.username = username;
        this.password =password;

    }
}

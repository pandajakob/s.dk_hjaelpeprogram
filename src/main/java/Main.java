import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter email:");
        String email = myObj.nextLine();  // Read user input
        System.out.println("Enter password:");
        String password = myObj.nextLine();

        User user = new User(email, password);
        user.generateCSRFToken();
        user.login();
        if (!user.getLoggedIn()) {
            return;
        }
        user.fetchApplicantPk();
        user.fetchBuildings();
        user.sortBuildings();

        Building[] buildings = user.getBuildings();

        for (Building building : buildings) {
            System.out.println(building.getName() + " - " + building.getDesc_address());
            System.out.println("https://www.google.com/maps?q="+building.getLatitude()+","+building.getLongitude());
            System.out.println("A: " +building.getRanking('A'));
            System.out.println("B: " +building.getRanking('B'));
            System.out.println("C: " +building.getRanking('C'));

            System.out.println("\n");
        }
    }
}

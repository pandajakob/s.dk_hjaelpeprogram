import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpClientService httpClient = new HttpClientService();
        AuthService auth = new AuthService(httpClient);

        Session session = auth.login("therealjakobmichaelsen@gmail.com", "dirryk-syntuV-gicfa3");

        System.out.println(session.getCsrftoken());
        System.out.println(session.getSessionId());

        UserService userService = new UserService(session, httpClient);
        User user = userService.retrieveUser();
    }
}

        /*
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
        */
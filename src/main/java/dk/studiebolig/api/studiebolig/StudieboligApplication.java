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
    public static void main(String[] args) {

        SpringApplication.run(StudieboligApplication.class, args);


    }


}

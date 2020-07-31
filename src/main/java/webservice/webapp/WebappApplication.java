package webservice.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import webservice.webapp.domain.Task1;
import webservice.webapp.domain.Task2;
import webservice.webapp.domain.TaskManager;

import java.io.File;
import java.util.*;

@SpringBootApplication
public class WebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebappApplication.class, args);
    }

}

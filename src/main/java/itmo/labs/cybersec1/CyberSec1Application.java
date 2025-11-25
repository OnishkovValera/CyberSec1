package itmo.labs.cybersec1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class CyberSec1Application {
    public static void main(String[] args) {
        SpringApplication.run(CyberSec1Application.class, args);
    }
}

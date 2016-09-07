package benjamin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {JmxAutoConfiguration.class})
public class BenjaminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BenjaminApplication.class, "--debug");
    }

}
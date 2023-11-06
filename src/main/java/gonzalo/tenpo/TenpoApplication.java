package gonzalo.tenpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class TenpoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenpoApplication.class, args);
    }

}

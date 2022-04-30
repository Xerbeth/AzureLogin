package co.gov.icfes.AzureLogin.Controller;

//region import
import co.gov.icfes.AzureLogin.AzureLoginApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;
//endregion import

@SpringBootApplication
@RestController
public class PingController {

    public static void main(String[] args) {
        SpringApplication.run(AzureLoginApplication.class, args);
    }

    @GetMapping("/PingApplication")
    public String PingApplication(){
        return "Servicio OK";
    }

    @ApiIgnore
    @GetMapping("/")
    public ModelAndView SwaggerHome() {
        return new ModelAndView("redirect:/swagger-ui/index.html");
    }

}

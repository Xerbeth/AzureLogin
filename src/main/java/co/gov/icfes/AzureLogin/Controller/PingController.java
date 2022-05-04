package co.gov.icfes.AzureLogin.controller;

//region import
import co.gov.icfes.AzureLogin.dto.ApiResponse;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;
//endregion import

@SpringBootApplication
@RestController
public class PingController {

    @RequestMapping(value = "/Ping/PingApplication",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<HttpStatus> PingApplication(){
        return new ApiResponse.ApiResponseBuilder<HttpStatus>(HttpStatus.OK)
                .status(true)
                .build();
    }

    @ApiIgnore
    @GetMapping("/")
    public ModelAndView SwaggerHome() {
        return new ModelAndView("redirect:/swagger-ui/index.html");
    }

}

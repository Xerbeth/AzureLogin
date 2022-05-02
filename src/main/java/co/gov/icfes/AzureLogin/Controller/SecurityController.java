package co.gov.icfes.AzureLogin.controller;

//region import
import co.gov.icfes.AzureLogin.DTO.ApiResponse;
import co.gov.icfes.AzureLogin.services.implement.ISecurityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//endregion import

@SpringBootApplication
@RestController
public class SecurityController {

    private static final Logger LOG = LogManager.getLogger(SecurityController.class);
    @Autowired
    private ISecurityService securityService;

    @GetMapping("/GetADToken")
    public ApiResponse<String> GetToken() throws Exception {
        return securityService.GetToken();
    }

}

package co.gov.icfes.AzureLogin.controller;

//region import
import co.gov.icfes.AzureLogin.dto.ApiResponse;
import co.gov.icfes.AzureLogin.services.implement.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
//endregion import

@SpringBootApplication
@RestController()
public class SecurityController {
    @Autowired
    private ISecurityService securityService;
    @RequestMapping(value = "/Security/GetTokent",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<String> GetToken() {
        return securityService.GetToken();
    }

    @RequestMapping(value = "/Security/GetTokenUsernamePassword",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<String> GetTokenUsernamePassword() throws Exception {
        securityService.GetTokenUsernamePassword();
        return new ApiResponse<String>();
    }

}

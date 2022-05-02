package co.gov.icfes.AzureLogin.controller;

//region import
import co.gov.icfes.AzureLogin.DTO.ApiResponse;
import co.gov.icfes.AzureLogin.services.implement.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//endregion import

@SpringBootApplication
@RestController
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping("/GetInformationAccount/{idAccount}")
    public ApiResponse<String> GetInformationAccount(String idAccount) throws Exception {
        return accountService.GetInformationAccount(idAccount);
    }
}

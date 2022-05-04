package co.gov.icfes.AzureLogin.controller;

//region import
import co.gov.icfes.AzureLogin.dto.ApiResponse;
import co.gov.icfes.AzureLogin.dto.ChangePasswordAccount;
import co.gov.icfes.AzureLogin.dto.UserAccount;
import co.gov.icfes.AzureLogin.services.implement.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
//endregion import

@SpringBootApplication
@RestController()
public class AccountController {
    @Autowired
    private IAccountService accountService;
    @RequestMapping(value = "/Account/GetInformationAccount",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse<UserAccount> GetInformationAccount(@RequestParam String idUserAccount) {
        return accountService.GetInformationAccount(idUserAccount);
    }
    @PostMapping(path = "/Account/CreateUserAccount",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> GetInformationAccount(@RequestBody UserAccount userAccount) {
        return accountService.CreateUserAccount(userAccount);
    }

    @RequestMapping(value = "/Account/DeleteUserAccount",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Boolean> DeleteUserAccount(@RequestParam String idUserAccount) {
        return accountService.DeleteUserAccount(idUserAccount);
    }

    @RequestMapping(value = "/Account/ChangePassword",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Boolean> ChangePassword(@RequestBody ChangePasswordAccount changePasswordAccount) {
        return accountService.ChangePassword(changePasswordAccount);
    }
}

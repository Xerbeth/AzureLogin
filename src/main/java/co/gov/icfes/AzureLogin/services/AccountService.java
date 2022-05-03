package co.gov.icfes.AzureLogin.services;

//region import
import co.gov.icfes.AzureLogin.dto.ApiResponse;
import co.gov.icfes.AzureLogin.dto.ChangePasswordAccount;
import co.gov.icfes.AzureLogin.dto.UserAccount;
import co.gov.icfes.AzureLogin.Utils.Constants.MapperUserGrahp;
import co.gov.icfes.AzureLogin.services.implement.IAccountService;
import co.gov.icfes.AzureLogin.services.implement.ISecurityService;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.PasswordProfile;
import com.microsoft.graph.models.User;
import com.microsoft.graph.models.UserChangePasswordParameterSet;
import com.microsoft.graph.requests.GraphServiceClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static co.gov.icfes.AzureLogin.Utils.Constants.AccountMessage.*;
import static co.gov.icfes.AzureLogin.Utils.Constants.Variables.*;
//endregion import

@Service
public class AccountService implements IAccountService {

    @Autowired
    private ISecurityService securityService;

    private static final Logger LOG = LogManager.getLogger(SecurityService.class);
    @Value("${spring.cloud.azure.active-directory.domain}")
    private String Domain;

    @Override
    public ApiResponse<UserAccount> GetInformationAccount(String idAccount) {
        ApiResponse<UserAccount> response = new ApiResponse<UserAccount>();
        try{
            LOG.info("Solicitud de información de cuenta de usuario " + idAccount);
            ApiResponse<TokenCredentialAuthProvider> tokenCredentialAuthProvider = securityService.GetTokenCredencialAuthProvider();

            if(!tokenCredentialAuthProvider.getStatus()){
                throw new Exception(ERROR01_TOKEN_INFOACCOUNT);
            }

            final GraphServiceClient graphClient =
                    GraphServiceClient
                            .builder()
                            .authenticationProvider( tokenCredentialAuthProvider.getData() )
                            .buildClient();

            final User user = graphClient.users(idAccount)
                    .buildRequest()
                    .select("id, displayName, givenName, surname, mailNickname, mail, mobilePhone, country, city, streetAddress, userPrincipalName")
                    .get();

            response.setData(MapperUserGrahp.ToUserAccount(user));
            response.setMessage(SUCCESS_MESSAGE);
            response.setStatus(SUCCESS);

        }catch (Exception ex){
            LOG.info(SERVICE_EXECUTION_ERROR + SecurityService.class.getName());
            LOG.error(ex);
            response.setException(ex.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse<String> CreateUserAccount(UserAccount userAccount) {
        ApiResponse<String> response = new ApiResponse<String>();
        try{
            LOG.info("Solicitud de creación cuenta de usuario");
            ApiResponse<TokenCredentialAuthProvider> tokenCredentialAuthProvider = securityService.GetTokenCredencialAuthProvider();

            if(!tokenCredentialAuthProvider.getStatus()){
                throw new Exception(ERROR02_TOKEN_CREATEACCOUNT);
            }

            GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( tokenCredentialAuthProvider.getData() ).buildClient();

            User user = new User();
            user.accountEnabled = true; // borrar antes de salir a producción
            user.displayName = userAccount.getPrimerNombre() + " " + userAccount.getPrimerApellido();
            user.givenName = userAccount.getPrimerNombre();
            user.surname = userAccount.getPrimerApellido();
            user.mailNickname = userAccount.getUserName();
            user.mail = userAccount.getCorreoElectronico();
            user.mobilePhone = userAccount.getNumeroTelefonico();
            user.country = userAccount.getPais();
            user.city = userAccount.getMunicipio();
            user.streetAddress = userAccount.getDireccion();
            user.userPrincipalName = userAccount.getUserName()+Domain;
            PasswordProfile passwordProfile = new PasswordProfile();
            passwordProfile.forceChangePasswordNextSignIn = true; // Habilitar el cambio de contraseña la primera vez que inicie sesion
            passwordProfile.password = userAccount.getPassword();
            user.passwordProfile = passwordProfile;

            final User userResponse = graphClient.users()
                    .buildRequest()
                    .post(user);

            response.setData(userResponse.id);
            response.setMessage(SUCCESS_MESSAGE);
            response.setStatus(SUCCESS);

        }catch (Exception ex){
            LOG.info(SERVICE_EXECUTION_ERROR + SecurityService.class.getName());
            LOG.error(ex);
            response.setException(ex.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse<Boolean> DeleteUserAccount(String idUserAccount) {
        ApiResponse<Boolean> response = new ApiResponse<Boolean>();
        try{
            LOG.info("Solicitud de eliminación de cuenta de usuario " + idUserAccount);
            ApiResponse<TokenCredentialAuthProvider> tokenCredentialAuthProvider = securityService.GetTokenCredencialAuthProvider();

            if(!tokenCredentialAuthProvider.getStatus()){
                throw new Exception(ERROR03_TOKEN_DELETEACCOUNT);
            }

            GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( tokenCredentialAuthProvider.getData() ).buildClient();

            graphClient.users(idUserAccount)
                    .buildRequest()
                    .delete();

            response.setData(true);
            response.setMessage(SUCCESS_MESSAGE);
            response.setStatus(SUCCESS);

        }catch (Exception ex){
            LOG.info(SERVICE_EXECUTION_ERROR + SecurityService.class.getName());
            LOG.error(ex);
            response.setException(ex.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse<Boolean> ChangePassword(ChangePasswordAccount changePasswordAccount) {
        ApiResponse<Boolean> response = new ApiResponse<Boolean>();
        try{
            LOG.info("Solicitud de eliminación de cuenta de usuario " + changePasswordAccount.getIdUserAccount());
            ApiResponse<TokenCredentialAuthProvider> tokenCredentialAuthProvider = securityService.GetTokenCredencialAuthProvider();

            if(!tokenCredentialAuthProvider.getStatus()){
                throw new Exception(ERROR04_TOKEN_CHANGEPASSWORD);
            }

            GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( tokenCredentialAuthProvider.getData() ).buildClient();

            graphClient.me()
                    .changePassword(UserChangePasswordParameterSet
                            .newBuilder()
                            .withCurrentPassword(changePasswordAccount.getContrasenaActual())
                            .withNewPassword(changePasswordAccount.getContrasenaNueva())
                            .build())
                    .buildRequest()
                    .post();

            response.setData(true);
            response.setMessage(SUCCESS_MESSAGE);
            response.setStatus(SUCCESS);

        }catch (Exception ex){
            LOG.info(SERVICE_EXECUTION_ERROR + SecurityService.class.getName());
            LOG.error(ex);
            response.setException(ex.getMessage());
        }
        return response;
    }
}

package co.gov.icfes.AzureLogin.services;

//region import
import co.gov.icfes.AzureLogin.DTO.ApiResponse;
import co.gov.icfes.AzureLogin.DTO.UserAccount;
import co.gov.icfes.AzureLogin.Utils.Constants.MapperUserGrahp;
import co.gov.icfes.AzureLogin.services.implement.IAccountService;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.PasswordProfile;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import static co.gov.icfes.AzureLogin.Utils.Constants.Variables.*;
//endregion import

@Service
public class AccountService implements IAccountService {

    private static final Logger LOG = LogManager.getLogger(SecurityService.class);
    @Value("${spring.cloud.azure.active-directory.credential.client-id}")
    private String ClientId;
    @Value("${spring.cloud.azure.active-directory.credential.client-secret}")
    private String ClientSecret;
    @Value("${spring.cloud.azure.active-directory.profile.tenant-id}")
    private String TenantId;
    @Value("${spring.cloud.azure.active-directory.default-scope}")
    private String DefaultScope;
    @Value("${spring.cloud.azure.active-directory.default-url}")
    private String DefaultUrl;
    @Value("${spring.cloud.azure.active-directory.domain}")
    private String Domain;

    @Override
    public ApiResponse<UserAccount> GetInformationAccount(String idAccount) {
        ApiResponse<UserAccount> response = new ApiResponse<UserAccount>();
        try{
            LOG.info("Solicitud de información de cuenta de usuario " + idAccount);
            List<String> scopes = new ArrayList<String>();
            scopes.add(DefaultScope);

            final ClientSecretCredential clientSecretCredential  = new ClientSecretCredentialBuilder()
                    .clientId(ClientId)
                    .clientSecret(ClientSecret)
                    .tenantId(TenantId)
                    .build();

            final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(scopes, clientSecretCredential );

            final GraphServiceClient graphClient =
                    GraphServiceClient
                            .builder()
                            .authenticationProvider(tokenCredentialAuthProvider)
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
            List<String> scopes = new ArrayList<String>();
            scopes.add(DefaultScope);

            final ClientSecretCredential clientSecretCredential  = new ClientSecretCredentialBuilder()
                    .clientId(ClientId)
                    .clientSecret(ClientSecret)
                    .tenantId(TenantId)
                    .build();

            final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(scopes, clientSecretCredential );

            GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( tokenCredentialAuthProvider ).buildClient();

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
            response.setMessage(SUCCESS_MESSAGE);
            response.setStatus(SUCCESS);
            response.setData(userResponse.id);

        }catch (Exception ex){
            LOG.info(SERVICE_EXECUTION_ERROR + SecurityService.class.getName());
            LOG.error(ex);
            response.setException(ex.getMessage());
        }
        return response;
    }
}

package co.gov.icfes.AzureLogin.services;

//region import
import co.gov.icfes.AzureLogin.DTO.ApiResponse;
import co.gov.icfes.AzureLogin.services.implement.IAccountService;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
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

    @Override
    public ApiResponse<String> GetInformationAccount(String idAccount) {
        ApiResponse<String> response = new ApiResponse<String>();
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

            final User user = graphClient.users(idAccount).buildRequest().get();
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            response.setData(ow.writeValueAsString(user));
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

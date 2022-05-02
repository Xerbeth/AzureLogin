package co.gov.icfes.AzureLogin.services;

//region import
import co.gov.icfes.AzureLogin.DTO.ApiResponse;
import co.gov.icfes.AzureLogin.services.implement.ISecurityService;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//endregion import

import static co.gov.icfes.AzureLogin.Utils.Constants.Variables.*;

@Service
public class SecurityService implements ISecurityService {

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
    public ApiResponse<String> GetToken() throws Exception {
        ApiResponse<String> response = new ApiResponse<String>();
        try{
            LOG.info("Solicitud de generación de token Active Directory.");
            List<String> scopes = new ArrayList<String>();
            scopes.add(DefaultScope);

            final ClientSecretCredential clientSecretCredential  = new ClientSecretCredentialBuilder()
                    .clientId(ClientId)
                    .clientSecret(ClientSecret)
                    .tenantId(TenantId)
                    .build();

            final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(scopes, clientSecretCredential );

            URL requestUrl = new URL(DefaultUrl);
            response.setData(tokenCredentialAuthProvider.getAuthorizationTokenAsync(requestUrl).get());
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

package co.gov.icfes.AzureLogin.services;

import co.gov.icfes.AzureLogin.DTO.ApiResponse;
import co.gov.icfes.AzureLogin.services.implement.ISecurity;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static co.gov.icfes.AzureLogin.Utils.Constants.Variables.*;

@Service
public class SecurityService implements ISecurity {

    private static final Logger LOG = LogManager.getLogger(SecurityService.class);

    private String ClientId = "318c3c9b-9f36-4cbe-8ffa-93bd78428834";
    private String ClientSecret = "45z7Q~9H2Ou4qWAESaUyOp0TBN5jYLGcdDsXB";
    private String TenantId = "375abf56-f1c9-42af-8094-fb5b7f1020fb";
    private String DefaultScope = "https://graph.microsoft.com/.default";
    private String DefaultUrl = "https://graph.microsoft.com/v1.0/me";

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

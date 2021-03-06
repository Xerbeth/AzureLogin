package co.gov.icfes.AzureLogin.services;

//region import
import co.gov.icfes.AzureLogin.dto.ApiResponse;
import co.gov.icfes.AzureLogin.dto.LoginUser;
import co.gov.icfes.AzureLogin.services.implement.ISecurityService;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.aad.msal4j.*;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.util.*;
import static co.gov.icfes.AzureLogin.Utils.Constants.Variables.*;
//endregion import

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
    @Value("${spring.cloud.azure.authority}")
    private String Authority;
    @Value("${spring.cloud.azure.scope}")
    private Set<String> scope;

    @Override
    public ApiResponse<String> GetToken() {
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

    @Override
    public ApiResponse<TokenCredentialAuthProvider> GetTokenCredencialAuthProvider() {
        ApiResponse<TokenCredentialAuthProvider> response = new ApiResponse<TokenCredentialAuthProvider>();
        try{
            LOG.info("Solicitud interna de generación de token Active Directory.");
            List<String> scopes = new ArrayList<String>();
            scopes.add(DefaultScope);

            final ClientSecretCredential clientSecretCredential  = new ClientSecretCredentialBuilder()
                    .clientId(ClientId)
                    .clientSecret(ClientSecret)
                    .tenantId(TenantId)
                    .build();

            final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(scopes, clientSecretCredential );

            response.setData(tokenCredentialAuthProvider);
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
    public ApiResponse<String> GetTokenUsernamePassword(LoginUser loginUser) {
        ApiResponse<String> response = new ApiResponse<String>();
        try{
            PublicClientApplication pca = PublicClientApplication.builder(ClientId)
                    .authority(Authority)
                    .build();

            //Get list of accounts from the application's token cache, and search them for the configured username
            //getAccounts() will be empty on this first call, as accounts are added to the cache when acquiring a token
            Set<IAccount> accountsInCache = pca.getAccounts().join();
            IAccount account = getAccountByUsername(accountsInCache, loginUser.getCorreoCorporativo());

            //Attempt to acquire token when user's account is not in the application's token cache
            IAuthenticationResult result = acquireTokenUsernamePassword(pca, scope, account, loginUser.getCorreoCorporativo(), loginUser.getPassword());
            LOG.info("Account username: " + result.account().username());
            LOG.info("Access token:     " + result.accessToken());
            LOG.info("Id token:         " + result.idToken());

            accountsInCache = pca.getAccounts().join();
            account = getAccountByUsername(accountsInCache, loginUser.getCorreoCorporativo());

            //Attempt to acquire token again, now that the user's account and a token are in the application's token cache
            result = acquireTokenUsernamePassword(pca, scope, account, loginUser.getCorreoCorporativo(), loginUser.getPassword());
            LOG.info("Account username: " + result.account().username());
            LOG.info("Access token:     " + result.accessToken());
            LOG.info("Id token:         " + result.idToken());

            response.setData(result.accessToken());
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
    public ApiResponse<TokenCredentialAuthProvider> GetTokenCredencialAuthProviderByUserNamePassword(String correoElectronico, String password) {
        ApiResponse<TokenCredentialAuthProvider> response = new ApiResponse<TokenCredentialAuthProvider>();
        try{
            final UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredentialBuilder()
                    .clientId(ClientId)
                    .username(correoElectronico)
                    .password(password)
                    .build();

            final TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(Arrays.asList(DefaultScope), usernamePasswordCredential);

            response.setData(tokenCredentialAuthProvider);
            response.setMessage(SUCCESS_MESSAGE);
            response.setStatus(SUCCESS);
        }catch (Exception ex){
            LOG.info(SERVICE_EXECUTION_ERROR + SecurityService.class.getName());
            LOG.error(ex);
            response.setException(ex.getMessage());
        }

        return response;
    }

    private static IAuthenticationResult acquireTokenUsernamePassword(PublicClientApplication pca,
                                                                      Set<String> scope,
                                                                      IAccount account,
                                                                      String username,
                                                                      String password) throws Exception {
        IAuthenticationResult result;
        try {
            SilentParameters silentParameters =
                    SilentParameters
                            .builder(scope)
                            .account(account)
                            .build();
            // Try to acquire token silently. This will fail on the first acquireTokenUsernamePassword() call
            // because the token cache does not have any data for the user you are trying to acquire a token for
            result = pca.acquireTokenSilently(silentParameters).join();
            LOG.info("==acquireTokenSilently call succeeded");
        } catch (Exception ex) {
            if (ex.getCause() instanceof MsalException) {
                LOG.info("==acquireTokenSilently call failed: " + ex.getCause());
                UserNamePasswordParameters parameters =
                        UserNamePasswordParameters
                                .builder(scope, username, password.toCharArray())
                                .build();
                // Try to acquire a token via username/password. If successful, you should see
                // the token and account information printed out to console
                result = pca.acquireToken(parameters).join();
                LOG.info("==username/password flow succeeded");
            } else {
                // Handle other exceptions accordingly
                throw ex;
            }
        }
        return result;
    }

    /**
     * Helper function to return an account from a given set of accounts based on the given username,
     * or return null if no accounts in the set match
     */
    private static IAccount getAccountByUsername(Set<IAccount> accounts, String username) {
        if (accounts.isEmpty()) {
            LOG.info("==No accounts in cache");
        } else {
            LOG.info("==Accounts in cache: " + accounts.size());
            for (IAccount account : accounts) {
                if (account.username().equals(username)) {
                    return account;
                }
            }
        }
        return null;
    }

}

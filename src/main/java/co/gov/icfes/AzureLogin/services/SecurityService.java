package co.gov.icfes.AzureLogin.services;

//region import
import co.gov.icfes.AzureLogin.dto.ApiResponse;
import co.gov.icfes.AzureLogin.services.implement.ISecurityService;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.aad.msal4j.*;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
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

    private static String authority;
    private static Set<String> scope;
    private static String clientId;
    private static String username;
    private static String password;

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
    public void GetTokenUsernamePassword() throws Exception {

        setUpSampleData();

        PublicClientApplication pca = PublicClientApplication.builder(ClientId)
                .authority(authority)
                .build();

        //Get list of accounts from the application's token cache, and search them for the configured username
        //getAccounts() will be empty on this first call, as accounts are added to the cache when acquiring a token
        Set<IAccount> accountsInCache = pca.getAccounts().join();
        IAccount account = getAccountByUsername(accountsInCache, username);

        //Attempt to acquire token when user's account is not in the application's token cache
        IAuthenticationResult result = acquireTokenUsernamePassword(pca, scope, account, username, password);
        System.out.println("Account username: " + result.account().username());
        System.out.println("Access token:     " + result.accessToken());
        System.out.println("Id token:         " + result.idToken());
        System.out.println();

        accountsInCache = pca.getAccounts().join();
        account = getAccountByUsername(accountsInCache, username);

        //Attempt to acquire token again, now that the user's account and a token are in the application's token cache
        result = acquireTokenUsernamePassword(pca, scope, account, username, password);
        System.out.println("Account username: " + result.account().username());
        System.out.println("Access token:     " + result.accessToken());
        System.out.println("Id token:         " + result.idToken());
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
            System.out.println("==acquireTokenSilently call succeeded");
        } catch (Exception ex) {
            if (ex.getCause() instanceof MsalException) {
                System.out.println("==acquireTokenSilently call failed: " + ex.getCause());
                UserNamePasswordParameters parameters =
                        UserNamePasswordParameters
                                .builder(scope, username, password.toCharArray())
                                .build();
                // Try to acquire a token via username/password. If successful, you should see
                // the token and account information printed out to console
                result = pca.acquireToken(parameters).join();
                System.out.println("==username/password flow succeeded");
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
            System.out.println("==No accounts in cache");
        } else {
            System.out.println("==Accounts in cache: " + accounts.size());
            for (IAccount account : accounts) {
                if (account.username().equals(username)) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Helper function unique to this sample setting. In a real application these wouldn't be so hardcoded, for example
     * values such as username/password would come from the user, and different users may require different scopes
     */
    private static void setUpSampleData() throws IOException {
        authority = "https://login.microsoftonline.com/organizations/";
        scope = Collections.singleton("user.read");
        clientId = "318c3c9b-9f36-4cbe-8ffa-93bd78428834";
        username = "AdeleV@faibertorresohotmail.onmicrosoft.com";
        password = "Pruebas123*";
    }

}

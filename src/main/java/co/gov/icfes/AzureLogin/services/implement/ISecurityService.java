package co.gov.icfes.AzureLogin.services.implement;

//region import
import co.gov.icfes.AzureLogin.dto.ApiResponse;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import org.springframework.scheduling.annotation.Async;
//endregion import

public interface ISecurityService {

    /**
     * Método para obtener el token de operación sobre Azure AD
     * @return Token de opearación
     */
    ApiResponse<String> GetToken();

    /**
     * Método para obtener el TokenCredencialAuthProvider para realizar operaciones sobre el Active Directory
     * @return TokenCredencialAuthProvider
     */
    ApiResponse<TokenCredentialAuthProvider> GetTokenCredencialAuthProvider();
}

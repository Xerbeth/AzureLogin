package co.gov.icfes.AzureLogin.services.implement;

//region import
import co.gov.icfes.AzureLogin.dto.ApiResponse;
import co.gov.icfes.AzureLogin.dto.LoginUser;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
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

    /**
     * Método para obtener el Token de operación sobre un usuario
     * @param loginUser
     * @return Token de operación
     */
    ApiResponse<String> GetTokenUsernamePassword(LoginUser loginUser);

    /**
     * Método para obtener el TokenCredencialAuthProvider para realizar operaciones sobre el Active Directory
     * mediante los correo electronico corporativo del usuario y la contraseña
     * @param correoElectronico Correo electronico corporativo de la cuenta
     * @param password Constraseña
     * @return TokenCredencialAuthProvider
     */
    ApiResponse<TokenCredentialAuthProvider> GetTokenCredencialAuthProviderByUserNamePassword(String correoElectronico, String password);
}

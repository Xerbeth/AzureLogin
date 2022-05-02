package co.gov.icfes.AzureLogin.services.implement;

//region import
import co.gov.icfes.AzureLogin.DTO.ApiResponse;
//endregion import

public interface ISecurityService {

    /**
     * Método para obtener el token de operación sobre Azure AD
     * @return Token de opearación
     */
    ApiResponse<String> GetToken() throws Exception;
}

package co.gov.icfes.AzureLogin.services.implement;

import co.gov.icfes.AzureLogin.DTO.ApiResponse;

public interface ISecurity {

    /**
     * Método para obtener el token de operación sobre Azure AD
     * @return Token de opearación
     */
    ApiResponse<String> GetToken() throws Exception;
}

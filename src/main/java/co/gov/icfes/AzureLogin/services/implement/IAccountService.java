package co.gov.icfes.AzureLogin.services.implement;

import co.gov.icfes.AzureLogin.DTO.ApiResponse;

public interface IAccountService {
    /**
     * Método para obtener la información del usuario del Active Directory
     * @param idAccount Identificador del usuario en Active Directory
     * @return Información de la cuenta del usuario
     */
    ApiResponse<String> GetInformationAccount(String idAccount);
}

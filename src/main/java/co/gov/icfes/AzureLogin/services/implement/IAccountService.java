package co.gov.icfes.AzureLogin.services.implement;

import co.gov.icfes.AzureLogin.DTO.ApiResponse;
import co.gov.icfes.AzureLogin.DTO.UserAccount;

public interface IAccountService {
    /**
     * Método para obtener la información del usuario del Active Directory
     * @param idAccount Identificador del usuario en Active Directory
     * @return Información de la cuenta del usuario
     */
    ApiResponse<UserAccount> GetInformationAccount(String idAccount);

    /**
     * Método para crear una cuenta de usuario en el Active Directory
     * @param userAccount Información de la nueva cuenta
     * @return Información de la cuenta del usuario
     */
    ApiResponse<String> CreateUserAccount(UserAccount userAccount);
}

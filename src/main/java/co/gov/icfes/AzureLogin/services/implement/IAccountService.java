package co.gov.icfes.AzureLogin.services.implement;

//region import
import co.gov.icfes.AzureLogin.dto.ApiResponse;
import co.gov.icfes.AzureLogin.dto.ChangePasswordAccount;
import co.gov.icfes.AzureLogin.dto.UserAccount;
//endregion import

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

    /**
     * Método para eliminar un usuario del Active Directory
     * @param idUserAccount Identificador del usuario
     * @return Respuesta de la petición
     */
    ApiResponse<Boolean> DeleteUserAccount(String idUserAccount);

    /**
     * Método para cambiar la contraseña de un usuario
     * @param changePasswordAccount Objeto requeridos para la operación
     * @return Respuesta de la petición
     */
    ApiResponse<Boolean> ChangePassword(ChangePasswordAccount changePasswordAccount);

    /**
     * Método para actualizar los datos de una cuenta de usuario en el Active Directory
     * @param userAccount Información de la cuenta
     * @return Respuesta de la petición
     */
    ApiResponse<Boolean> UpdateUserAccount(UserAccount userAccount);

}

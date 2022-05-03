package co.gov.icfes.AzureLogin.Utils.Constants;

//region import
import co.gov.icfes.AzureLogin.dto.UserAccount;
import com.microsoft.graph.models.User;
//endregion import

/**
 * Clase para realizar mappeo manual entre las clases User del grahp y UserAccount
 */
public class MapperUserGrahp {

    /**
     * Método para obtener generar un objeto UserAccount de User grahp
     * @param user Objeto con información del grahp
     * @return Objeto UserAccount con información
     */
    public static UserAccount ToUserAccount(final User user){
        UserAccount userAccount = new UserAccount();
        userAccount.setIdUserAccount(user.id);
        userAccount.setNombreCompleto(user.displayName);
        userAccount.setPrimerNombre(user.givenName);
        userAccount.setPrimerApellido(user.surname);
        userAccount.setCorreoElectronico(user.mail);
        userAccount.setUserName(user.mailNickname);
        userAccount.setNumeroTelefonico(user.mobilePhone);
        userAccount.setFechaNacimiento(String.valueOf(user.birthday));
        userAccount.setPais(user.country);
        userAccount.setMunicipio(user.city);
        userAccount.setDireccion(user.streetAddress);

        return userAccount;
    }
}

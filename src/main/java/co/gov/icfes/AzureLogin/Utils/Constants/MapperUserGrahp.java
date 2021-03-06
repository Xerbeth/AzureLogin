package co.gov.icfes.AzureLogin.Utils.Constants;

//region import
import co.gov.icfes.AzureLogin.dto.UserAccount;
import com.microsoft.graph.models.User;
import static co.gov.icfes.AzureLogin.Utils.Constants.StringFormat.splitName;
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
        String[] nombresSeparados = splitName(user.givenName);
        String[] apellidosSeparados = splitName(user.surname);

        UserAccount userAccount = new UserAccount();
        userAccount.setIdUserAccount(user.id == null ? "" :user.id);
        userAccount.setNombreCompleto(user.displayName == null ? "" : user.displayName);
        userAccount.setPrimerNombre(user.givenName == null ? "" : nombresSeparados[0]);
        userAccount.setSegundoNombre(nombresSeparados.length == 1 ? "" : nombresSeparados[1]);
        userAccount.setPrimerApellido(user.surname == null ? "" : apellidosSeparados[0]);
        userAccount.setSegundoApellido(apellidosSeparados.length == 1 ? "" : apellidosSeparados[1]);
        userAccount.setCorreoElectronico(user.mail == null ? "" : user.mail);
        userAccount.setUserName(user.mailNickname == null ? "" : user.mailNickname);
        userAccount.setNumeroTelefonico(user.mobilePhone == null ? "" : user.mobilePhone);
        userAccount.setFechaNacimiento(String.valueOf(user.birthday));
        userAccount.setPais(user.country == null ? "" : user.country);
        userAccount.setMunicipio(user.city == null ? "" : user.city);
        userAccount.setDireccion(user.streetAddress == null ? "" : user.streetAddress);

        return userAccount;
    }
}

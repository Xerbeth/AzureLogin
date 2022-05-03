package co.gov.icfes.AzureLogin.dto;

public class ChangePasswordAccount {

    /**
     * Propiedad para la contraseña actual del usuario
     */
    private String  ContrasenaActual;
    /**
     * Propiedad para la contraseña nueva
     */
    private String ContrasenaNueva;
    /**
     * Propiedad para el identificador del usuario
     */
    private String IdUserAccount;
    public ChangePasswordAccount(){}

    public String getContrasenaActual() {
        return ContrasenaActual;
    }

    public void setContrasenaActual(String contrasenaActual) {
        ContrasenaActual = contrasenaActual;
    }

    public String getContrasenaNueva() {
        return ContrasenaNueva;
    }

    public void setContrasenaNueva(String contrasenaNueva) {
        ContrasenaNueva = contrasenaNueva;
    }

    public String getIdUserAccount() {
        return IdUserAccount;
    }

    public void setIdUserAccount(String idUserAccount) {
        IdUserAccount = idUserAccount;
    }
}

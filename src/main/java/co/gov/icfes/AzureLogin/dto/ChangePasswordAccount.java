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
     * Propiedad para el correo electroniico corporativo de la cuenta del usuario
     */
    private String CorreoElectronico;
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

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        CorreoElectronico = correoElectronico;
    }
}

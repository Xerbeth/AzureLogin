package co.gov.icfes.AzureLogin.dto;


public class LoginUser {

    /**
     * Propiedad para el correo de la cuenta en el directorio activo dominio @icfes.gov.co
     */
    private String CorreoCorporativo;
    /**
     * Propiedad para la cuenta del usuario
     */
    private String Password;
    public LoginUser(){}

    public String getCorreoCorporativo() {
        return CorreoCorporativo;
    }

    public void setCorreoCorporativo(String correoCorporativo) {
        CorreoCorporativo = correoCorporativo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

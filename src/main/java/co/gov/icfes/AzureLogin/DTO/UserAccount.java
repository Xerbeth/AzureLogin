package co.gov.icfes.AzureLogin.DTO;

import static co.gov.icfes.AzureLogin.Utils.Constants.StringFormat.upperCaseFirst;
import static co.gov.icfes.AzureLogin.Utils.Constants.Variables.EMPTY_STRING;

public class UserAccount {

    /**
     * Propiedad para el identificador del azure Active Directory
     */
    private String IdUserAccount;
    /**
     * Propiedad para el nombre completo del usuario
     * @return
     */
    private String NombreCompleto;
    /**
     * Propiedad para el primer nombre del usuario
     */
    private String PrimerNombre;
    /**
     * propiedad para el segundo nombre del usuario
     */
    private String SegundoNombre;
    /**
     * Propiedad  para el primer apellido del usuario
     */
    private String PrimerApellido;
    /**
     * Propiedad para el segundo apellido del usuario
     */
    private String SegundoApellido;
    /**
     * Propiedad para el correo electronico del usuario
     */
    private String CorreoElectronico;
    /**
     * Propiedad para el genero del usuario
     */
    private String Genero;
    /**
     * Propiedad que no se puede establecer en una solicitud inicial de creación de usuario
     */
    private String FechaNacimiento;
    /**
     * Propiedad para el nombre de usuario de la cuenta del usuario
     */
    private String UserName;
    /**
     * Propiedad para el password del usuario
     */
    private String Password;
    /**
     * Propiedad para el número telefonico del usuario
     */
    private String NumeroTelefonico;
    /**
     * Propiedad para el pais
     */
    private String Pais;
    /**
     * Propiedad para el departamento
     */
    private String Departamento;
    /**
     * Propiedad para el municipio
     */
    private String Municipio;
    /**
     * Propiedad para la dirección del usuario
     */
    private String Direccion;

    public UserAccount() {
        this.SegundoNombre = EMPTY_STRING;
        this.SegundoApellido = EMPTY_STRING;
        this.Genero = EMPTY_STRING;
        this.Password = EMPTY_STRING;
        this.Departamento = EMPTY_STRING;
    }

    public String getIdUserAccount() {
        return IdUserAccount;
    }

    public void setIdUserAccount(String idUserAccount) {
        IdUserAccount = idUserAccount;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        NombreCompleto = nombreCompleto;
    }

    public String getPrimerNombre() {
        return upperCaseFirst(PrimerNombre.toLowerCase());
    }

    public void setPrimerNombre(String primerNombre) {
        PrimerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return upperCaseFirst(SegundoNombre.toLowerCase());
    }

    public void setSegundoNombre(String segundoNombre) {
        SegundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return upperCaseFirst(PrimerApellido.toLowerCase());
    }

    public void setPrimerApellido(String primerApellido) {
        PrimerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return upperCaseFirst(SegundoApellido.toLowerCase());
    }

    public void setSegundoApellido(String segundoApellido) {
        SegundoApellido = segundoApellido;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico.toLowerCase();
    }

    public void setCorreoElectronico(String correoElectronico) {
        CorreoElectronico = correoElectronico;
    }

    public String getGenero() {
        return upperCaseFirst(Genero.toLowerCase());
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public String getUserName() {
        return upperCaseFirst(UserName.toLowerCase());
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNumeroTelefonico() {
        return NumeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        NumeroTelefonico = numeroTelefonico;
    }
    public String getPais() {
        return upperCaseFirst(Pais.toLowerCase());
    }

    public void setPais(String pais) {
        Pais = pais;
    }

    public String getMunicipio() {
        return upperCaseFirst(Municipio.toLowerCase());
    }

    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }

    public String getDepartamento() {
        return upperCaseFirst(Departamento.toLowerCase());
    }

    public void setDepartamento(String departamento) {
        Departamento = departamento;
    }

    public String getDireccion() {
        return upperCaseFirst(Direccion.toLowerCase());
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
}
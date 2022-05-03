package co.gov.icfes.AzureLogin.Utils.Constants;

//region import
import static co.gov.icfes.AzureLogin.Utils.Constants.Variables.EMPTY_STRING;
//endregion import

/**
 * Clase para dar formato a los textos
 */
public class StringFormat {
    /**
     * Método para convertir el primer caracter de una string a mayusculas
     * @param val Texto a transformar
     * @return Texto transformado con el primer caracter en mayusculas
     */
    public static String upperCaseFirst(String val) {
        if(val.length() == 0) return EMPTY_STRING;
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }
}

package co.gov.icfes.AzureLogin.DTO;

public class ApiResponse<T> {

    private boolean Status;
    private String Mensaje;
    private T Data;

    public ApiResponse(T data){
        this.setData(data);
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}

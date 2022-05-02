package co.gov.icfes.AzureLogin.DTO;

import static co.gov.icfes.AzureLogin.Utils.Constants.Variables.*;

public class ApiResponse<T> {

    private boolean Status;
    private String Message;
    private String Exception;
    private T Data;

    public ApiResponse(){
        this.Status = false;
        this.Message = (this.Status) ? SUCCESS_MESSAGE : FAILED_MESSAGE;
    }

    public ApiResponse(boolean status, String message, String exception, T data) {
        Status = status;
        Message = message;
        Exception = exception;
        Data = data;
    }

    public ApiResponse(ApiResponseBuilder apiResponseBuilder) {
        this.Status = apiResponseBuilder.Status;
        this.Message = apiResponseBuilder.Message;
        this.Exception = apiResponseBuilder.Exception;
        this.Data = (T) apiResponseBuilder.Data;
    }

    public boolean getStatus() {
        return Status;
    }
    public void setStatus(boolean status) {
        this.Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public String getException() {
        return Exception;
    }

    public void setException(String exception) {
        this.Exception = exception;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        this.Data = data;
    }

    public static class ApiResponseBuilder<T>{
        private boolean Status;
        private String Message;
        private String Exception;
        private T Data;

        public ApiResponseBuilder(T t){
            this.Data = t;
            this.Status = FAILED;
            this.Message = FAILED_MESSAGE;
            this.Exception = EMPTY_STRING;
        }

        public ApiResponseBuilder status(boolean status){
            this.Status = status;
            this.Message = (status == true) ? SUCCESS_MESSAGE : FAILED_MESSAGE;
            return this;
        }

        public ApiResponseBuilder message(String message){
            this.Message = message;
            return this;
        }

        public ApiResponseBuilder exception(String exception){
            this.Exception = exception;
            return this;
        }

        //Return the finally consrcuted User object
        public ApiResponse build() {
            ApiResponse apiResponse =  new ApiResponse(this);
            return apiResponse;
        }

    }
}

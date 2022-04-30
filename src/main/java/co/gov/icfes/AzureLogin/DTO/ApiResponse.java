package co.gov.icfes.AzureLogin.DTO;

import static co.gov.icfes.AzureLogin.Utils.Constants.Variables.*;

public class ApiResponse<T> {

    private final boolean Status;
    private final String Message;
    private final String Exception;
    private final T Data;


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

    public boolean isStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public String getException() {
        return Exception;
    }

    public T getData() {
        return Data;
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

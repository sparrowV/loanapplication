package eu.twinno.loanapplication.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Dto {
    public enum Result {
        OK(0, "OK"),
        REGISTRATION_SUCCESSFUL(0,"registration was successful!"),
        LOAN_APPLICATION_REGISTRATION_SUCCESSFUL(0,"loan application was registered successfully"),
        LOAN_APPLICATION_VALIDATION_FAILED(2,"validation of the given loan application failed"),
        CHANGE_STATUS_TO_HIMSELF_ERROR(4,"you can't change status to yourself"),
        USER_DOES_NOT_EXIST(3,"user does not exist"),
        USER_STATUS_CHANGE_SUCCESSFUL(0,"status change was successful"),
        USERNAME_TAKEN(1,"given username is taken!"),
        LOAN_APPLICATION_DOES_NOT_EXIST(6,"loan application with given id does not exist"),
        LOAN_APPLICATION_STATUS_CHANGE_ERROR(7,"can't change loan application status"),
        LOAN_APPLICATION_STATUS_CHANGE_SUCCESSFUL(0,"successfully changed loan application status");
        private int statusCode;
        private String description;

        Result(int errorCode, String description) {
            this.statusCode = errorCode;
            this.description = description;

        }

        public Dto getResponse(Object object) {
            return new Dto(this.getStatusCode(), this.getDescription(), object);
        }

        public Dto getResponse() {
            return new Dto(this.getStatusCode(), this.getDescription());
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    private Object data;
    private int statusCode;
    private String description;

    public Dto(int statusCode, String description, Object data) {
        this.data = data;
        this.statusCode = statusCode;
        this.description = description;
    }

    public Dto(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }


}

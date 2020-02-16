package eu.twinno.loanapplication.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Dto {
    public enum Result {
        OK(0, "OK"),
        REGISTRATION_SUCCESSFUL(0,"registration was successful!"),
        USERNAME_TAKEN(1,"given username is taken!");
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

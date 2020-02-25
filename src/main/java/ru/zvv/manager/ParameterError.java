package ru.zvv.manager;

public class ParameterError {

    private String message;

    public ParameterError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

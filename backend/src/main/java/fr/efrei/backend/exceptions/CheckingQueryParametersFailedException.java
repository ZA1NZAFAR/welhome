package fr.efrei.backend.exceptions;

public class CheckingQueryParametersFailedException extends Exception {
    public CheckingQueryParametersFailedException() {}

    public CheckingQueryParametersFailedException(String message) { super(message); }
}

package tdd.vendingMachine.validation.exception;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message){
        super(message);
    }
}
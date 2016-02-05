package tdd.vendingMachine.validation;

public class BusinessRuleValidationException extends RuntimeException {

    BusinessRuleValidationException(String message){
        super(message);
    }
}

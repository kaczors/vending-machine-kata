package tdd.vendingMachine.validation.rule;

import tdd.vendingMachine.validation.exception.BusinessRuleValidationException;

public interface Rule<T> {

    boolean isValid(T object);

    BusinessRuleValidationException getException(T object);

}

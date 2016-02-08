package tdd.vendingMachine.validation.rule;

import tdd.vendingMachine.validation.exception.ApplicationException;

public interface Rule<T> {

    boolean isValid(T object);

    ApplicationException getException(T object);

}

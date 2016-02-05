package tdd.vendingMachine.validation;

public interface Rule<T> {

    boolean isValid(T object);

    String getViolationMessage(T object);

}

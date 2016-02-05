package tdd.vendingMachine.validation;

public interface Validator<T> {
    void validate(T object);
}

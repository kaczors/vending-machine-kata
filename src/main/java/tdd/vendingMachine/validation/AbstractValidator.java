package tdd.vendingMachine.validation;

import java.util.Collection;

abstract class AbstractValidator<T> implements Validator<T> {

    private final Collection<Rule<T>> rules;

    AbstractValidator(Collection<Rule<T>> rules) {
        this.rules = rules;
    }

    @Override
    public void validate(T object) {
        rules.forEach(
            r -> {
                if (!r.isValid(object)) {
                    throw new BusinessRuleValidationException(r.getViolationMessage(object));
                }
            }
        );
    }
}

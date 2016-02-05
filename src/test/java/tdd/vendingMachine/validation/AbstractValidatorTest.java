package tdd.vendingMachine.validation;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.util.Lists.newArrayList;
import static tdd.vendingMachine.validation.AbstractValidatorTest.TestRule.Builder.testRule;

public class AbstractValidatorTest {
    private static final String SAMPLE_VIOLATION_MESSAGE = "Message";

    @Test
    public void should_throw_exception_when_at_least_one_rule_is_failing() {
        //given
        Validator<String> validator = new AbstractValidator<String>(
            newArrayList(
                newPassingRule(),
                newFailingRule(SAMPLE_VIOLATION_MESSAGE))) {
        };

        //when
        assertThatThrownBy(() -> validator.validate("Sample object"))
            .isInstanceOf(BusinessRuleValidationException.class)
            .hasMessage(SAMPLE_VIOLATION_MESSAGE);
    }

    @Test
    public void should_do_not_throw_exception_when_all_rules_are_passing() {
        //given
        Validator<String> validator = new AbstractValidator<String>(
            newArrayList(
                newPassingRule(),
                newPassingRule())) {
        };

        //when
        validator.validate("Sample object");

        //then no exception expected
    }

    private Rule<String> newPassingRule() {
        return testRule().shouldPass(true).build();
    }

    private Rule<String> newFailingRule(String message) {
        return testRule().shouldPass(false).message(message).build();
    }


    static class TestRule implements Rule<String> {

        private boolean shouldPass;
        private String message;

        @Override
        public boolean isValid(String object) {
            return shouldPass;
        }

        @Override
        public String getViolationMessage(String object) {
            return message;
        }

        public static class Builder {
            private boolean shouldPass;
            private String message;

            private Builder() {
            }

            public static Builder testRule() {
                return new Builder();
            }

            public Builder shouldPass(boolean shouldPass) {
                this.shouldPass = shouldPass;
                return this;
            }

            public Builder message(String message) {
                this.message = message;
                return this;
            }

            public TestRule build() {
                TestRule testRule = new TestRule();
                testRule.message = message;
                testRule.shouldPass = shouldPass;
                return testRule;
            }
        }
    }

}

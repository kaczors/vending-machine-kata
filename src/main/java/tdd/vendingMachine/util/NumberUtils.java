package tdd.vendingMachine.util;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

public final class NumberUtils {
    private static final int COMPARE_TO_EQUAL = 0;
    private static final int COMPARE_TO_LOWER_THAN = -1;

    private NumberUtils(){
    }

    public static boolean isEqualOrGreaterThan(BigDecimal number1, BigDecimal number2){
        checkNotNull(number1);
        checkNotNull(number2);

        return number1.compareTo(number2) != COMPARE_TO_LOWER_THAN;
    }

    public static boolean isEqualByComparingTo(BigDecimal number1, BigDecimal number2){
        return number1.compareTo(number2) == COMPARE_TO_EQUAL;
    }
}

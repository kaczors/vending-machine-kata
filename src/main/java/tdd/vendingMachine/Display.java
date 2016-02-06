package tdd.vendingMachine;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static tdd.vendingMachine.ApplicationConstants.WELCOME_MESSAGE;

class Display {
    private final static String MONEY_FORMAT = "0.00";
    private final static char MONEY_DECIMAL_SEPARATOR = '.';

    private final DecimalFormat decimalFormat;

    private String message = WELCOME_MESSAGE;

    public Display(){
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator(MONEY_DECIMAL_SEPARATOR);
        this.decimalFormat = new DecimalFormat(MONEY_FORMAT, decimalFormatSymbols);
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setMessage(BigDecimal money){
        setMessage(decimalFormat.format(money));
    }

    public String getMessage() {
        return message;
    }

    public void clear() {
        this.message = WELCOME_MESSAGE;
    }
}

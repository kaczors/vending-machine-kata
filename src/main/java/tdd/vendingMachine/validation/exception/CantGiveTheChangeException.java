package tdd.vendingMachine.validation.exception;

import static tdd.vendingMachine.MessageFormats.CANT_GIVE_THE_CHANGE_MESSAGE;

public class CantGiveTheChangeException extends ApplicationException {

    public CantGiveTheChangeException() {
        super(CANT_GIVE_THE_CHANGE_MESSAGE);
    }
}

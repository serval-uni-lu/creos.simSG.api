package duc.sg.java.validator.Exceptions;

import duc.sg.java.validator.rules.IRule;

public class InvalidGridException extends Exception {

    public InvalidGridException(IRule violatedrule) {
        super(violatedrule.getClass().getCanonicalName());
    }


}

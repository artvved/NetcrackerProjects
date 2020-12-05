package validators;

import domain.contracts.Contract;

public interface Validator {
    public ValidationResultMessage validate(Contract contract);
}

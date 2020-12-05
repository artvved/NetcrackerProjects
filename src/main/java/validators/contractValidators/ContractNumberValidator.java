package validators.contractValidators;

import domain.clients.Client;
import domain.contracts.Contract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class ContractNumberValidator implements Validator {

    private final int MAX_NUMBER = 1000;

    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        int number = contract.getNumber();

        if (number > MAX_NUMBER
                || number < 0) {
            msg.setErrorFieldName("number");
            msg.setStatus(Status.ERROR);
        } else if (number == MAX_NUMBER) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

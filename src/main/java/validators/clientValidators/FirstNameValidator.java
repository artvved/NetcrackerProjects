package validators.clientValidators;

import domain.clients.Client;
import domain.contracts.Contract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class FirstNameValidator implements Validator {

    private final int MAX_FIRST_NAME_LEN = 20;

    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        Client client = contract.getClient();
        String firstName = client.getFirstName();

        if (firstName == null ||
                firstName.isEmpty() ||
                firstName.length() > MAX_FIRST_NAME_LEN) {
            msg.setErrorFieldName("firstName");
            msg.setStatus(Status.ERROR);
            msg.setMessage("Error in validating "+msg.getErrorFieldName());

        } else if (firstName.length() == MAX_FIRST_NAME_LEN) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

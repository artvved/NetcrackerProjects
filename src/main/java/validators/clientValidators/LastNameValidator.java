package validators.clientValidators;

import domain.clients.Client;
import domain.contracts.Contract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class LastNameValidator implements Validator {
    private final int MAX_LAST_NAME_LEN = 30;

    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        Client client = contract.getClient();
        String lastName = client.getLastName();

        if (lastName == null
                || lastName.isEmpty() ||
                lastName.length() > MAX_LAST_NAME_LEN) {
            msg.setErrorFieldName("lastName");
            msg.setStatus(Status.ERROR);
            msg.setMessage("Error in validating "+msg.getErrorFieldName());

        } else if (lastName.length() == MAX_LAST_NAME_LEN) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

package validators.clientValidators;

import domain.clients.Client;
import domain.contracts.Contract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class PassportValidator implements Validator {

    private final Long MAX_NUMBER = Long.parseLong("9999999999");

    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        Client client = contract.getClient();
        Long passport = client.getPassport();

        if (passport > MAX_NUMBER ||
                passport < 0) {
            msg.setErrorFieldName("passport");
            msg.setStatus(Status.ERROR);
        } else if (passport.equals(MAX_NUMBER)) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

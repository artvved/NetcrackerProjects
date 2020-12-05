package validators.clientValidators;

import domain.clients.Client;
import domain.contracts.Contract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class BirthDateValidator implements Validator {

    private final int MIN_AGE = 18;
    private final int OK_AGE = 20;
    private final int MAX_AGE = 150;


    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        Client client = contract.getClient();
        int age=client.getAge();

        if (age< MIN_AGE ||
            age>MAX_AGE) {
            msg.setErrorFieldName("birthDate");
            msg.setStatus(Status.ERROR);
            msg.setMessage("Error in validating "+msg.getErrorFieldName());

        } else if (age< OK_AGE) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

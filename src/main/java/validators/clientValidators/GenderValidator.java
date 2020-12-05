package validators.clientValidators;

import domain.clients.Client;
import domain.clients.util.Gender;
import domain.contracts.Contract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class GenderValidator implements Validator {


    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        Client client = contract.getClient();
        Gender gender = client.getGender();
        if (gender.equals(Gender.WARHELICOPTER)){
            msg.setStatus(Status.WARNING);
        }else{
            msg.setStatus(Status.OK);
        }


        return msg;
    }
}

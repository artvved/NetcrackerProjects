package validators.contractValidators.wiredInternetContractValidators;

import domain.contracts.Contract;
import domain.contracts.WiredInternetContract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class MaxInternetSpeedValidator implements Validator {

    private final int MAX_SPEED = 1000;

    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        int maxInternetSpeed = ((WiredInternetContract)contract).getMaxInternetSpeed();

        if (maxInternetSpeed > MAX_SPEED
                || maxInternetSpeed < 0) {
            msg.setErrorFieldName("maxInternetSpeed");
            msg.setStatus(Status.ERROR);
            msg.setMessage("Error in validating "+msg.getErrorFieldName());
        } else if (maxInternetSpeed == MAX_SPEED) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

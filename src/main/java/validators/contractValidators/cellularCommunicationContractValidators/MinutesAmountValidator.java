package validators.contractValidators.cellularCommunicationContractValidators;

import domain.contracts.CellularCommunicationContract;
import domain.contracts.Contract;
import domain.contracts.WiredInternetContract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class MinutesAmountValidator implements Validator {

    private final int MAX_MINUTES_AMOUNT = 1000;

    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        int minutes = ((CellularCommunicationContract) contract).getMinutes();

        if (minutes > MAX_MINUTES_AMOUNT
                || minutes <= 0) {
            msg.setErrorFieldName("minutes");
            msg.setStatus(Status.ERROR);
            msg.setMessage("Error in validating "+msg.getErrorFieldName());
        } else if (minutes == MAX_MINUTES_AMOUNT) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

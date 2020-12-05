package validators.contractValidators.cellularCommunicationContractValidators;

import domain.contracts.CellularCommunicationContract;
import domain.contracts.Contract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class MegabytesAmountValidator implements Validator {

    private final int MAX_MEGABYTES_AMOUNT = 1000;

    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        int megabytes = ((CellularCommunicationContract) contract).getMegabytes();

        if (megabytes > MAX_MEGABYTES_AMOUNT
                || megabytes <= 0) {
            msg.setErrorFieldName("megabytes");
            msg.setStatus(Status.ERROR);
            msg.setMessage("Error in validating "+msg.getErrorFieldName());
        } else if (megabytes == MAX_MEGABYTES_AMOUNT) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

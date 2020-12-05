package validators.contractValidators.cellularCommunicationContractValidators;

import domain.contracts.CellularCommunicationContract;
import domain.contracts.Contract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class SMSAmountValidator implements Validator {

    private final int MAX_SMS_AMOUNT = 1000;

    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        int sms = ((CellularCommunicationContract) contract).getSms();

        if (sms > MAX_SMS_AMOUNT
                || sms <= 0) {
            msg.setErrorFieldName("sms");
            msg.setStatus(Status.ERROR);
            msg.setMessage("Error in validating "+msg.getErrorFieldName());
        } else if (sms == MAX_SMS_AMOUNT) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

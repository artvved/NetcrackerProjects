package validators.contractValidators;

import domain.contracts.Contract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

import java.time.LocalDate;

public class ContractStartDateValidator implements Validator {

    private final LocalDate MIN_DATE = LocalDate.of(1950,1,1);
    private final LocalDate WARNING_DATE = LocalDate.of(2012, 12, 12);


    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        LocalDate startDate = contract.getStartDate();

        if (startDate.isBefore(MIN_DATE)) {
            msg.setErrorFieldName("startDate");
            msg.setStatus(Status.ERROR);
        } else if (startDate.equals(WARNING_DATE)) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

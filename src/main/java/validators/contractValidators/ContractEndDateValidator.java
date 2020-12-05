package validators.contractValidators;

import domain.contracts.Contract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

import java.time.LocalDate;

public class ContractEndDateValidator implements Validator {

    private final LocalDate MIN_DATE = LocalDate.of(1950,1,1);
    private final LocalDate WARNING_DATE = LocalDate.of(2012, 12, 12);


    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        LocalDate startDate = contract.getStartDate();
        LocalDate endDate = contract.getEndDate();


        if (endDate.isBefore(MIN_DATE) ||
                endDate.isBefore(startDate)) {
            msg.setErrorFieldName("endDate");
            msg.setStatus(Status.ERROR);
            msg.setMessage("Error in validating "+msg.getErrorFieldName());
        } else if (endDate.equals(WARNING_DATE)) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

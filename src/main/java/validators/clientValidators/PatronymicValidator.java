package validators.clientValidators;

import domain.clients.Client;
import domain.contracts.Contract;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class PatronymicValidator implements Validator {
    private final int MAX_PATRONYMIC_LEN = 30;

    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        Client client = contract.getClient();
        String patronymic = client.getPatronymic();
        if (patronymic == null)
        {
            msg.setStatus(Status.OK);
            return msg;
        }
        if (patronymic.isEmpty() ||
                patronymic.length() > MAX_PATRONYMIC_LEN) {
            msg.setErrorFieldName("patronymic");
            msg.setStatus(Status.ERROR);
        } else if (patronymic.length() == MAX_PATRONYMIC_LEN) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

package validators.contractValidators.digitalTVContractValidators;

import domain.contracts.Contract;
import domain.contracts.DigitalTVContract;
import domain.contracts.WiredInternetContract;
import domain.contracts.util.TVChannel;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

import java.util.List;

public class ChannelsPackageValidator implements Validator {

    @Override
    public ValidationResultMessage validate(Contract contract) {
        ValidationResultMessage msg = new ValidationResultMessage();
        List<TVChannel> channelsPackage = ((DigitalTVContract) contract).getChannelsPackage();

        if (channelsPackage == null ||
            channelsPackage.isEmpty()) {
            msg.setStatus(Status.WARNING);
        } else {
            msg.setStatus(Status.OK);
        }
        return msg;
    }
}

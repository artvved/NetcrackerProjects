package validators.clientValidators;

import domain.clients.Client;
import domain.contracts.Contract;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class LastNameValidatorTest extends TestCase {
    @Test
    public void testValidate() {
        Validator v=new LastNameValidator();
        Contract c=new Contract();
        Client cl=new Client();
        cl.setLastName("1");
        c.setClient(cl);
        ValidationResultMessage m=v.validate(c);
        Assert.assertEquals(Status.OK,m.getStatus());
    }
}
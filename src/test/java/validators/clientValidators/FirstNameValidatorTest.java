package validators.clientValidators;

import domain.clients.Client;
import domain.contracts.Contract;
import fileHandlers.CSVInfoLoader;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import repository.BubbleRepositorySorter;
import repository.ContractRepository;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;

public class FirstNameValidatorTest extends TestCase {

    @Test
    public void testValidate() {
        Validator v=new FirstNameValidator();
        Contract c=new Contract();
        Client cl=new Client();
        cl.setFirstName("1");
        c.setClient(cl);
        ValidationResultMessage m=v.validate(c);
        Assert.assertEquals(Status.OK,m.getStatus());

        cl.setFirstName("11111111111111111120");//20
        m=v.validate(c);
        Assert.assertEquals(Status.WARNING,m.getStatus());

        cl.setFirstName("111111111111111111121");//21
        m=v.validate(c);
        Assert.assertEquals(Status.ERROR,m.getStatus());
        Assert.assertEquals("firstName",m.getErrorFieldName());
    }

}
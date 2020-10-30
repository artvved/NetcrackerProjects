import domain.clients.Client;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class TestClient {
    @Test
    public void testGetAge(){
        Client client1=new Client();
        client1.setBirthDate(LocalDate.of(2000,1,1));
        Client client2=new Client();
        client2.setBirthDate(LocalDate.of(2000,1,1));

        Assert.assertEquals(client1.getAge(),client2.getAge());
    }
}

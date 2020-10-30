import domain.contracts.Contract;
import org.junit.Assert;
import org.junit.Test;
import repository.ContractRepository;

import java.util.Optional;

public class TestContractRepository {
    @Test
    public void testAddByIdSimpleAdding() {
        ContractRepository contractRepository = new ContractRepository(new Contract[10]);
        Contract c = new Contract();
        c.setId((long) 1);
        contractRepository.addById(c);
        Assert.assertEquals(c, contractRepository.getById((long) 1).get());
    }
    @Test
    public void testAddByIdIncreaseAdding() {
        ContractRepository contractRepository = new ContractRepository(new Contract[10]);
        Contract c = new Contract();
        c.setId((long) 11);
        contractRepository.addById(c);
        Assert.assertEquals(20,contractRepository.getAll().length);
    }

    @Test
    public void testDeleteById() {
        ContractRepository contractRepository = new ContractRepository(new Contract[10]);
        Contract c = new Contract();
        c.setId((long) 1);
        contractRepository.addById(c);
        contractRepository.deleteById((long)1);
        Assert.assertEquals(Optional.empty(),contractRepository.getById((long) 1));
    }

}

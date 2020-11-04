package repository;

import domain.contracts.Contract;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class ContractRepositoryTest extends TestCase {
    @Test
    public void testAddSimpleAdding() {
        ContractRepository contractRepository = new ContractRepository(new Contract[10]);
        Contract c = new Contract();
        c.setId((long) 1);
        contractRepository.add(c);
        Assert.assertEquals(c, contractRepository.getById((long) 1).get());
    }

    @Test
    public void testAddIncreaseAdding() {
        ContractRepository contractRepository = new ContractRepository(new Contract[10]);
        for (int i = 0; i < 11; i++) {
            Contract c = new Contract();
            contractRepository.add(c);
        }

        Assert.assertEquals(20, contractRepository.getAll().length);
    }

    @Test
    public void testDeleteById() {
        ContractRepository contractRepository = new ContractRepository(new Contract[10]);
        Contract c = new Contract();
        c.setId((long) 1);
        contractRepository.add(c);
        contractRepository.deleteById((long) 1);
        Assert.assertEquals(Optional.empty(), contractRepository.getById((long) 1));
    }

    @Test
    public void testDeleteByIdShifting() {
        ContractRepository contractRepository = new ContractRepository(new Contract[10]);
        for (int i = 0; i < 3; i++) {
            Contract c = new Contract();
            c.setId((long) i);
            contractRepository.add(c);
        }

        contractRepository.deleteById((long) 1);
        Assert.assertEquals(2, contractRepository.getOccupancy());
    }
    @Test
    public void testDeleteByIdShifting1() {
        ContractRepository contractRepository = new ContractRepository(new Contract[3]);
        for (int i = 0; i < 3; i++) {
            Contract c = new Contract();
            c.setId((long) i);
            contractRepository.add(c);
        }

        contractRepository.deleteById((long) 1);
        Assert.assertEquals(2, contractRepository.getOccupancy());
    }
}
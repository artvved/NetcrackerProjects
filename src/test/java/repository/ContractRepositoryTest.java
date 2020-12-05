package repository;

import domain.contracts.Contract;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ContractRepositoryTest extends TestCase {
    @Test
    public void testAddSimpleAdding() {
        ContractRepository contractRepository = new ContractRepository(new Contract[3],
                new BubbleRepositorySorter());
        Contract c = new Contract();
        c.setId((long) 1);
        contractRepository.add(c);
        Assert.assertEquals(c, contractRepository.getById((long) 1).get());
    }

    @Test
    public void testAddIncreaseAdding() {
        ContractRepository contractRepository = new ContractRepository(new Contract[3],
                new BubbleRepositorySorter());
        for (int i = 0; i < 11; i++) {
            Contract c = new Contract();
            contractRepository.add(c);
        }

        Assert.assertEquals(20, contractRepository.getAll().length);
    }

    @Test
    public void testDeleteById() {
        ContractRepository contractRepository = new ContractRepository(new Contract[3],
                new BubbleRepositorySorter());
        Contract c = new Contract();
        c.setId((long) 1);
        contractRepository.add(c);
        contractRepository.deleteById((long) 1);
        Assert.assertEquals(Optional.empty(), contractRepository.getById((long) 1));
    }

    @Test
    public void testDeleteByIdShifting() {
        ContractRepository contractRepository = new ContractRepository(new Contract[3],
                new BubbleRepositorySorter());
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
        ContractRepository contractRepository = new ContractRepository(new Contract[3],
                new BubbleRepositorySorter());
        for (int i = 0; i < 3; i++) {
            Contract c = new Contract();
            c.setId((long) i);
            contractRepository.add(c);
        }

        contractRepository.deleteById((long) 1);
        Assert.assertEquals(2, contractRepository.getOccupancy());
    }
    @Test
    public void testFindById(){
        ContractRepository contractRepository = new ContractRepository(new Contract[3],
                new BubbleRepositorySorter());
        for (int i = 0; i < 3; i++) {
            Contract c = new Contract();
            c.setId((long) i);
            contractRepository.add(c);
        }
        ContractRepository res=contractRepository.find(new Predicate<Contract>() {
            @Override
            public boolean test(Contract contract) {
                return contract.getId()==1;
            }
        });
        Assert.assertEquals(1, (int)(long)res.getById((long)1).get().getId());
    }
    @Test
    public void testFindByNumber(){
        ContractRepository contractRepository = new ContractRepository(new Contract[3],
                new BubbleRepositorySorter());
        for (int i = 0; i < 3; i++) {
            Contract c = new Contract();
            c.setId((long) i);
            c.setNumber(1);
            contractRepository.add(c);
        }
        ContractRepository res=contractRepository.find(new Predicate<Contract>() {
            @Override
            public boolean test(Contract contract) {
                return contract.getNumber()==1;
            }
        });
        for (int i = 0; i < res.getOccupancy(); i++) {
            Assert.assertEquals(1, (int)(long)res.getById((long)i).get().getNumber());
        }
        Assert.assertEquals(3, res.getOccupancy());


    }


}
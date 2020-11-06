package repository;

import domain.contracts.Contract;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class BubbleRepositorySorterTest extends TestCase {

    @Test
    public void testRepositoryBubbleSortNumbers() {
        ContractRepository contractRepository = new ContractRepository(new Contract[3]);
        for (int i = 0; i < 3; i++) {
            Contract c = new Contract();
            c.setId((long) i);
            c.setNumber(2 - i);
            contractRepository.add(c);
        }
        BubbleRepositorySorter brs = new BubbleRepositorySorter();
        contractRepository.sort(brs, new Comparator<Contract>() {
            @Override
            public int compare(Contract contract, Contract t1) {
                if (contract.getNumber() < t1.getNumber()) {
                    return -1;
                } else if (contract.getNumber() == t1.getNumber()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        for (int i = 0; i < contractRepository.getAll().length; i++) {
            Assert.assertEquals(i, (int) (long) contractRepository.getAll()[i].getNumber());
        }
    }

}
package fileHandlers;

import domain.clients.Client;
import domain.clients.util.Gender;
import domain.contracts.Contract;
import domain.contracts.WiredInternetContract;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import repository.BubbleRepositorySorter;
import repository.ContractRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CSVInfoLoaderTest extends TestCase {

    private ContractRepository generate(){
        ContractRepository contractRepository = new ContractRepository(new Contract[3],
                new BubbleRepositorySorter());
        for (int i = 0; i < 4; i++) {
            Client client=new Client((long)i,"fn"+i,"ln"+i, Gender.MALE,LocalDate.now(),(long)1234567891+i);
            Contract c = new WiredInternetContract((long) i,i, LocalDate.now(),LocalDate.now(),client,50);
            contractRepository.add(c);
        }
        return contractRepository;
    }
    @Test
    public void testLoadingContractsFromCSV(){
        ContractRepository contractRepository=new ContractRepository(
                new Contract[100],
                new BubbleRepositorySorter());

        CSVInfoLoader loader= new CSVInfoLoader(contractRepository);

        try {
            loader.addContractsFromCSVFile("src/main/resources/contracts.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(5,contractRepository.getOccupancy());
    }


}
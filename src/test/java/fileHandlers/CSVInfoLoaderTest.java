package fileHandlers;

import domain.clients.Client;
import domain.clients.util.Gender;
import domain.contracts.Contract;
import domain.contracts.WiredInternetContract;
import junit.framework.TestCase;
import org.junit.Test;
import repository.BubbleRepositorySorter;
import repository.ContractRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CSVInfoLoaderTest extends TestCase {
    @Test
    public void testLoad(){
        ContractRepository contractRepository = new ContractRepository(new Contract[3],
                new BubbleRepositorySorter());
        for (int i = 0; i < 4; i++) {
            Client client=new Client((long)i,"fn"+i,"ln"+i, Gender.MALE,LocalDate.now(),(long)1234567891);
            Contract c = new WiredInternetContract((long) i,i, LocalDate.now(),LocalDate.now(),client,50);
            contractRepository.add(c);
        }
        CSVInfoLoader loader= new CSVInfoLoader(contractRepository);

        try {
            loader.addContractsFromCSVFile("src/main/resources/contracts.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
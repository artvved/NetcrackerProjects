package fileHandlers;

import domain.clients.Client;
import domain.clients.util.Gender;
import domain.contracts.CellularCommunicationContract;
import domain.contracts.Contract;
import domain.contracts.DigitalTVContract;
import domain.contracts.WiredInternetContract;
import domain.contracts.util.TVChannel;
import repository.ContractRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVInfoLoader {
    private ContractRepository contractRepository;

    public CSVInfoLoader(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;

    }

    public CSVInfoLoader() {
    }

    private Client getByPassport(Set<Client> clients, Long passport) {
        for (Client client : clients) {
            if (client.getPassport().equals(passport))
                return client;
        }
        return new Client();
    }

    private boolean clientAlreadyExists(Set<Client> clients, Long passport) {
        for (Client client : clients) {
            if (client.getPassport().equals(passport))
                return true;
        }
        return false;
    }

    private List<TVChannel> parseTVChannels(String[] channels) {
        List<TVChannel> list = new ArrayList<>();
        for (String channel : channels) {
            list.add(TVChannel.valueOf(channel));
        }
        return list;
    }

    private Set<Client> getSetOfExistingClients(){
        Set<Client> clients = new HashSet<>();
        Contract[] contracts = contractRepository.getAll();
        for (int i = 0; i < contractRepository.getOccupancy(); i++) {
            Client cur = contracts[i].getClient();
            clients.add(cur);
        }
        return clients;
    }

    public void addContractsFromCSVFile(String path) throws Exception {
        String line;
        String cvsSplitBy = ";";
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        boolean firstLine = true;
        Set<Client> clients = getSetOfExistingClients();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] stringContract = line.split(cvsSplitBy);
                Long passport = Long.parseLong(stringContract[4]);
                Client client;

                if (!clientAlreadyExists(clients, passport)) {
                    String[] fio = stringContract[1].split(" ");

                    String firstName = fio[0];
                    String lastName = fio[1];
                    String patronymic = null;
                    if (fio.length == 3)
                        patronymic = fio[2];

                    Long id = Long.parseLong(stringContract[0]);
                    Gender gender = Gender.valueOf(stringContract[2]);
                    LocalDate birth = LocalDate.parse(stringContract[3],formatter);
                    if (patronymic == null)
                        client = new Client(id, firstName, lastName, gender, birth, passport);
                    else
                        client = new Client(id, firstName, lastName, patronymic, gender, birth, passport);
                    clients.add(client);
                } else
                    client = getByPassport(clients, passport);

                Contract contract;
                Long contractId = Long.parseLong(stringContract[5]);
                int number = Integer.parseInt(stringContract[6]);
                LocalDate start = LocalDate.parse(stringContract[7],formatter);
                LocalDate end = LocalDate.parse(stringContract[8],formatter);

                switch (stringContract[9]) {
                    case "WiredInternetContract":
                        int maxSpeed = Integer.parseInt(stringContract[10]);
                        contract = new WiredInternetContract(contractId, number, start, end, client, maxSpeed);
                        break;
                    case "DigitalTVContract":
                        String[] stringChannels = stringContract[10].split(" ");
                        List<TVChannel> channels = parseTVChannels(stringChannels);
                        contract = new DigitalTVContract(contractId, number, start, end, client, channels);
                        break;
                    case "CellularCommunicationContract":
                        String[] stringContractParams = stringContract[10].split(" ");
                        int minutes = Integer.parseInt(stringContractParams[0]);
                        int megabytes = Integer.parseInt(stringContractParams[1]);
                        int sms = Integer.parseInt(stringContractParams[2]);
                        contract = new CellularCommunicationContract(contractId, number, start, end, client, minutes, megabytes, sms);
                        break;
                    default:
                        throw new Exception("No such variant of contract");
                }
                contractRepository.add(contract);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ContractRepository getContractRepository() {
        return contractRepository;
    }

    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }


}

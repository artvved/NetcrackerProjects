package fileHandlers;

import annotations.AutoInjectable;
import domain.clients.Client;
import domain.clients.util.Gender;
import domain.contracts.CellularCommunicationContract;
import domain.contracts.Contract;
import domain.contracts.DigitalTVContract;
import domain.contracts.WiredInternetContract;
import domain.contracts.util.TVChannel;
import domain.contracts.util.TVChannelEntity;
import repository.ContractRepository;
import validators.Status;
import validators.ValidationResultMessage;
import validators.Validator;
import validators.clientValidators.*;
import validators.contractValidators.ContractEndDateValidator;
import validators.contractValidators.ContractNumberValidator;
import validators.contractValidators.ContractStartDateValidator;
import validators.contractValidators.cellularCommunicationContractValidators.MegabytesAmountValidator;
import validators.contractValidators.cellularCommunicationContractValidators.MinutesAmountValidator;
import validators.contractValidators.cellularCommunicationContractValidators.SMSAmountValidator;
import validators.contractValidators.digitalTVContractValidators.ChannelsPackageValidator;
import validators.contractValidators.wiredInternetContractValidators.MaxInternetSpeedValidator;

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
    @AutoInjectable
    private List<Validator> validators;

    public CSVInfoLoader(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
        initValidators();
    }

    private void initValidators() {
        validators.add(new FirstNameValidator());
        validators.add(new LastNameValidator());
        validators.add(new PatronymicValidator());
        validators.add(new GenderValidator());
        validators.add(new BirthDateValidator());
        validators.add(new PassportValidator());

        validators.add(new ContractNumberValidator());
        validators.add(new ContractStartDateValidator());
        validators.add(new ContractEndDateValidator());
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

    private List<TVChannelEntity> parseTVChannels(String[] channels) {
        List<TVChannelEntity> list = new ArrayList<>();
        for (String channel : channels) {
            list.add(TVChannelEntity.builder()
                    .name(TVChannel.valueOf(channel))
                    .build());
        }
        return list;
    }

    private Set<Client> getSetOfExistingClients() {
        Set<Client> clients = new HashSet<>();
        Contract[] contracts = contractRepository.getAll();
        for (int i = 0; i < contractRepository.getOccupancy(); i++) {
            Client cur = contracts[i].getClient();
            clients.add(cur);
        }
        return clients;
    }

    /**
     * opens csv file, parses each line to contract and adds new contract to repository
     *
     * @param path path of csv file which we want to open
     * @throws Exception exception if name of contract in file does not match to available ones
     */
    public void addContractsFromCSVFile(String path) throws Exception {
        String line;
        String cvsSplitBy = ";";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
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
                    LocalDate birth = LocalDate.parse(stringContract[3], formatter);

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
                LocalDate start = LocalDate.parse(stringContract[7], formatter);
                LocalDate end = LocalDate.parse(stringContract[8], formatter);

                List<Validator> specList = new ArrayList<>();
                switch (stringContract[9]) {
                    case "WiredInternetContract":
                        int maxSpeed = Integer.parseInt(stringContract[10]);
                        contract = new WiredInternetContract(contractId, number, start, end, client, maxSpeed);
                        specList.add(new MaxInternetSpeedValidator());
                        validators.add(new MaxInternetSpeedValidator());
                        break;
                    case "DigitalTVContract":
                        String[] stringChannels = stringContract[10].split(" ");
                        List<TVChannelEntity> channels = parseTVChannels(stringChannels);
                        contract = new DigitalTVContract(contractId, number, start, end, client, channels);
                        specList.add(new ChannelsPackageValidator());
                        validators.add(new ChannelsPackageValidator());
                        break;
                    case "CellularCommunicationContract":
                        String[] stringContractParams = stringContract[10].split(" ");
                        int minutes = Integer.parseInt(stringContractParams[0]);
                        int megabytes = Integer.parseInt(stringContractParams[1]);
                        int sms = Integer.parseInt(stringContractParams[2]);
                        contract = new CellularCommunicationContract(contractId, number, start, end, client, minutes, megabytes, sms);
                        specList.add(new MegabytesAmountValidator());
                        validators.add(new MegabytesAmountValidator());
                        specList.add(new MinutesAmountValidator());
                        validators.add(new MinutesAmountValidator());
                        specList.add(new SMSAmountValidator());
                        validators.add(new SMSAmountValidator());
                        break;
                    default:
                        throw new Exception("No such variant of contract");
                }
                boolean contractIsOk=true;
                List<ValidationResultMessage> results = validate(contract);
                for (ValidationResultMessage msg:results) {
                    if (msg.getStatus().equals(Status.ERROR))
                        contractIsOk=false;
                        break;
                }
                if (contractIsOk)
                contractRepository.add(contract);
                validators.removeAll(specList);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private List<ValidationResultMessage> validate(Contract contract) {
        List<ValidationResultMessage> msgs = new ArrayList<>();
        for (Validator v : validators) {
            msgs.add(v.validate(contract));
        }
        return msgs;
    }

    public ContractRepository getContractRepository() {
        return contractRepository;
    }

    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<Validator> getValidators() {
        return validators;
    }
}

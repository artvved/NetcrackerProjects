package dbloader.dao;

import annotations.AutoInjectable;
import domain.clients.Client;
import domain.contracts.CellularCommunicationContract;
import domain.contracts.Contract;
import domain.contracts.DigitalTVContract;
import domain.contracts.WiredInternetContract;
import dbloader.ConnectionBuilder;
import domain.contracts.util.TVChannel;
import domain.contracts.util.TVChannelEntity;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO to manage Contract
 *
 * @author artvved
 */
public class ContractDAO implements DAO<Contract> {
    private static final String SELECT_ALL = "select * from contract";
    private static final String INSERT = "insert into contract (id, number,start_date, end_date, client_id, " +
            "contract_type, minutes, megabytes,sms, max_internet_speed) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    @AutoInjectable
    ConnectionBuilder connectionBuilder;

    @AutoInjectable
    ClientDAO clientDAO;

    @AutoInjectable
    TVChannelDAO tvChannelDAO;

    @Override
    public Optional<Contract> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Contract> getAll() {
        final List<Contract> result = new ArrayList<>();
        try (
                Connection connection = connectionBuilder.getConnection();
                Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            try (ResultSet resultSet = stmt.executeQuery(SELECT_ALL)) {
                while (resultSet.next()) {
                    result.add(
                            createContractFromResult(resultSet)
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(Contract model) {
        try (
                Connection connection = connectionBuilder.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT)
        ) {
                configStatementForContract(model, preparedStatement);
                preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveAll(List<Contract> models) {
        try (
                Connection connection = connectionBuilder.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT)
        ) {
            for (Contract contract : models) {
                configStatementForContract(contract, preparedStatement);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Return contract from ResultSet
     *
     * @param resultSet resultSet of contract query
     * @return contract from ResultSet
     * @throws SQLException when parameters are not read correctly
     */
    private Contract createContractFromResult(ResultSet resultSet) throws SQLException {
        Contract contract;
        String contractType = resultSet.getString("contract_type");
        if (contractType.equals(CellularCommunicationContract.class.getSimpleName())) {
            CellularCommunicationContract specContract = CellularCommunicationContract.builder()
                    .minutes((resultSet.getInt("minutes")))
                    .megabytes(resultSet.getInt("megabytes"))
                    .sms(resultSet.getInt("sms"))
                    .build();
            contract = specContract;
        } else if (contractType.equals(WiredInternetContract.class.getSimpleName())) {
            WiredInternetContract specContract = WiredInternetContract.builder()
                    .maxInternetSpeed(resultSet.getInt("max_internet_speed"))
                    .build();
            contract = specContract;
        } else {
            //digitalTV
            List<TVChannelEntity> list = tvChannelDAO.getChannelsByContractId(resultSet.getInt("id"));
            DigitalTVContract specContract = DigitalTVContract.builder()
                    .channelsPackage(list)
                    .build();

            contract = specContract;
        }
        contract.setId(resultSet.getLong("id"));
        contract.setStartDate(resultSet.getDate("start_date").toLocalDate());
        contract.setEndDate(resultSet.getDate("end_date").toLocalDate());
        contract.setNumber(resultSet.getInt("number"));
        // client insert
        Client client = clientDAO.get(
                (int) resultSet.getLong("client_id")
        ).orElseThrow(SQLException::new);
        contract.setClient(client);
        return contract;
    }

    /**
     * Config statement for contract to insert
     *
     * @param contract          contract to insert
     * @param preparedStatement insert statement
     * @throws SQLException when query params are wrong
     */
    private void configStatementForContract(Contract contract, PreparedStatement preparedStatement) throws SQLException {
        int count = 1;
        preparedStatement.setLong(count++, contract.getId());
        preparedStatement.setLong(count++, contract.getNumber());
        preparedStatement.setDate(count++, Date.valueOf(contract.getStartDate()));
        preparedStatement.setDate(count++, Date.valueOf(contract.getEndDate()));
        if (clientDAO.get(contract.getClient().getId().intValue()).isEmpty()) {
            clientDAO.save(contract.getClient());
        }
        preparedStatement.setLong(count++, contract.getClient().getId());
        preparedStatement.setString(count++, contract.getClass().getSimpleName());
        String simpleClassName = contract.getClass().getSimpleName();
        if (simpleClassName.equals(CellularCommunicationContract.class.getSimpleName())) {
            CellularCommunicationContract specContract = (CellularCommunicationContract) contract;
            preparedStatement.setInt(count++, specContract.getMinutes());
            preparedStatement.setInt(count++, specContract.getMegabytes());
            preparedStatement.setInt(count++, specContract.getSms());
            preparedStatement.setNull(count++, Types.INTEGER);

        } else if (simpleClassName.equals(WiredInternetContract.class.getSimpleName())) {
            WiredInternetContract specContract = (WiredInternetContract) contract;
            preparedStatement.setNull(count++, Types.INTEGER);
            preparedStatement.setNull(count++, Types.INTEGER);
            preparedStatement.setNull(count++, Types.INTEGER);
            preparedStatement.setInt(count++, specContract.getMaxInternetSpeed());

        } else {
            DigitalTVContract specContract = (DigitalTVContract) contract;
            preparedStatement.setNull(count++, Types.INTEGER);
            preparedStatement.setNull(count++, Types.INTEGER);
            preparedStatement.setNull(count++, Types.INTEGER);
            preparedStatement.setNull(count++, Types.INTEGER);


            /*
            List<TVChannelEntity> channelsByContractId = tvChannelDAO.getChannelsByContractId(Math.toIntExact(specContract.getId()));
            for (TVChannelEntity e:channelsByContractId){

            }
            if (tvChannelDAO.get(specContract.getChannelsPackage().getId().intValue()).isEmpty()) {
                channelPackageDAO.save(specContract.getChannelPackage());
            }*/
        }
    }

}

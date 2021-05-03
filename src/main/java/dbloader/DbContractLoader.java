package dbloader;


import annotations.AutoInjectable;
import dbloader.dao.ContractDAO;
import repository.ContractRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 * Save contracts to database and load from db
 *
 */
public class DbContractLoader implements ContractLoader {
    @AutoInjectable
    ContractDAO contractDAO;

    @AutoInjectable
    ConnectionBuilder connectionBuilder;

    @Override
    public void save(ContractRepository contractRepository) {
        clearDb();
        contractDAO.saveAll(Arrays.asList(contractRepository.getAll().clone()));
    }

    @Override
    public void load(ContractRepository contractRepository) {
        contractRepository.clear();
        contractRepository.addAll(contractDAO.getAll());
    }

    /**
     * for clear db before save contracts
     */
    private void clearDb() {
        try (
                Connection connection = connectionBuilder.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate("truncate contract; truncate client cascade;truncate TVChannel cascade");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

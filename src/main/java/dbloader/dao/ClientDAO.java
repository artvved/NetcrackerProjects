package dbloader.dao;

import annotations.AutoInjectable;
import domain.clients.Client;
import dbloader.ConnectionBuilder;
import domain.clients.util.Gender;


import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * DAO to manage Client
 *
 * @author artvved
 */
public class ClientDAO implements DAO<Client> {
    private static final String SELECT_ID = "select * from client where id=?";
    private static final String INSERT = "insert into client (id, first_name,last_name,patronymic, birth_date, gender, passport) values (?, ?, ?, ?, ?,?,?)";
    @AutoInjectable
    ConnectionBuilder connectionBuilder;


    @Override
    public Optional<Client> get(int id) {
        try (
                Connection connection = connectionBuilder.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(Client.builder()
                        .id(resultSet.getLong("id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .patronymic(resultSet.getString("patronymic"))
                        .birthDate(resultSet.getDate("birth_date").toLocalDate())
                        .gender(Gender.valueOf(resultSet.getString("gender").toUpperCase()))
                        .passport(resultSet.getLong("passport"))
                        .build());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Client> getAll() {
        return null;
    }

    @Override
    public void save(Client model) {
        try (
                Connection connection = connectionBuilder.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT)
        ) {
            int count = 1;
            preparedStatement.setLong(count++, model.getId());
            preparedStatement.setString(count++, model.getFirstName());
            preparedStatement.setString(count++, model.getLastName());
            preparedStatement.setString(count++, model.getPatronymic());
            preparedStatement.setDate(count++, Date.valueOf(model.getBirthDate()));
            preparedStatement.setString(count++, model.getGender().toString());
            preparedStatement.setLong(count++, model.getPassport());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveAll(List<Client> models) {
        for (Client c : models) {
            save(c);
        }
    }


}

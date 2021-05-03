package dbloader.dao;

import annotations.AutoInjectable;
import domain.contracts.util.TVChannel;
import domain.contracts.util.TVChannelEntity;
import dbloader.ConnectionBuilder;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO to manage ChannelPackage
 * @author artvved
 */
public class TVChannelDAO implements DAO<TVChannelEntity> {
    private static final String SELECT_ID = "select * from TVChannel where id=?";
    private static final String SELECT_CHANNELS_BY_CONTRACT_ID = "select * from TVChannel where contract_id=?";
    private static final String INSERT = "insert into TVChannel (id, name,contract_id) values (?, ?,?)";
    @AutoInjectable
    ConnectionBuilder connectionBuilder;
    @AutoInjectable
    ContractDAO contractDAO;

    @Override
    public Optional<TVChannelEntity> get(int id) {
        try (
                Connection connection = connectionBuilder.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                return Optional.of(TVChannelEntity.builder()
                        .id(resultSet.getLong("id"))
                        .name(TVChannel.valueOf(resultSet.getString("name")))
                        .contract(contractDAO.get((int) resultSet.getLong("contract_id")).get())
                        .build());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }


    public List<TVChannelEntity> getChannelsByContractId(int id) {
        List<TVChannelEntity> list=new ArrayList<>();
        try (
                Connection connection = connectionBuilder.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CHANNELS_BY_CONTRACT_ID)
        ) {
            preparedStatement.setInt(1, id);//?
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                list.add((TVChannelEntity.builder()
                        .id(resultSet.getLong("id"))
                        .name(TVChannel.valueOf(resultSet.getString("name")))
                        .contract(contractDAO.get((int) resultSet.getLong("contract_id")).get())
                        .build()));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<TVChannelEntity> getAll() {
        return null;
    }

    @Override
    public void save(TVChannelEntity model) {
        try (
                Connection connection = connectionBuilder.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT)
        ){
            int count = 1;
            preparedStatement.setLong(count++, model.getId());
            preparedStatement.setString(count++, String.valueOf(model.getName()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveAll(List<TVChannelEntity> models) {

    }


}

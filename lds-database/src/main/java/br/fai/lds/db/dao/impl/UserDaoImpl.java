package br.fai.lds.db.dao.impl;

import br.fai.lds.db.connection.ConnectionFactory;
import br.fai.lds.db.dao.UserDao;
import br.fai.models.entities.UserModel;
import br.fai.models.enums.UserType;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao<UserModel> {
    @Override
    public List<UserModel> find() {

        List<UserModel> users = new ArrayList<UserModel>();

        final String sql = "SELECT * FROM usuario";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UserModel user = new UserModel();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("nome_usuario"));
                user.setFullName(resultSet.getString("nome_completo"));
                user.setEmail(resultSet.getString("email"));
                user.setType(UserType.valueOf(resultSet.getString("tipo")));
                user.setActive(resultSet.getBoolean("esta_ativo"));
                user.setLastModified(resultSet.getTimestamp("ultima_modificacao"));
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement, connection, resultSet);
        }

        return users;
    }

    @Override
    public UserModel findById(int id) {

        UserModel user = null;

        final String sql = "SELECT * FROM usuario u WHERE u.id = ?;";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return user;
            }

            user = new UserModel();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("nome_usuario"));
            user.setFullName(resultSet.getString("nome_completo"));
            user.setEmail(resultSet.getString("email"));
            user.setType(UserType.valueOf(resultSet.getString("tipo")));
            user.setActive(resultSet.getBoolean("esta_ativo"));
            user.setLastModified(resultSet.getTimestamp("ultima_modificacao"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement, connection, resultSet);
        }

        return user;
    }

    @Override
    public int create(UserModel entity) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int id = -1;

        final String sql = "INSERT INTO usuario (nome_completo, senha, nome_usuario," +
                " email, tipo, esta_ativo, criado_em, criador_por, ultima_modificacao)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {

            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, entity.getFullName());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getUsername());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, UserType.USUARIO.toString());
            preparedStatement.setBoolean(6, entity.getActive());
            preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(8, entity.getUsername());
            preparedStatement.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

            preparedStatement.execute();

            resultSet = preparedStatement.getGeneratedKeys();


            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

            connection.commit();
            return id;
        } catch (Exception e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }

            return id;

        } finally {
            ConnectionFactory.close(preparedStatement, connection, resultSet);
        }


    }

    @Override
    public boolean update(UserModel entity) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        final String sql = "UPDATE usuario SET nome_completo = ?, " +
                "ultima_modificacao = ?, " +
                "email = ? " +
                "WHERE " +
                "id = ? ;";

        try {

            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, entity.getFullName());
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setInt(4, entity.getId());

            preparedStatement.execute();
            connection.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }

            return false;

        } finally {
            ConnectionFactory.close(preparedStatement, connection);
        }
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;

        final String sql = "DELETE FROM usuario u WHERE u.id = ?;";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            connection.setAutoCommit(false);
            preparedStatement.execute();
            connection.commit();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        } finally {
            ConnectionFactory.close(preparedStatement, connection);
        }

        return result;
    }

    @Override
    public UserModel validateUsernameAndPassword(String username, String password) {

        UserModel user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String sql = "SELECT * FROM usuario u WHERE u.nome_usuario = ? AND u.senha = ?;";

        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return user;
            }

            user = new UserModel();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("nome_usuario"));
            user.setFullName(resultSet.getString("nome_completo"));
            user.setEmail(resultSet.getString("email"));
            user.setType(UserType.valueOf(resultSet.getString("tipo")));
            user.setActive(resultSet.getBoolean("esta_ativo"));
            user.setLastModified(resultSet.getTimestamp("ultima_modificacao"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement, connection, resultSet);
        }

        return user;
    }
}

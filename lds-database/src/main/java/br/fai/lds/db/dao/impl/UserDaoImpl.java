package br.fai.lds.db.dao.impl;

import br.fai.lds.db.connection.ConnectionFactory;
import br.fai.lds.db.dao.UserDao;
import br.fai.models.entities.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao<UserModel> {
    @Override
    public List<UserModel> find() {
        return null;
    }

    @Override
    public UserModel findById(int id) {
        return null;
    }

    @Override
    public int create(UserModel entity) {
        return 0;
    }

    @Override
    public boolean update(UserModel entity) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public UserModel validateUsernameAndPassword(String username, String password) {

        UserModel user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final String sql = "SELECT * FROM usuario u WHERE u.nome_usuario = ? AND u.senha = ?;";

        connection = ConnectionFactory.getConnection();
        try {
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
            user.setActive(resultSet.getBoolean("esta_ativo"));
            user.setLastModified(resultSet.getTimestamp("ultima_modificacao"));
            user.setLastModifiedBy(resultSet.getString("modificado_por"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(preparedStatement, connection, resultSet);
        }

        return user;
    }
}

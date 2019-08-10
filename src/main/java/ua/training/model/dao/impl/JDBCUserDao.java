package ua.training.model.dao.impl;

import ua.training.model.dao.UserDao;
import ua.training.model.dao.mapper.UserMapper;
import ua.training.model.entity.User;

import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserDao {
    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(User entity) {

    }

    @Override
    public Optional<User> findById(int id) throws SQLException {
        Optional<User> user = Optional.empty();
        PreparedStatement stmt = connection.prepareStatement(
                "select * from user where user_id = (?)");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        UserMapper userMapper = new UserMapper();

        if (rs.next()) {
            user = Optional.of(userMapper.extractFromResultSet(rs));
        }

        stmt.close();
        connection.close();
        return user;
    }


    @Override
    public List<User> findAll() throws SQLException {
        Map<Integer, User> users = new HashMap<>();

        final String query = "" +
                " select * from user";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        UserMapper userMapper = new UserMapper();

        while (rs.next()) {
            User user = userMapper
                    .extractFromResultSet(rs);
            userMapper
                    .makeUnique(users, user);
        }
        return new ArrayList<>(users.values());
    }

    @Override
    public void update(User user) throws SQLException {
        int id = user.getId();
        String role = user.getRole().toString();
        PreparedStatement stmt = connection.prepareStatement(
                "update user set role = ?" +
                        " where user_id = ?");
        stmt.setInt(2, id);
        stmt.setString(1, role);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "delete from user where user_id = (?)");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        stmt.close();
        connection.close();

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCount() throws SQLException {
        return 0;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> result = Optional.empty();
        try (PreparedStatement ps = connection.prepareCall("SELECT * FROM user WHERE email = ?")) {
            ps.setString(1, email);
            ResultSet rs;
            rs = ps.executeQuery();
            UserMapper mapper = new UserMapper();
            if (rs.next()) {
                result = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public void register(String name, String email, String pass) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "insert into user (name, email, password) values (?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, pass);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }
}
package ua.training.model.dao.mapper;

import ua.training.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setSurname(rs.getString("email"));
        user.setRoleFromString(rs.getString("role"));
        user.setPassword(rs.getString("password"));
        return user;
    }

    public void makeUnique(Map<Integer, User> cache,
                           User user) {
        cache.putIfAbsent(user.getId(), user);
        cache.get(user.getId());
    }
}

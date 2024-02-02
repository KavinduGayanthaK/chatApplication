package lk.ijse.dao;

import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    public List<User> getAllUsers() throws SQLException, ClassNotFoundException;
    public boolean saveUser(User user) throws SQLException, ClassNotFoundException;
}

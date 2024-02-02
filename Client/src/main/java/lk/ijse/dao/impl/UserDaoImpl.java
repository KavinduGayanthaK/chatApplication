package lk.ijse.dao.impl;


import lk.ijse.dao.UserDao;
import lk.ijse.entity.User;
import lk.ijse.utils.SQLUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =  SQLUtils.execute("SELECT * FROM users");
        ArrayList<User> userArrayList = new ArrayList<>();

        while (resultSet.next()) {
            userArrayList.add(new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
       return userArrayList;
    }

    @Override
    public boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        return SQLUtils.execute("INSERT INTO users VALUES(?,?,?)", user.getUserName(),user.getPassword(),user.getProfilePhoto());

    }
}

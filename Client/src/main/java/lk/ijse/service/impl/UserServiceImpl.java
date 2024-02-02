package lk.ijse.service.impl;



import lk.ijse.dao.UserDao;
import lk.ijse.dao.impl.UserDaoImpl;
import lk.ijse.dto.UserDto;
import lk.ijse.entity.User;
import lk.ijse.service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public ArrayList<UserDto> getAllUsers() throws SQLException, ClassNotFoundException {

        ArrayList<UserDto>  userArrayList = new ArrayList<>();
        for (User user: userDao.getAllUsers()) {
            userArrayList.add(new UserDto(
                    user.getUserName(),
                    user.getPassword(),
                    user.getProfilePhoto()
            ));
        }
        return userArrayList;
    }

    @Override
    public boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException {
       return userDao.saveUser(new User(userDto.getUserName(), userDto.getPassword(), userDto.getProfilePhoto()));
    }
}

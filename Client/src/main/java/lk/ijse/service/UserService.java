package lk.ijse.service;



import lk.ijse.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserService {
    public ArrayList<UserDto> getAllUsers() throws SQLException, ClassNotFoundException;
    public boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException;
}

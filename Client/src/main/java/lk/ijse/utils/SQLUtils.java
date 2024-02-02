package lk.ijse.utils;



import lk.ijse.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtils {
    public static <T> T execute(String sql, Object... ob) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < ob.length; i++) {
            preparedStatement.setObject((i+1),ob[i]);
        }

        if (sql.startsWith("SELECT")) {
            return (T) preparedStatement.executeQuery();
        }else {
            return (T)(Boolean)(preparedStatement.executeUpdate()>0);
        }
    }

}

package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.dto.UserDto;
import lk.ijse.service.UserService;
import lk.ijse.service.impl.UserServiceImpl;


import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    @FXML
    private JFXButton addNewUserBtn;
    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private JFXButton loginBtn;

    UserService userService = new UserServiceImpl();

    @FXML
    void loginBtnOnAction(ActionEvent event) {
        String userName;
        String password;

        try {
            for (UserDto user : userService.getAllUsers()) {
                userName = user.getUserName();
                password = user.getPassword();
                if (userName.equals(txtUserName.getText())) {
                    if (password.equals(txtPassword.getText())) {
                        ClientController.setProfile(user);
                        ClientController.setClientName(userName);
                        Stage stage = new Stage();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/client_form.fxml"))));
                        stage.show();

                        Stage stage1 = (Stage) loginBtn.getScene().getWindow();
                        stage1.close();
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addNewUserBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/addNewUser_form.fxml"))));
        stage.show();
    }
}

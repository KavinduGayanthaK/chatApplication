package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.UserDto;
import lk.ijse.service.UserService;
import lk.ijse.service.impl.UserServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class AddNewUserFormController {
    @FXML
    private JFXButton registerBtn;
    @FXML
    private ImageView profileImage;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    UserService userService = new UserServiceImpl();
    @FXML
    private AnchorPane root;
    private String imgPath =
            "C:\\Users\\Kavindu\\Documents\\GDSE67\\SECOND SEMESTER\\Intro duction to network programming\\java-socket\\Client\\src\\main\\resources\\asset\\icons8-add-camera-60.png";

    @FXML
    void AddImageOnMouseClick(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage primaryStage = (Stage) root.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            imgPath = selectedFile.getAbsolutePath();
        }
        addPhoto();
    }

    @FXML
    void registerBtnOnAction(ActionEvent event) throws IOException {
        boolean addOk=false;
        try {
            addOk=userService.saveUser(new UserDto(txtUserName.getText(),txtPassword.getText(),imgPath));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (addOk){
            imgPath="C:\\Users\\Kavindu\\Documents\\GDSE67\\SECOND SEMESTER\\Intro duction to network programming\\java-socket\\Client\\src\\main\\resources\\asset\\icons8-add-camera-60.png";
            addPhoto();
            txtUserName.clear();
            txtPassword.clear();
        }
    }

    private void addPhoto() throws IOException {
        assert imgPath != null;
        File imageFile = new File(imgPath);
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        byte[] imageBytes = new byte[(int) imageFile.length()];
        fileInputStream.read(imageBytes);
        fileInputStream.close();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        Image image = new Image(byteArrayInputStream);
        profileImage.setImage(image);
    }


}

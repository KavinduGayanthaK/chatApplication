package lk.ijse.controller;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.dto.TransferData;
import lk.ijse.dto.UserDto;
import lk.ijse.entity.User;
import lk.ijse.service.ClientSide;
import lk.ijse.service.UserService;
import lk.ijse.service.impl.UserServiceImpl;

import java.io.*;

public class ClientController {
    public VBox mainContainer;
    public TextField txtMessage;
    private static String clientName;
    private static UserDto profile = null;

    @FXML
    private Label clientLabel;
    @FXML
    private HBox meHbox;
    @FXML
    private Label sendMeLabel;
    @FXML
    private ImageView profileImage;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXButton emojiBtn;

    @FXML
    private JFXButton fileBtn;
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane emojiPane;


    ClientSide clientSide;

    public void initialize() {
        emojiPane.setVisible(false);
        if (!profile.getProfilePhoto().equals("asset/icons8-camera-96.png")) {
            File imageFile = new File(profile.getProfilePhoto());
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(imageFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            byte[] imageBytes = new byte[(int) imageFile.length()];
            try {
                fileInputStream.read(imageBytes);
                fileInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
            Image image = new Image(byteArrayInputStream);
            profileImage.setImage(image);

            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            clientLabel.setText(clientName);
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/client_form.fxml"));
            ClientController controller = loader.getController();
            clientSide = new ClientSide(controller);
            new Thread(clientSide).start();

            clientSide.valueProperty().addListener((observableValue, oldValue, newValue) -> {
                Label label = new Label(clientSide.getName() + ": " + newValue.getMessage());
                label.setStyle("-fx-background-color: #FFFFFF; " +
                        "-fx-padding: 10px;" +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-family: Arial; " +
                        "-fx-background-radius: 5");
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.getChildren().add(label);
                mainContainer.getChildren().add(hBox);
            });
        }
    }

    public void sendBtnOnAction(ActionEvent actionEvent) {
        try {
            clientSide.sendToClient(new TransferData(txtMessage.getText(), "", "ALL", "TEXT_MESSAGE"));
            Label label = new Label();
            label.setStyle("-fx-background-color: #075e54; " +
                    "-fx-padding: 10px;" +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-text-fill: #FFFFFF;" +
                    "-fx-font-family: Arial; " +
                    "-fx-background-radius: 5");
            label.setText("Me: " + txtMessage.getText());
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.getChildren().add(label);
            mainContainer.getChildren().add(hBox);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void setClientName(String name) {
        clientName = name;
    }

    @FXML
    void fileBtnOnAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        Stage primaryStage = (Stage) root.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        String filePath = null;

        if (selectedFile != null) {
            filePath = selectedFile.getAbsolutePath();
        }

        assert filePath != null;
        File imageFile = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(imageFile);
        byte[] imageBytes = new byte[(int) imageFile.length()];
        fileInputStream.read(imageBytes);
        fileInputStream.close();

        addPhoto("Me", imageBytes, "#7A8194", "CENTER_RIGHT");
        clientSide.sendImg(profile.getUserName(), imageBytes);
    }

    public void addPhoto(String fname, byte[] imageBytes, String colorCode, String alignment) {
        Platform.runLater(() -> {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefSize(180, 180);
            anchorPane.setStyle("-fx-background-color: " + colorCode + ";" +
                    "-fx-padding: 5px;" +
                    "-fx-background-radius: 5;");

            Label label = new Label(fname);
            label.setStyle("-fx-background-color: " + colorCode + ";" +
                    "-fx-padding: 0px;" +
                    "-fx-font-size: 12px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-text-fill: #FFFFFF;" +
                    "-fx-font-family: Arial; " +
                    "-fx-background-radius: 5;");

            AnchorPane.setTopAnchor(label, 2.0);
            AnchorPane.setLeftAnchor(label, 2.0);
            anchorPane.getChildren().add(label);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
            Image image = new Image(byteArrayInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(180);
            imageView.setFitHeight(180);
            AnchorPane.setTopAnchor(imageView, 20.0);
            AnchorPane.setRightAnchor(imageView, 0.0);
            anchorPane.getChildren().add(imageView);

            HBox messageContainer = new HBox(anchorPane);
            messageContainer.setAlignment(Pos.valueOf(alignment));
            mainContainer.getChildren().add(messageContainer);
            mainContainer.setSpacing(10);
            scrollPane.setVvalue(1.0);
        });
    }

    @FXML
    void emojiBtnOnAction(ActionEvent event) {
        emojiPane.setVisible(!emojiPane.isVisible());
    }

    @FXML
    void emoji1OnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDC4D" + " ");
    }

    @FXML
    void emoji2OnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE02" + " ");
    }

    @FXML
    void emoji3OnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE02" + " ");
    }

    @FXML
    void emoji4OnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE0A" + " ");
    }

    @FXML
    void emoji5OnAction(MouseEvent event) {
        txtMessage.appendText("\uD83D\uDE0D" + " ");
    }

    public static void setProfile(UserDto user) {
        profile = user;
    }

}

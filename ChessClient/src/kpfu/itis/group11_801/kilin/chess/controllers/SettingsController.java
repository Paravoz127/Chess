package kpfu.itis.group11_801.kilin.chess.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kpfu.itis.group11_801.kilin.chess.Main;

public class SettingsController {
    @FXML private TextField ip;
    @FXML private TextField port;
    @FXML private Button connectBtn;
    @FXML private Button menuBtn;

    public void connect() {
        try {
            NetWorkClient netWorkClient = new NetWorkClient(ip.getText(), Integer.parseInt(port.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Connected");
            alert.setContentText("Connected to " + ip.getText());
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problem with connection");
            alert.setContentText("Connection Error");
            alert.show();
        }
    }

    public void toMenu() throws Exception {
        Stage stage = Main.getPrimaryStage();
        stage.getScene().setRoot(FXMLLoader.load(Main.class.getResource("views/Menu.fxml")));
        stage.sizeToScene();
    }
}

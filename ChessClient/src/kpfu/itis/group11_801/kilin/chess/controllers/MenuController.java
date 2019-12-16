package kpfu.itis.group11_801.kilin.chess.controllers;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kpfu.itis.group11_801.kilin.chess.Main;
import kpfu.itis.group11_801.kilin.chess.models.Game;
import sun.nio.ch.Net;

import java.util.Optional;

public class MenuController {
    @FXML private Button settingsBtn;
    @FXML private Button randomBtn;
    @FXML private Button createBtn;
    @FXML private Button connectBtn;
    @FXML private Label errorMessage;

    public void settings() throws Exception {
        Stage stage = Main.getPrimaryStage();
        stage.getScene().setRoot(FXMLLoader.load(Main.class.getResource("views/Settings.fxml")));
        stage.sizeToScene();
    }

    public void randomGame() throws Exception {
        Stage stage = Main.getPrimaryStage();
        stage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("views/Game.fxml")), 880, 970 ));
        NetWorkClient.getCurrentNetwork().randomGame();
    }

    @FXML
    public void initialize() {
        errorMessage.visibleProperty().bind(NetWorkClient.hasConnection.not());
        randomBtn.disableProperty().bind(NetWorkClient.hasConnection.not());
        createBtn.disableProperty().bind(NetWorkClient.hasConnection.not());
        connectBtn.disableProperty().bind(NetWorkClient.hasConnection.not());
    }

    public void createRoom() throws Exception {
        Stage stage = Main.getPrimaryStage();
        stage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("views/Game.fxml")), 880, 970 ));
        NetWorkClient.getCurrentNetwork().createGame();
    }

    public void connectToRoom() throws Exception {
        Stage stage = Main.getPrimaryStage();
        stage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("views/Game.fxml")), 880, 970 ));
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Room number");
        dialog.setContentText("Enter room number");
        dialog.setHeaderText("Enter room number to connect");
        Optional<String> res = dialog.showAndWait();
        if (res.isPresent()) {
            try {
                NetWorkClient.getCurrentNetwork().connectGame(Integer.parseInt(res.get()));
                Game.getCurrentGame().setMessage("Enemy`s move");
            } catch (Exception e) {
                NetWorkClient.getCurrentNetwork().roomNotExist();
            }
        } else {
            NetWorkClient.getCurrentNetwork().roomNotExist();
        }
    }
}

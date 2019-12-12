package kpfu.itis.group11_801.kilin.chess.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import kpfu.itis.group11_801.kilin.chess.Main;

public class MenuController {
    @FXML private Button settingsBtn;
    @FXML private Button randomBtn;
    @FXML private Button createBtn;
    @FXML private Button connectBtn;

    public void settings() throws Exception {
        Stage stage = Main.getPrimaryStage();
        stage.getScene().setRoot(FXMLLoader.load(Main.class.getResource("views/Settings.fxml")));
        stage.sizeToScene();
    }

    public void randomGame() throws Exception {
        Stage stage = Main.getPrimaryStage();
        //stage.getScene().setRoot(Main.getGame());
        Scene scene = stage.getScene();
        stage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("views/Game.fxml"))));
        stage.setHeight(970);
    }
}

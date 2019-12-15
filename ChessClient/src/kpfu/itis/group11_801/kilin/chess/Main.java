package kpfu.itis.group11_801.kilin.chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kpfu.itis.group11_801.kilin.chess.controllers.NetWorkClient;
import kpfu.itis.group11_801.kilin.chess.controllers.NetWorkThread;
import sun.nio.ch.Net;

public class Main extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("views/Menu.fxml"));
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root, 880, 880));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        NetWorkClient.getCurrentNetwork().giveUp();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return stage;
    }
}

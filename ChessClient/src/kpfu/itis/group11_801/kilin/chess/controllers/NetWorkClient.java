package kpfu.itis.group11_801.kilin.chess.controllers;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import kpfu.itis.group11_801.kilin.chess.Main;
import kpfu.itis.group11_801.kilin.chess.models.Figure;
import kpfu.itis.group11_801.kilin.chess.models.Game;
import kpfu.itis.group11_801.kilin.chess.models.Team;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;

public class NetWorkClient {
    private static NetWorkClient currentNetwork;
    private Socket socket;
    private OutputStream writer;
    private InputStream reader;
    private BooleanProperty gameIsGoing = new SimpleBooleanProperty(false);
    private BooleanProperty hasRoom = new SimpleBooleanProperty(false);
    public static BooleanProperty hasConnection = new SimpleBooleanProperty(false);
    public static BooleanProperty yourMove = new SimpleBooleanProperty(false);

    public boolean isHasRoom() {
        return hasRoom.get();
    }

    public static void setYourMove(boolean yourMove) {
        NetWorkClient.yourMove.setValue(yourMove);
    }


    public NetWorkClient(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Connected: " + socket.getInetAddress() + ":" + socket.getPort());
        currentNetwork = this;
        writer = socket.getOutputStream();
        reader = socket.getInputStream();
        hasRoom.setValue(false);
        hasConnection.setValue(true);
        gameIsGoing.setValue(false);
    }

    public BooleanProperty gameIsGoingProperty() {
        return gameIsGoing;
    }

    public void setGameIsGoing(boolean gameIsGoing) {
        this.gameIsGoing.set(gameIsGoing);
    }

    public static NetWorkClient getCurrentNetwork() {
        return currentNetwork;
    }

    public void randomGame() {
        try {
            writer.write(5);
            hasRoom.setValue(true);
            int response = reader.read();
            if (response == 0) {
                new Game(Team.WHITE, GameController.getImages());
            } else {
                new Game(Team.BLACK, GameController.getImages());
                setGameIsGoing(true);
            }
            GameController.getMessageLabel().textProperty().bind(Game.getCurrentGame().messageProperty());
            if (response == 1) {
                Game.getCurrentGame().setMessage("Enemy`s move");
            }
            System.out.println("new game");
            new NetWorkThread(socket).start();
        } catch (Exception e) {
            disconnected();
        }
    }

    public void createGame() {
        try {
            writer.write(0);
            int key = reader.read();
            hasRoom.setValue(true);
            new Game(Team.WHITE, GameController.getImages());
            GameController.getMessageLabel().textProperty().bind(Game.getCurrentGame().messageProperty());
            Game.getCurrentGame().setMessage("Waiting of second player: your code " + key);
            System.out.println("new game");
            new NetWorkThread(socket).start();
        } catch (Exception e) {
            disconnected();
        }
    }

    public void roomNotExist() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("The room does not exist");
        alert.setContentText("Connection error");
        Stage stage = Main.getPrimaryStage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("views/Menu.fxml")), 880, 880));
        } catch (Exception e) {
            e.printStackTrace();
        }
        alert.show();

    }

    public void connectGame(int key) {
        try {
            writer.write(1);
            writer.write(key);
            if (reader.read() == 1) {
                hasRoom.setValue(true);
                new Game(Team.BLACK, GameController.getImages());
                GameController.getMessageLabel().textProperty().bind(Game.getCurrentGame().messageProperty());
                Game.getCurrentGame().setMessage("Enemy`s move");
                setGameIsGoing(true);
                System.out.println("new game");
                new NetWorkThread(socket).start();
            } else {
                roomNotExist();
            }

        } catch (Exception e) {
            disconnected();
        }
    }

    public void move(int x1, int y1, int x2, int y2){
        try {
            writer.write(2);
            writer.write(x1);
            writer.write(y1);
            writer.write(x2);
            writer.write(y2);
            yourMove.setValue(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void specialMove(int x, int y, Figure figure) {
        try {
            int figureCode = 0;
            switch (figure.getName()) {
                case "Horse":
                    figureCode = 3;
                    break;
                case "Elephant":
                    figureCode = 2;
                    break;
                case "Queen":
                    figureCode = 1;
                    break;
                case "Castle":
                    figureCode = 4;
                    break;
            }
            writer.write(3);
            writer.write(x);
            writer.write(y);
            writer.write(figureCode);
        } catch (Exception e) {
            disconnected();
        }
    }

    public void giveUp() {
        try {
            gameIsGoing.setValue(false);
            hasRoom.setValue(false);
            writer.write(4);
            Game.getCurrentGame().setMessage("You lose: you gived up");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quit() {
        hasRoom.setValue(false);
        yourMove.setValue(false);
        setGameIsGoing(false);
    }

    public void gameEnd() {
        try {
            setGameIsGoing(false);
            yourMove.setValue(false);
            hasRoom.setValue(false);
            writer.write(7);
        } catch (Exception e) {
            disconnected();
        }
    }

    public void disconnected() {
        gameIsGoing.setValue(false);
        yourMove.setValue(false);
        hasRoom.setValue(false);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection reset");
        alert.setHeaderText("Not connection to server");
        alert.setContentText("Connection reset");
        alert.show();
        hasConnection.setValue(false);
        currentNetwork = null;
        Stage stage = Main.getPrimaryStage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("views/Menu.fxml")), 880, 880));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHasRoom(boolean hasRoom) {
        this.hasRoom.setValue(hasRoom);
    }

    public BooleanProperty hasRoomProperty() {
        return hasRoom;
    }

    public void drawOffer() {
        try {
            writer.write(8);
        } catch (Exception e) {
            disconnected();
        }
    }

    public void draw() {
        try {
            writer.write(9);
            writer.write(0);
            setGameIsGoing(false);
            yourMove.setValue(false);
            hasRoom.setValue(false);
            Game.getCurrentGame().setMessage("Draw");
        } catch (Exception e) {
            disconnected();
        }
    }

    public void notDraw() {
        try {
            writer.write(9);
            writer.write(1);
        } catch (Exception e) {
            disconnected();
        }
    }
}

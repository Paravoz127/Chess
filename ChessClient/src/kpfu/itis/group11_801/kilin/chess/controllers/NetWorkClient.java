package kpfu.itis.group11_801.kilin.chess.controllers;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import kpfu.itis.group11_801.kilin.chess.models.Figure;
import kpfu.itis.group11_801.kilin.chess.models.Game;
import kpfu.itis.group11_801.kilin.chess.models.Team;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetWorkClient {
    private static NetWorkClient currentNetwork;
    private Socket socket;
    private OutputStream writer;
    private InputStream reader;
    private boolean hasRoom;
    public static BooleanProperty hasConnection = new SimpleBooleanProperty(false);
    public static BooleanProperty yourMove = new SimpleBooleanProperty(false);

    public boolean isHasRoom() {
        return hasRoom;
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
        hasRoom = false;
        hasConnection.setValue(true);
    }

    public static NetWorkClient getCurrentNetwork() {
        return currentNetwork;
    }

    public void randomGame() throws IOException {
        writer.write(5);
        hasRoom = true;
        int response = reader.read();
        if (response == 0) {
            new Game(Team.WHITE, GameController.getImages());
        } else {
            new Game(Team.BLACK, GameController.getImages());
        }
        GameController.getMessageLabel().textProperty().bind(Game.getCurrentGame().messageProperty());
        if (response == 1) {
            Game.getCurrentGame().setMessage("Enemy`s move");
        }
        System.out.println("new game");
        new NetWorkThread(socket).start();
    }

    public void disconnect() throws IOException {
        writer.write(-1);
    }

    public void move(int x1, int y1, int x2, int y2){
        try {
            Game game = Game.getCurrentGame();
            writer.write(2);
            writer.write(x1);
            writer.write(y1);
            writer.write(x2);
            writer.write(y2);
            yourMove.setValue(false);
            if (game.isShah(game.getCurrentTeam() == Team.WHITE ? Team.BLACK : Team.WHITE)) {
                game.setMessage("Enemy`s move: Shah");
            } else {
                game.setMessage("Enemy`s move");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void specialMove(int x1, int y1, int x2, int y2, Figure figure) throws IOException {
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
        Game game = Game.getCurrentGame();
        writer.write(3);
        writer.write(x1);
        writer.write(y1);
        writer.write(x2);
        writer.write(y2);
        yourMove.setValue(false);
        if (game.isShah(game.getCurrentTeam() == Team.WHITE ? Team.BLACK : Team.WHITE)) {
            game.setMessage("Enemy`s move: Shah");
        } else {
            game.setMessage("Enemy`s move");
        }
        writer.write(figureCode);
    }

    public void giveUp() {
        try {
            hasRoom = false;
            writer.write(4);
            Game.getCurrentGame().setMessage("You lose: you gived up");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quit() {
        hasRoom = false;
        yourMove.setValue(false);
    }

    public void setHasRoom(boolean hasRoom) {
        this.hasRoom = hasRoom;
    }
}

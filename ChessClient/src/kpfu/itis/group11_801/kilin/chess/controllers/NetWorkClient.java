package kpfu.itis.group11_801.kilin.chess.controllers;

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

public class NetWorkClient extends Thread {
    /**
     * -1   =>  disconnect
     * 0    =>  create room
     * 1    =>  connect to room
     * 2    =>  move
     * 3    =>  special move 1 - horse, 2 - elephant, 3 - queen, 4 - castle
     * 4    =>  give up
     * 5    =>  random game
     * 6    =>  game started if you are white
     */
    private static NetWorkClient currentNetwork;
    private Socket socket;
    private OutputStream writer;
    private InputStream reader;
    private boolean hasRoom;
    public static BooleanProperty hasConnection = new SimpleBooleanProperty(false);
    public static BooleanProperty yourMove = new SimpleBooleanProperty(false);

    @Override
    public void run() {
        try {
            int code;
            while (hasRoom) {
                code = reader.read();
                System.out.println(code);
                switch (code) {
                    case 2:
                        Game.getCurrentGame()
                                .getItem(reader.read(), reader.read())
                                .move(reader.read(), reader.read());
                        yourMove.setValue(true);
                        break;
                    case 6:
                        yourMove.setValue(true);
                        break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
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

    public NetWorkClient(String ip) throws IOException {
        this(ip, 3456);
    }

    public static NetWorkClient getCurrentNetwork() {
        return currentNetwork;
    }

    public void randomGame() throws IOException {
        writer.write(5);
        hasRoom = true;
        start();
        int response = reader.read();
        if (response == 0) {
            new Game(Team.WHITE, GameController.images);
        } else {
            new Game(Team.BLACK, GameController.images);
        }
        System.out.println("new game");
    }

    public void disconnect() throws IOException {
        writer.write(-1);
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

    public void specialMove(int x1, int y1, int x2, int y2, Figure figure) throws IOException {
        int figureCode = 0;
        switch (figure.getName()) {
            case "Horse":
                figureCode = 1;
                break;
            case "Elephant":
                figureCode = 2;
                break;
            case "Queen":
                figureCode = 3;
                break;
            case "Castle":
                figureCode = 4;
                break;
        }
        writer.write(3);
        writer.write(x1);
        writer.write(y1);
        writer.write(x2);
        writer.write(y2);
        writer.write(figureCode);
    }

    public void setHasRoom(boolean hasRoom) {
        this.hasRoom = hasRoom;
    }
}

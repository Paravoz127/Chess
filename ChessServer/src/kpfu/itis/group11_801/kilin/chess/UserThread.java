package kpfu.itis.group11_801.kilin.chess;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class UserThread extends Thread {
    /**
     * -1   =>  disconnect
     * 0    =>  create room
     * 1    =>  connect to room
     * 2    =>  move
     * 3    =>  special move 1 - horse, 2 - elephant, 3 - queen, 4 - castle
     * 4    =>  give up
     * 5    =>  random game
     */
    private Socket socket;
    private Room room;

    public Room getRoom() {
        return room;
    }

    public UserThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            int code = inputStream.read();
            while (code != -1) {
                System.out.println(code);
                switch(code) {
                    case 5:
                        room = RandomRoom.connect(this);
                        break;
                    case 2:
                        room.move(this, inputStream.read(), inputStream.read(), inputStream.read(), inputStream.read());
                        break;
                    case 4:
                        try {
                            room.giveUp(this);
                            clearRoom();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }

                code = inputStream.read();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(int message) throws IOException{
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(message);
        System.out.println(message);
    }

    public void clearRoom() {
        room = null;
    }



}

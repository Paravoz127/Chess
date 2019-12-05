package kpfu.itis.group11_801.kilin.chess;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    public static ArrayList<UserThread> users;

    public static void main(String [] args) throws IOException {
        users = new ArrayList<>();
        ServerSocket ss = new ServerSocket(3456);
        System.out.println("Server started at " + ss.getLocalPort());

        while(true) {
            UserThread userThread = new UserThread(ss.accept());
            userThread.start();
            users.add(userThread);
            System.out.println("Connected new User");
        }
    }
}

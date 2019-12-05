package kpfu.itis.group11_801.kilin.chess.controllers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetWorkClient {
    private NetWorkClient currentNetwork;
    private Socket socket;

    public NetWorkClient(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Connected: " + socket.getInetAddress() + ":" +socket.getPort());
        currentNetwork = this;
    }

    public NetWorkClient(String ip) throws IOException {
        this(ip, 3456);
    }

    public NetWorkClient getCurrentNetwork() {
        return currentNetwork;
    }
}

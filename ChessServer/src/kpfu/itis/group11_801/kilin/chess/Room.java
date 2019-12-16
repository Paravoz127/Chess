package kpfu.itis.group11_801.kilin.chess;

import java.io.IOException;

public class Room {
    private UserThread user1;
    private UserThread user2;

    public Room(UserThread user) {
        user1 = user;
    }

    public void addUser(UserThread userThread) {
        user2 = userThread;
    }

    public void move(UserThread sender, int x1, int y1, int x2, int y2) throws IOException {
        UserThread receiver = sender.equals(user1) ? user2 : user1;
        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
        receiver.sendMessage(2);
        receiver.sendMessage(x1);
        receiver.sendMessage(y1);
        receiver.sendMessage(x2);
        receiver.sendMessage(y2);
    }

    public void specialMove(UserThread sender, int x, int y, int figure) throws Exception {
        /**
         * Figure codes:
         * 1 - queen
         * 2 - elephant
         * 3 - horse
         * 4 - castle
         */
        UserThread receiver = sender.equals(user1) ? user2 : user1;
        receiver.sendMessage(3);
        receiver.sendMessage(x);
        receiver.sendMessage(y);
        receiver.sendMessage(figure);
    }

    public UserThread getUser1() {
        return user1;
    }

    public UserThread getUser2() {
        return user2;
    }

    public void giveUp(UserThread userThread) throws IOException{
            giveUpIfDisconnected(userThread);
            userThread.sendMessage(100);
    }

    public void giveUpIfDisconnected (UserThread userThread) throws IOException{
        UserThread receiver = userThread.equals(user1) ? user2 : user1;
        receiver.sendMessage(4);
        receiver.clearRoom();
    }

    public void gameEnd(UserThread userThread) throws IOException{
        UserThread receiver = userThread.equals(user1) ? user2 : user1;
        userThread.sendMessage(100);
        receiver.sendMessage(100);
        receiver.clearRoom();
    }

    public void drawOffer(UserThread userThread) throws IOException{
        UserThread receiver = userThread.equals(user1) ? user2 : user1;
        receiver.sendMessage(8);
    }

    public void draw(UserThread userThread)  throws IOException {
        UserThread receiver = userThread.equals(user1) ? user2 : user1;
        receiver.sendMessage(9);
        receiver.sendMessage(0);
        receiver.clearRoom();
        userThread.sendMessage(100);
        userThread.clearRoom();
    }

    public void notDraw(UserThread userThread) throws IOException {
        UserThread receiver = userThread.equals(user1) ? user2 : user1;
        receiver.sendMessage(9);
        receiver.sendMessage(1);
    }
}

package kpfu.itis.group11_801.kilin.chess;

import java.io.IOException;
import java.util.ArrayList;

public class RandomRoom extends Room {
    private static ArrayList<Room> rooms = new ArrayList<>();

    public RandomRoom(UserThread userThread) {
        super(userThread);
    }

    public synchronized static Room connect(UserThread userThread) throws Exception{
        if (rooms.isEmpty()) {
            Room room = new RandomRoom(userThread);
            rooms.add(room);
            userThread.sendMessage(0);
            return room;
        } else {
            Room room = rooms.get(0);
            room.addUser(userThread);
            rooms.remove(room);
            userThread.sendMessage(1);
            room.getUser1().sendMessage(6);
            return room;
        }
    }

    @Override
    public void giveUp(UserThread userThread) throws IOException {
        try {
            super.giveUp(userThread);
        } catch (NullPointerException e) {
            rooms.remove(userThread.getRoom());
        }
    }
}

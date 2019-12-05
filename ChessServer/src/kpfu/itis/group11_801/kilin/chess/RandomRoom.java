package kpfu.itis.group11_801.kilin.chess;

import java.util.ArrayList;

public class RandomRoom extends Room {
    private static ArrayList<Room> rooms;

    public RandomRoom(UserThread userThread) {
        super(userThread);
    }

    public synchronized static void connect(UserThread userThread) {
        if (rooms.isEmpty()) {
            rooms.add(new RandomRoom(userThread));
        } else {
            Room room = rooms.get(0);
            room.addUser(userThread);
            rooms.remove(room);
        }
    }
}

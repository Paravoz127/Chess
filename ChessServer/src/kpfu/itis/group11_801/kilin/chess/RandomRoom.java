package kpfu.itis.group11_801.kilin.chess;

import java.util.ArrayList;

public class RandomRoom extends Room {
    private static ArrayList<Room> rooms = new ArrayList<>();

    public RandomRoom(UserThread userThread) {
        super(userThread);
    }

    public synchronized static Room connect(UserThread userThread) {
        if (rooms.isEmpty()) {
            Room room = new RandomRoom(userThread);
            rooms.add(room);
            return room;
        } else {
            Room room = rooms.get(0);
            room.addUser(userThread);
            rooms.remove(room);
            return room;
        }
    }
}

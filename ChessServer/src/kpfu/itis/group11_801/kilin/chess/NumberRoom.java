package kpfu.itis.group11_801.kilin.chess;

import java.io.IOException;
import java.util.*;

public class NumberRoom extends Room{
    private static int countOfPool = 100;
    private static Stack<Integer> numberPool;
    private static Map<Integer, Room> rooms = new TreeMap<>();

    private int key;

    public NumberRoom(UserThread user, int key) {
        super(user);
        this.key = key;
    }

    private static void init() {
        if (numberPool == null) {
            numberPool = new Stack<>();
            for (int i = 100; i < countOfPool; i++) {
                numberPool.push(i);
            }
        }
    }

    public synchronized static Room createRoom(UserThread userThread) throws Exception {
        init();
        if (numberPool.empty()) {
            updatePool();
        }
        int key = numberPool.pop();
        Room room = new NumberRoom(userThread, key);
        rooms.put(key, room);
        userThread.sendMessage(key);
        return room;
    }

    public synchronized static Room connect(UserThread userThread, int id) throws Exception {
        init();
        Room room = rooms.get(id);
        if (room != null) {
            room.addUser(userThread);
            rooms.remove(id);
            numberPool.push(id);
            userThread.sendMessage(1);
            room.getUser1().sendMessage(6);
            return room;
        } else {
            userThread.sendMessage(10);
        }
        return null;
    }

    private static void updatePool() {
        for (int i = countOfPool; i < countOfPool * 2; i++) {
            numberPool.add(i);
        }
    }

    @Override
    public void giveUp(UserThread userThread) throws IOException {
        try {
            super.giveUp(userThread);
        } catch (NullPointerException e) {
            rooms.remove(key);
            numberPool.push(key);

        }
    }
}

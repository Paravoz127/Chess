package kpfu.itis.group11_801.kilin.chess.models;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;
import kpfu.itis.group11_801.kilin.chess.controllers.NetWorkClient;
import kpfu.itis.group11_801.kilin.chess.controllers.NetWorkThread;
import sun.nio.ch.Net;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Game {
    private static Game currentGame;
    private Item [][] table;
    private ArrayList<Item> items;
    private Team currentTeam;
    private King w_king;
    private King b_king;
    private StringProperty message = new SimpleStringProperty("Waiting of second player");

    public Game(Team team, Map<String, ImageView> images) {
        items = new ArrayList<>();
        currentGame = this;
        currentTeam = team;
        table = new Item[8][8];

        for (int i = 1; i <= 8; i++) {
            items.add(new Pawn(images.get("w_pawn" + i), Team.WHITE));
        }

        for (int i = 1; i <= 8; i++) {
            items.add(new Pawn(images.get("b_pawn" + i), Team.BLACK));
        }

        items.add(new Castle(images.get("w_castle1"), Team.WHITE));
        items.add(new Castle(images.get("w_castle2"), Team.WHITE));
        items.add(new Castle(images.get("b_castle1"), Team.BLACK));
        items.add(new Castle(images.get("b_castle2"), Team.BLACK));

        items.add(new Elephant(images.get("w_elephant1"), Team.WHITE));
        items.add(new Elephant(images.get("w_elephant2"), Team.WHITE));
        items.add(new Elephant(images.get("b_elephant1"), Team.BLACK));
        items.add(new Elephant(images.get("b_elephant2"), Team.BLACK));

        items.add(new Horse(images.get("w_horse1"), Team.WHITE));
        items.add(new Horse(images.get("w_horse2"), Team.WHITE));
        items.add(new Horse(images.get("b_horse1"), Team.BLACK));
        items.add(new Horse(images.get("b_horse2"), Team.BLACK));

        items.add(new Queen(images.get("w_queen"), Team.WHITE));
        items.add(new Queen(images.get("b_queen"), Team.BLACK));

        w_king = new King(images.get("w_king"), Team.WHITE);
        items.add(w_king);
        b_king = new King(images.get("b_king"), Team.BLACK);
        items.add(b_king);

    }

    public StringProperty messageProperty() {
        return message;
    }

    public void createItem(int x, int y, Item item) {
        table[x][y] = item;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public Item[][] getTable() {
        return table;
    }

    public Item getItem(int x, int y) {
        return table[x][y];
    }

    public void deleteElem(int x, int y) {
        if (table[x][y] != null) {
            items.remove(table[x][y]);
            table[x][y].destroy();
        }
        table[x][y] = null;
    }

    public void restoreElem(int x, int y, Item item) {
        items.add(item);
        table[x][y] = item;
        item.restore();
    }

    public boolean hasItem(int x, int y) {
        return getItem(x, y) != null;
    }

    public void moveItem(int x1, int y1, int x2, int y2) {
        Item item = getItem(x1, y1);
        table[x1][y1] = null;
        table[x2][y2] = item;
    }

    public boolean isAttackedField(int x, int y, Team team) {
        for (Item item : items) {
            if (item.team != team && item.canMove(x, y)) {
                return true;
            }
        }
        return false;
    }

    public Item whoAttacked(int x, int y, Team team) {
        for (Item item : items) {
            if (item.team != team && item.canMove(x, y)) {
                return item;
            }
        }
        return null;
    }

    public boolean isCheckMate(Team team) {
        King king;
        if (team == Team.WHITE) {
            king = w_king;
        } else {
            king = b_king;
        }

        int kingX = king.getX();
        int kingY = king.getY();

        if (!isShah(team)) {
            return false;
        }
        /**
         * if king can move
         */
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = kingX + i;
                int y = kingY + j;
                if (x >= 0 && y >= 0 && x <= 7 && y <= 7) {
                    if (king.canMove(x, y)) {
                        if (tryMove(king, x, y)) {
                            return false;
                        }
                    }
                }
            }
        }

        Item attacker = whoAttacked(king.getX(), king.getY(), team);

        int attackerX = attacker.getX();
        int attackerY = attacker.getY();

        /**
         * if enemy`s figure can be attacked
         */
        if (tryAllMove(king.team, attackerX, attackerY)) {
            return false;
        }

        /**
         * if we can close king from attacker
         */

        if (!(attacker instanceof Horse)) {
            if (attackerX == kingX) {
                int minY = attackerY > kingY ? kingY : attackerY;
                int maxY = attackerY < kingY ? kingY : attackerY;

                for (int i = minY + 1; i < maxY; i++) {
                    if (tryAllMove(king.team, kingX, i)) {
                        return false;
                    }
                }
            } else if (attackerY == kingY) {
                int minX = attackerX > kingX ? kingX : attackerX;
                int maxX = attackerX < kingX ? kingX : attackerX;

                for (int i = minX + 1; i < maxX; i++) {
                    if (tryAllMove(king.team, i, kingY)) {
                        return false;
                    }
                }
            } else {
                int minY = attackerY > kingY ? kingY : attackerY;
                int minX = attackerX > kingX ? kingX : attackerX;
                int maxX = attackerX < kingX ? kingX : attackerX;

                for (int i = 1; i <= maxX - minX; i++) {
                    if (tryAllMove(king.team, minX + i, minY + 1)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean tryAllMove(Team team, int x, int y) {
        ArrayList<Item> copyOfItems = new ArrayList<>(items);
        for (Item item : copyOfItems) {
            if (item.team == team) {
                if (item.canMove(x, y)) {
                    if(tryMove(item, x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean tryMove(Item item, int x, int y) {
        boolean res = true;
        if (item.canMove(x, y)) {
            Item removedItem = Game.getCurrentGame().getItem(x, y);
            Game.getCurrentGame().deleteElem(x, y);
            Game.getCurrentGame().moveItem(item.boardX, item.boardY, x, y);
            item.posX.set(Item.intToPos(x));
            item.posY.set(Item.intToPos(y));

            if (Game.getCurrentGame().isShah(item.team)) {
                res = false;
            }
            item.posX.set(Item.intToPos(item.boardX));
            item.posY.set(Item.intToPos(item.boardY));
            Game.getCurrentGame().moveItem(x, y, item.boardX, item.boardY);
            if (removedItem != null) {
                Game.getCurrentGame().restoreElem(x, y, removedItem);
            }
            return res;
        } else {
            return false;
        }
    }

    public boolean isDraw() {
        if (items.size() == 2) {
            return true;
        }

        if (items.size() == 3) {
            for (Item item : items) {
                if (item instanceof Horse || item instanceof Elephant) {
                    return true;
                }
            }
        }

        if (items.size() == 4) {
            Team team = null;
            for (Item item : items) {
                if (item instanceof Horse || item instanceof Elephant) {
                    if (team == null) {
                        team = item.team;
                    } else if (team != item.team) {
                        return true;
                    }
                }
            }
        }

        Team enemyTeam = currentTeam == Team.WHITE ? Team.BLACK : Team.WHITE;

        Team team = NetWorkClient.yourMove.get() ? currentTeam : enemyTeam;

        if (!isShah(team)) {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (tryAllMove(team, i, j)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean isShah(Team team) {
        King king;
        if (team == Team.WHITE) {
            king = w_king;
        } else {
            king = b_king;
        }
        return isAttackedField(king.getX(), king.getY(), team);
    }

    public void addItem(Item item) {
        table[item.getX()][item.getY()] = item;
        items.add(item);
    }

    public void setMessage(String s) {
        messageProperty().setValue(s);
    }
}

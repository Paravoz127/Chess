package kpfu.itis.group11_801.kilin.chess.models;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
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

    public boolean isShah(Team team) {
        King king;
        if (team == Team.WHITE) {
            king = w_king;
        } else {
            king = b_king;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (table[i][j] == null) {
                    System.out.print("null\t");
                } else {
                    System.out.print( table[i][j].getClass().getSimpleName() + "\t");
                }
            }
            System.out.println();
        }
        System.out.println(isAttackedField(king.getX(), king.getY(), team));
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

package kpfu.itis.group11_801.kilin.chess.models;
import javafx.scene.image.ImageView;

import java.util.Map;

public class Game {
    private static Game currentGame;
    private Item [][] table;
    private Team currentTeam;

    public Game(Team team, Map<String, ImageView> images) {
        currentGame = this;
        currentTeam = team;
        Pawn pawn;
        table = new Pawn[8][8];

        for (int i = 1; i <= 8; i++) {
            pawn = new Pawn(images.get("w_pawn" + i), Team.WHITE);
            table[pawn.getX()][pawn.getY()] = pawn;
        }

        for (int i = 1; i <= 8; i++) {
            pawn = new Pawn(images.get("b_pawn" + i), Team.BLACK);
            table[pawn.getX()][pawn.getY()] = pawn;
        }
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
            table[x][y].destroy();
        }
        table[x][y] = null;
    }

    public boolean hasItem(int x, int y) {
        return getItem(x, y) != null;
    }

    public void moveItem(int x1, int y1, int x2, int y2) {
        Item item = getItem(x1, y1);
        table[x1][y1] = null;
        table[x2][y2] = item;
    }
}

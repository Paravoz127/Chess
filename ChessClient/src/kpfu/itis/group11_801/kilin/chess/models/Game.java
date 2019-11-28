package kpfu.itis.group11_801.kilin.chess.models;

public class Game {
    private static Game currentGame;
    private Item [][] table;
    private Team currentTeam;

    public Game(Team team) {
        currentTeam = team;

        currentGame = this;
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

    public boolean hasItem(int x, int y) {
        return getItem(x, y) != null;
    }
}

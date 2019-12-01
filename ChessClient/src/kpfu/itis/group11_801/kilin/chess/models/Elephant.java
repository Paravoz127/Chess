package kpfu.itis.group11_801.kilin.chess.models;

import javafx.scene.image.ImageView;

public class Elephant extends Item {

    public Elephant(ImageView imageView, Team team) {
        super(imageView, team);
    }

    @Override
    public boolean canMove(int x, int y) {
        Game currentGame = Game.getCurrentGame();
        if (isEngaged(x, y)) {
            return false;
        }

        int delta = Math.abs(boardX - x);

        if (delta != Math.abs(boardY - y)) {
            return false;
        }

        int currentX, currentY;

        for (int i = 1; i < delta; i++) {
            if (x < boardX) {
                currentX = boardX - i;
            } else {
                currentX = boardX + i;
            }
            if (y < boardY) {
                currentY = boardY - i;
            } else {
                currentY = boardY + i;
            }
            if (currentGame.hasItem(currentX, currentY)) {
                return false;
            }
        }

        return true;
    }
}

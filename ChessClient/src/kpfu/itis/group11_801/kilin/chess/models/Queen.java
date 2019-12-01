package kpfu.itis.group11_801.kilin.chess.models;

import javafx.scene.image.ImageView;

public class Queen extends Item {

    public Queen (ImageView imageView, Team team) {
        super(imageView, team);
    }

    @Override
    public boolean canMove(int x, int y) {
        Game currentGame = Game.getCurrentGame();
        if (isEngaged(x, y)) {
            return false;
        }

        if(boardY == y) {
            if (x < boardX) {
                for (int i = boardX - 1; i > x; i--) {
                    if (currentGame.hasItem(i, y)) {
                        return false;
                    }
                }
            } else {
                for (int i = boardX + 1; i < x; i++) {
                    if (currentGame.hasItem(i, y)) {
                        return false;
                    }
                }
            }
            return true;
        } else if (boardX == x) {
            if (y < boardY) {
                for (int i = boardY - 1; i > y; i--) {
                    if (currentGame.hasItem(x, i)) {
                        return false;
                    }
                }
            } else {
                for (int i = boardY + 1; i < y; i++) {
                    if (currentGame.hasItem(x, i)) {
                        return false;
                    }
                }
            }
            return true;
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

package kpfu.itis.group11_801.kilin.chess.models;

import javafx.scene.image.ImageView;

import java.util.Currency;

public class Castle extends Item {
    private boolean moved = false;

    public Castle(ImageView imageView, Team team) {
        super(imageView, team);
    }

    @Override
    public boolean move(int x, int y) {
        if (super.move(x, y)) {
            moved = true;
            return true;
        }
        return false;
    }

    public boolean isMoved() {
        return moved;
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

        return false;
    }

}

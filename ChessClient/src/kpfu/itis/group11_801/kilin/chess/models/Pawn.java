package kpfu.itis.group11_801.kilin.chess.models;

import javafx.scene.image.ImageView;

public class Pawn extends Item {
    private boolean moved = false;

    public Pawn(ImageView imageView) {
        super(imageView);
    }

    @Override
    public boolean move(int x, int y) {
        if (super.move(x, y)) {
            moved = true;
            return true;
        }
        return false;
    }

    @Override
    boolean canMove(int x, int y) {
        if (boardX - x != 0) return false;
        if (!moved) {
            if (boardY - y == 2 || boardY - y == 1) {
                return true;
            }
        } else if (boardY - y == 1) {
            return true;
        }
        return false;
    }
}

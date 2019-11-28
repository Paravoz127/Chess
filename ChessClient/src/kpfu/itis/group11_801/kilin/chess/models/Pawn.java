package kpfu.itis.group11_801.kilin.chess.models;

import javafx.scene.image.ImageView;

public class Pawn extends Item {
    private boolean moved = false;

    public Pawn(ImageView imageView, Team team) {
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

    @Override
    public boolean canMove(int x, int y) {
        int factor = 1;
        if (team == Team.BLACK) {
            factor = -1;
        }

        if (Math.abs(boardX - x) == 1 && boardY - y == factor && Game.getCurrentGame().hasItem(x, y) &&
                Game.getCurrentGame().getItem(x, y).team != team) {
            return true;
        }
        else if (boardX - x != 0) {
            return false;
        }
        if (boardY - y == factor && !(Game.getCurrentGame().hasItem(x, y))) {
            return true;
        } else if (!moved) {
            if (boardY - y == factor * 2 && !(Game.getCurrentGame().hasItem(x, y))) {
                return true;
            }
        }
        return false;
    }
}

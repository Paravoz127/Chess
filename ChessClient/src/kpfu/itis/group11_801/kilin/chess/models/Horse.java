package kpfu.itis.group11_801.kilin.chess.models;

import javafx.scene.image.ImageView;

import static java.lang.Math.abs;

public class Horse extends Item {

    public Horse(ImageView imageView, Team team) {
        super(imageView, team);
    }

    @Override
    public boolean canMove(int x, int y) {
        Game currentGame = Game.getCurrentGame();
        if (isEngaged(x, y)) {
            return false;
        }

        if (abs(x - boardX) == 2 && abs(y - boardY) == 1 || abs(x - boardX) == 1 && abs(y - boardY) == 2) {
            return true;
        }

        return false;
    }

}

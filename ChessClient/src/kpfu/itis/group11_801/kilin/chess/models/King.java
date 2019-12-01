package kpfu.itis.group11_801.kilin.chess.models;

import javafx.scene.image.ImageView;
import static java.lang.Math.abs;

public class King extends Item {

    public King(ImageView imageView, Team team) {
        super(imageView, team);
    }

    @Override
    public boolean canMove(int x, int y) {
        Game currentGame = Game.getCurrentGame();
        if (isEngaged(x, y)) {
            return false;
        }

        if ((abs(x - boardX) == 1 || abs(x - boardX) == 0) && (abs(y - boardY) == 1 || abs(y - boardY) == 0)) {
            return true;
        }

        return false;

    }

}

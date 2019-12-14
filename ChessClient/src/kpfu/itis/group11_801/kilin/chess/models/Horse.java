package kpfu.itis.group11_801.kilin.chess.models;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static java.lang.Math.abs;

public class Horse extends Item {

    public Horse(ImageView imageView, Team team) {
        super(imageView, team);
    }


    @Override protected void animate(int x, int y) {
        if (Math.abs(x - boardX)== 2) {
            KeyValue kvx = new KeyValue(posX, intToPos(x));
            KeyFrame kfx = new KeyFrame(Duration.seconds(2.0 / SPEED), kvx);
            KeyValue kvy1 = new KeyValue(posY, posY.doubleValue());
            KeyValue kvy2 = new KeyValue(posY, intToPos(y));
            KeyFrame kfy1 = new KeyFrame(Duration.seconds(2.0 / SPEED), kvy1);
            KeyFrame kfy2 = new KeyFrame(Duration.seconds(3.0 / SPEED), kvy2);
            Timeline t = new Timeline(kfx, kfy1,  kfy2);
            t.play();
        } else {
            KeyValue kvy = new KeyValue(posY, intToPos(y));
            KeyFrame kfy = new KeyFrame(Duration.seconds(2.0 / SPEED), kvy);
            KeyValue kvx1 = new KeyValue(posX, posX.doubleValue());
            KeyValue kvx2 = new KeyValue(posX, intToPos(x));
            KeyFrame kfx1 = new KeyFrame(Duration.seconds(2.0 / SPEED), kvx1);
            KeyFrame kfx2 = new KeyFrame(Duration.seconds(3.0 / SPEED), kvx2);
            Timeline t = new Timeline(kfy, kfx1, kfx2);
            t.play();
        }
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

package kpfu.itis.group11_801.kilin.chess.models;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import kpfu.itis.group11_801.kilin.chess.controllers.NetWorkClient;

public abstract class Item {
    protected final static int OFFSET = 40;
    protected final static int SIZE = 100;
    protected final static int SPEED = 5;

    protected DoubleProperty posX;
    protected DoubleProperty posY;
    protected ImageView imageView;
    protected int boardX;
    protected int boardY;
    protected Team team;

    public Item(ImageView imageView, Team team) {
        posX = new SimpleDoubleProperty();
        posY = new SimpleDoubleProperty();
        posX.bindBidirectional(imageView.xProperty());
        posY.bindBidirectional(imageView.yProperty());
        boardX = posToInt(posX);
        boardY = posToInt(posY);
        this.imageView = imageView;
        this.team = team;

        if (Game.getCurrentGame().getCurrentTeam() == team) {
            imageView.setOnMousePressed(event -> {
                boardX = posToInt(posX);
                boardY = posToInt(posY);
                imageView.toFront();
            });
            imageView.setOnMouseDragged(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getX() >= OFFSET && event.getX() <= 8 * SIZE &&
                            event.getY() >= OFFSET && event.getY() <= 8 * SIZE) {
                        posX.set(event.getX() - SIZE / 2);
                        posY.set(event.getY() - SIZE / 2);
                    }
                }
            });
            imageView.setOnMouseReleased(event -> {
                int x = posToInt(posX);
                int y = posToInt(posY);
                int tmpX = boardX;
                int tmpY = boardY;
                move(x, y);
            });
        }
        Game.getCurrentGame().createItem(getX(), getY(), this);
    }

    public void destroy() {
        imageView.setVisible(false);
    }

    public void restore() {
        imageView.setVisible(true);
    }

    public boolean move(int x, int y) {
        if (canMove(x, y)) {
            Item item = Game.getCurrentGame().getItem(x, y);
            Game.getCurrentGame().deleteElem(x, y);
            Game.getCurrentGame().moveItem(boardX, boardY, x, y);
            if (team != Game.getCurrentGame().getCurrentTeam()) {
                imageView.toFront();
                animate(x, y);
            }
            posX.set(intToPos(x));
            posY.set(intToPos(y));


            if (Game.getCurrentGame().isShah(team)) {
                posX.set(intToPos(boardX));
                posY.set(intToPos(boardY));
                Game.getCurrentGame().moveItem(x, y, boardX, boardY);
                if (item != null) {
                    Game.getCurrentGame().restoreElem(x, y, item);
                }
                return false;
            } else {
                if (team == Game.getCurrentGame().getCurrentTeam()) {
                    NetWorkClient.getCurrentNetwork()
                            .move(boardX, boardY, x, y);
                }

                boardX = x;
                boardY = y;

                Game game = Game.getCurrentGame();
                if (team == game.getCurrentTeam()) {
                    if (game.isShah(game.getCurrentTeam() == Team.WHITE ? Team.BLACK : Team.WHITE)) {
                        game.setMessage("Enemy`s move: Check");
                    } else {
                        game.setMessage("Enemy`s move");
                    }
                } else {
                    if (game.isShah(game.getCurrentTeam())) {
                        game.setMessage("Your move: Check");
                    } else {
                        game.setMessage("Your move");
                    }
                }
                return true;
            }

        } else {
            posX.set(intToPos(boardX));
            posY.set(intToPos(boardY));
            return false;
        }

    }

    protected void animate(int x, int y) {
        double length = Math.sqrt((boardX - x) * (boardX - x) + (boardY - y) * (boardY - y));
        double time = length / SPEED;
        KeyValue kvx = new KeyValue(posX, intToPos(x));
        KeyValue kvy = new KeyValue(posY, intToPos(y));
        KeyFrame kf = new KeyFrame(Duration.seconds(time), kvx, kvy);
        Timeline t = new Timeline(kf);
        t.play();
    }

    public boolean isEngaged(int x, int y) {
        Game currentGame = Game.getCurrentGame();
        Item item = currentGame.getItem(x, y);
        if (boardY == y && boardX == x || (item != null && item.team == team)) {
            return true;
        }
        return false;
    }

    public abstract boolean canMove(int x, int y);

    public int getX() {
        return posToInt(posX);
    }

    public  int getY() {
        return posToInt(posY);
    }

    protected static int posToInt(DoubleProperty arg) {
        return (int)Math.round((arg.get() - OFFSET) / SIZE);
    }

    protected static double intToPos(int arg) {
        return arg * SIZE + OFFSET;
    }


}

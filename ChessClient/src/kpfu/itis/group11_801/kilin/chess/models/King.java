package kpfu.itis.group11_801.kilin.chess.models;

import javafx.scene.image.ImageView;
import static java.lang.Math.abs;

public class King extends Item {
    private boolean moved = false;

    public King(ImageView imageView, Team team) {
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
        Game game = Game.getCurrentGame();
        if (isEngaged(x, y)) {
            return false;
        }

        if ((abs(x - boardX) == 1 || abs(x - boardX) == 0) && (abs(y - boardY) == 1 || abs(y - boardY) == 0)) {
            return true;
        }

        if (!moved) {
            if (getY() == 0 && y == 0) {
                if (x == 6) {
                    if (game.getItem(6, 0) != null || game.getItem(5, 0) != null
                            || game.isShah(team) || game.isAttackedField(6, 0, team)
                            || game.isAttackedField(5, 0, team)) {
                        return false;
                    }

                    Item item = game.getItem(7, 0);
                    if (item instanceof Castle) {
                        Castle castle = (Castle)item;
                        if (!castle.isMoved()) {
                            moveCastle(castle, 5);
                            return  true;
                        }
                    }
                } else if (x == 2) {
                    if (game.getItem(2, 0) != null || game.getItem(3, 0) != null
                            || game.isShah(team) || game.isAttackedField(2, 0, team)
                            || game.isAttackedField(3, 0, team)) {
                        return false;
                    }
                    Item item = game.getItem(0, 0);
                    if (item instanceof Castle) {
                        Castle castle = (Castle)item;
                        if (!castle.isMoved()) {
                            moveCastle(castle, 3);
                            return  true;
                        }
                    }
                }
            } else if (y == 7){
                if (x == 6) {
                    if (game.getItem(6, 7) != null || game.getItem(5, 7) != null
                            || game.isShah(team) || game.isAttackedField(6, 7, team)
                            || game.isAttackedField(5, 7, team)) {
                        return false;
                    }
                    Item item = game.getItem(7, 7);
                    if (item instanceof Castle) {
                        Castle castle = (Castle)item;
                        if (!castle.isMoved()) {
                            moveCastle(castle, 5);
                            return  true;
                        }
                    }
                } else if (x == 2) {
                    if (game.getItem(2, 7) != null || game.getItem(3, 7) != null
                            || game.isShah(team) || game.isAttackedField(2, 7, team)
                            || game.isAttackedField(3, 7, team)) {
                        return false;
                    }
                    Item item = game.getItem(0, 7);
                    if (item instanceof Castle) {
                        Castle castle = (Castle)item;
                        if (!castle.isMoved()) {
                            moveCastle(castle, 3);
                            return  true;
                        }
                    }
                }
            }
        }

        return false;

    }

    private void moveCastle(Castle castle, int x) {
        castle.animate(x, castle.getY());
        Game.getCurrentGame().moveItem(castle.getX(), castle.getY(), x, castle.getY());
        castle.posX.set(intToPos(x));
        castle.boardX = posToInt(castle.posX);
        castle.boardY = posToInt(castle.posY);
    }

}

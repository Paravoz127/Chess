package kpfu.itis.group11_801.kilin.chess.models;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Optional;

public class Pawn extends Item {
    private boolean moved = false;

    public Pawn(ImageView imageView, Team team) {
        super(imageView, team);
    }

    @Override
    public boolean move(int x, int y) {
        if (super.move(x, y)) {
            moved = true;
            if (y == 0 || y == 7) {
                if (team == Game.getCurrentGame().getCurrentTeam()) {
                    ChoiceDialog<Figure> dialog;
                    if (team == Team.WHITE) {
                        dialog = new ChoiceDialog<>(Figure.WHITE_QUEEN, Figure.WHITE_QUEEN, Figure.WHITE_CASTLE, Figure.WHITE_ELEPHANT, Figure.WHITE_HORSE);
                    } else {
                        dialog = new ChoiceDialog<>(Figure.BLACK_QUEEN, Figure.BLACK_QUEEN, Figure.BLACK_CASTLE, Figure.BLACK_ELEPHANT, Figure.BLACK_HORSE);
                    }

                    dialog.setTitle("Select Figure");
                    dialog.setHeaderText("Select Figure:");
                    dialog.setContentText("Figure:");
                    Optional<Figure> result = Optional.empty();
                    while (!result.isPresent()){
                        result = dialog.showAndWait();
                    }
                    Figure figure = result.get();
                    Game.getCurrentGame().deleteElem(getX(), getY());
                    registryItem(figure);
                }

            }
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

    private void registryItem(Figure figure) {
        Item item = null;
        ImageView imageView = new ImageView(figure.getImage());
        imageView.setX(posX.get());
        imageView.setY(posY.get());
        imageView.setFitHeight(SIZE);
        imageView.setFitWidth(SIZE);
        switch (figure) {
            case BLACK_QUEEN:
            case WHITE_QUEEN:
                item = new Queen(imageView, team);
                break;
            case BLACK_HORSE:
            case WHITE_HORSE:
                item = new Horse(imageView, team);
                break;
            case BLACK_CASTLE:
            case WHITE_CASTLE:
                item = new Castle(imageView, team);
                break;
            case BLACK_ELEPHANT:
            case WHITE_ELEPHANT:
                item = new Elephant(imageView, team);
                break;
        }
        ((Pane)this.imageView.getParent()).getChildren().add(imageView);
        Game.getCurrentGame().addItem(item);

    }
}

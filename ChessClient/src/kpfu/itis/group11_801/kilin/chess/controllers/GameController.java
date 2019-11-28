package kpfu.itis.group11_801.kilin.chess.controllers;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import kpfu.itis.group11_801.kilin.chess.models.Pawn;
import kpfu.itis.group11_801.kilin.chess.models.Team;


public class GameController {
    private double x;
    private double y;

    @FXML private ImageView pawn1;
    @FXML private Pane pane;

    public void move() {

    }

    @FXML
    public void initialize() {
        Pawn pawn = new Pawn(pawn1);
    }
}

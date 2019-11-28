package kpfu.itis.group11_801.kilin.chess.controllers;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import kpfu.itis.group11_801.kilin.chess.models.Game;
import kpfu.itis.group11_801.kilin.chess.models.Team;

import java.util.Map;
import java.util.TreeMap;


public class GameController {
    private double x;
    private double y;

    @FXML private ImageView w_pawn1;
    @FXML private ImageView w_pawn2;
    @FXML private ImageView w_pawn3;
    @FXML private ImageView w_pawn4;
    @FXML private ImageView w_pawn5;
    @FXML private ImageView w_pawn6;
    @FXML private ImageView w_pawn7;
    @FXML private ImageView w_pawn8;

    @FXML private ImageView b_pawn1;
    @FXML private ImageView b_pawn2;
    @FXML private ImageView b_pawn3;
    @FXML private ImageView b_pawn4;
    @FXML private ImageView b_pawn5;
    @FXML private ImageView b_pawn6;
    @FXML private ImageView b_pawn7;
    @FXML private ImageView b_pawn8;


    public void move() {

    }

    @FXML
    public void initialize() {
        Map<String, ImageView> images = new TreeMap<>();
        images.put("w_pawn1", w_pawn1);
        images.put("w_pawn2", w_pawn2);
        images.put("w_pawn3", w_pawn3);
        images.put("w_pawn4", w_pawn4);
        images.put("w_pawn5", w_pawn5);
        images.put("w_pawn6", w_pawn6);
        images.put("w_pawn7", w_pawn7);
        images.put("w_pawn8", w_pawn8);

        images.put("b_pawn1", b_pawn1);
        images.put("b_pawn2", b_pawn2);
        images.put("b_pawn3", b_pawn3);
        images.put("b_pawn4", b_pawn4);
        images.put("b_pawn5", b_pawn5);
        images.put("b_pawn6", b_pawn6);
        images.put("b_pawn7", b_pawn7);
        images.put("b_pawn8", b_pawn8);

        new Game(Team.WHITE, images);
    }
}

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
    @FXML private ImageView w_castle1;
    @FXML private ImageView w_castle2;
    @FXML private ImageView w_elephant1;
    @FXML private ImageView w_elephant2;
    @FXML private ImageView w_queen;
    @FXML private ImageView w_horse1;
    @FXML private ImageView w_horse2;
    @FXML private ImageView w_king;

    @FXML private ImageView b_pawn1;
    @FXML private ImageView b_pawn2;
    @FXML private ImageView b_pawn3;
    @FXML private ImageView b_pawn4;
    @FXML private ImageView b_pawn5;
    @FXML private ImageView b_pawn6;
    @FXML private ImageView b_pawn7;
    @FXML private ImageView b_pawn8;
    @FXML private ImageView b_castle1;
    @FXML private ImageView b_castle2;
    @FXML private ImageView b_elephant1;
    @FXML private ImageView b_elephant2;
    @FXML private ImageView b_queen;
    @FXML private ImageView b_horse1;
    @FXML private ImageView b_horse2;
    @FXML private ImageView b_king;





    public void move() {

    }

    @FXML
    public void initialize() {
        try {
            new NetWorkClient("127.0.0.1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, ImageView> images = new TreeMap<>();
        images.put("w_pawn1", w_pawn1);
        images.put("w_pawn2", w_pawn2);
        images.put("w_pawn3", w_pawn3);
        images.put("w_pawn4", w_pawn4);
        images.put("w_pawn5", w_pawn5);
        images.put("w_pawn6", w_pawn6);
        images.put("w_pawn7", w_pawn7);
        images.put("w_pawn8", w_pawn8);
        images.put("w_castle1", w_castle1);
        images.put("w_castle2", w_castle2);
        images.put("w_elephant1", w_elephant1);
        images.put("w_elephant2", w_elephant2);
        images.put("w_horse1", w_horse1);
        images.put("w_horse2", w_horse2);
        images.put("w_queen", w_queen);
        images.put("w_king", w_king);

        images.put("b_pawn1", b_pawn1);
        images.put("b_pawn2", b_pawn2);
        images.put("b_pawn3", b_pawn3);
        images.put("b_pawn4", b_pawn4);
        images.put("b_pawn5", b_pawn5);
        images.put("b_pawn6", b_pawn6);
        images.put("b_pawn7", b_pawn7);
        images.put("b_pawn8", b_pawn8);
        images.put("b_castle1", b_castle1);
        images.put("b_castle2", b_castle2);
        images.put("b_elephant1", b_elephant1);
        images.put("b_elephant2", b_elephant2);
        images.put("b_horse1", b_horse1);
        images.put("b_horse2", b_horse2);
        images.put("b_queen", b_queen);
        images.put("b_king", b_king);

        new Game(Team.WHITE, images);
    }
}

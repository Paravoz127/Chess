package kpfu.itis.group11_801.kilin.chess.models;

import javafx.scene.image.Image;

public enum Figure {
    WHITE_QUEEN("kpfu/itis/group11_801/kilin/chess/resources/w_queen.png", "Queen"),
    WHITE_HORSE("kpfu/itis/group11_801/kilin/chess/resources/w_hourse.png", "Horse"),
    WHITE_ELEPHANT("kpfu/itis/group11_801/kilin/chess/resources/w_elephant.png", "Elephant"),
    WHITE_CASTLE("kpfu/itis/group11_801/kilin/chess/resources/w_castle.png", "Castle"),
    WHITE_PAWN("kpfu/itis/group11_801/kilin/chess/resources/w_pawn.png", "Pawn"),
    WHITE_KING("kpfu/itis/group11_801/kilin/chess/resources/w_king.png", "King"),
    BLACK_QUEEN("kpfu/itis/group11_801/kilin/chess/resources/b_queen.png", "Queen"),
    BLACK_HORSE("kpfu/itis/group11_801/kilin/chess/resources/b_hourse.png", "Horse"),
    BLACK_ELEPHANT("kpfu/itis/group11_801/kilin/chess/resources/b_elephant.png", "Elephant"),
    BLACK_CASTLE("kpfu/itis/group11_801/kilin/chess/resources/b_castle.png", "Castle"),
    BLACK_PAWN("kpfu/itis/group11_801/kilin/chess/resources/b_pawn.png", "Pawn"),
    BLACK_KING("kpfu/itis/group11_801/kilin/chess/resources/b_king.png", "King");

    private Image image;
    private String name;

    private Figure(String imagePath, String name) {
        this.image = new Image(imagePath);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
        switch (name) {
            case "Elephant":
                return "Bishop";
            case "Castle":
                return "Rook";
            case "Horse":
                return "Knight";
            default:
                return name;
        }
    }
}

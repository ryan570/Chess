package chess;

import chess.Piece.Type;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameController {

    private static boolean turn, selected;
    private static Position from;
    private static Piece selectedPiece;
    private static Piece[] white, black;

    public GameController(Board board) {
        white = board.white;
        black = board.black;
        turn = true;
        selected = false;
        play();
    }

    private static void play() {
        if (turn) {
            checkPromotion(black, Color.BLACK);
            unlock(white);
        } else {
            checkPromotion(white, Color.WHITE);
            unlock(black);
        }
    }

    private static void checkPromotion(Piece[] array, Color color) {
        for (Piece piece : array) {
            if (piece.getPosition() != null) {
                if (piece.getType() == Type.PAWN && (piece.getPosition().getRow() == Position.Row.EIGHT || piece.getPosition().getRow() == Position.Row.ONE)) {
                    HBox box = new HBox();
                    Piece[] choices = new Piece[]{
                            new Piece(null, Type.QUEEN, color),
                            new Piece(null, Type.ROOK, color),
                            new Piece(null, Type.BISHOP, color),
                            new Piece(null, Type.KNIGHT, color)
                    };
                    Scene scene = new Scene(box, 180,50);
                    Stage stage = new Stage();

                    for (Piece p : choices) {
                        box.getChildren().add(p);
                        p.setOnMousePressed(n -> {
                            piece.setType(p.getType());
                            stage.close();
                        });
                    }

                    stage.setScene(scene);
                    stage.setTitle("Select Piece");
                    stage.show();
                }
            }
        }
    }

    private static void unlock(Piece[] color) {
        if (color == white) {
            for (Piece piece : white) {
                piece.setLocked(false);
            }
            for (Piece piece : black) {
                piece.setLocked(true);
            }
        } else if (color == black) {
            for (Piece piece : white) {
                piece.setLocked(true);
            }
            for (Piece piece : black) {
                piece.setLocked(false);
            }
        }
    }

    private static boolean inCheck(Color color) {
        Piece king;
        Piece[] array;
        if (color == Color.WHITE) {
            king = white[15];
            array = black;
        } else {
            king = black[15];
            array = white;
        }
        for (Piece piece : array) {
            if (piece.getPosition() != null) {
                if (piece.canMoveTo(king.getPosition(), false)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void select(Position position) {
        if (!selected) {
            from = position;
            selectedPiece = position.getPiece();
            if (selectedPiece != null) selected = true;
        } else {
            if (from.equals(position)) {
                selected = false;
                selectedPiece = null;
                return;
            }
            if (selectedPiece.canMoveTo(position, true)) {
                selectedPiece.queue(position);
                from.queue(null, false);
                position.queue(selectedPiece, true);

                boolean check = inCheck(selectedPiece.getColor());
                selectedPiece.update(check);
                from.update(check);
                position.update(check);

                if (!check) turn = !turn;
            }
            selectedPiece = null;
            selected = false;
            play();
        }
    }
}
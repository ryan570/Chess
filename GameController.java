package chess;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameController {

    private static boolean turn, selected, check, first;
    private static Position from, to;
    private static Piece selectedPiece, current;
    private static Piece[] white, black;

    public GameController(Board board) {
        white = board.white;
        black = board.black;
        turn = true;
        selected = false;
        first = true;
        play();
    }

    private static void play() {
        if (turn) {
            checkPromotion(black, Color.BLACK);
            if (!first) check = inCheck(Color.WHITE);
            unlock(white);
        } else {
            checkPromotion(white, Color.WHITE);
            if (!first) check = inCheck(Color.BLACK);
            unlock(black);
        }
        first = false;
    }

    private static void checkPromotion(Piece[] array, Color color) {
        for (Piece piece : array) {
            if (piece.getPosition() != null) {
                if (piece.getType() == Piece.Type.PAWN && (piece.getPosition().getRow() == Position.Row.EIGHT || piece.getPosition().getRow() == Position.Row.ONE)) {
                    HBox box = new HBox();
                    Piece[] choices = new Piece[]{
                            new Piece(null, Piece.Type.QUEEN, color),
                            new Piece(null, Piece.Type.ROOK, color),
                            new Piece(null, Piece.Type.BISHOP, color),
                            new Piece(null, Piece.Type.KNIGHT, color)
                    };
                    Scene scene = new Scene(box, 190,50);
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
                if (piece.canMoveTo(king.getPosition())) {
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
            System.out.println(selectedPiece.getPosition().toString());
            if (selectedPiece != null) selected = true;
        } else {
            to = position;
            if (from.equals(to)) return;
            if (to.hasPiece()) {
                current = to.getPiece();
                current.setPosition(null);
            }
            selectedPiece.queue(from, to);
            if (inCheck(selectedPiece.getColor())) {
                to.revert();
                selectedPiece.update(true);
                selectedPiece = null;
                selected = false;
                return;
            }
            if (check) {
                check = inCheck(selectedPiece.getColor());
                selectedPiece.update(check);
                selected = false;
                if (check) {
                    current.setPosition(to);
                    to.revert();
                    return;
                }
            }
            selectedPiece.update(false);
            if (from != selectedPiece.getPosition()) turn = !turn;
            selected = false;
            play();
        }
    }
}
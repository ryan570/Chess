package chess;

import javafx.scene.paint.Color;

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
            if (!first) check = inCheck(Color.WHITE);
            unlock(white);
        } else {
            if (!first) check = inCheck(Color.BLACK);
            unlock(black);
        }
        first = false;
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
            if (selectedPiece != null) selected = true;
        } else {
            to = position;
            if (from.equals(to)) return;
            if (to.hasPiece()) {
                current = to.getPiece();
                to.getPiece().setPosition(null);
            }
            selectedPiece.queue(from, to);
            if (selectedPiece.getType() == Piece.Type.KING && inCheck(selectedPiece.getColor())) {
                to.revert();
                selectedPiece.update(true);
                return;
            }
            if (check) {
                check = inCheck(selectedPiece.getColor());
                selectedPiece.update(check);
                selected = false;
                if (check) {
                    to.revert();
                    return;
                }
            } else {
                selectedPiece.update(false);
            }
            if (selectedPiece.getPosition() != from) {
                turn = !turn;
            }
            selected = false;
            play();
        }
    }
}
package chess;

public class GameController {

    private static boolean turn, selected, whiteCheck, blackCheck, first;
    private static Position from, to;
    private static Piece selectedPiece;
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
            unlock(white);
            if (!first) whiteCheck = inCheck(white);
        } else {
            unlock(black);
            if (!first) blackCheck = inCheck(black);
        }
        first = false;
        System.out.println("black: " + blackCheck);
        System.out.println("white: " + whiteCheck);
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

    private static boolean inCheck(Piece[] array) {
        Piece king;
        if (array == white) {
            king = white[15];
            array = black;
        } else {
            king = black[15];
            array = white;
        }

        for (Piece piece : array) {
            boolean valid = piece.checkValidMove(piece.getPosition(), king.getPosition());
            boolean noCollisions = piece.checkCollisions(piece.getPosition(), king.getPosition());
            if (valid && noCollisions) return true;
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
            selectedPiece.queue(from, to);
            if (whiteCheck) {
                System.out.println(1);
                boolean solved = !inCheck(white);
                whiteCheck = !solved;
                selectedPiece.update(solved);
                selected = false;
                if (!solved) return;
            }
            else if (blackCheck) {
                boolean solved = !inCheck(black);
                blackCheck = !solved;
                selectedPiece.update(solved);
                selected = false;
                if (!solved) return;
            }
            selectedPiece.update(true);
            if (selectedPiece.getPosition() != from) {
                turn = !turn;
            }
            selected = false;
            play();
        }
    }
}
package chess;

public class GameController {

    private static boolean turn, selected;
    private static Board board;
    private static Position from, to;
    private static Piece selectedPiece;
    private static Piece[] white, black;

    public GameController(Board board) {
        this.board = board;

        white = board.white;
        black = board.black;
        turn = true;
        selected = false;
        play();
    }

    private static void play() {
        if (turn) {
            unlock(white);
        } else {
            unlock(black);
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



    public static void select(Position position) {
        if (!selected) {
            from = position;
            selectedPiece = position.getPiece();
            if (selectedPiece != null) selected = true;
        } else {
            to = position;
            if (from.equals(to)) return;
            selectedPiece.move(from, to);
            if (selectedPiece.getPosition() != from) {
                turn = !turn;
            }
            selected = false;
            play();
        }
    }
}
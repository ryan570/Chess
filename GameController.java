package chess;

public class GameController {

    private static boolean turn, selected;
    private static Board board;
    private static Position from, to;
    private static Piece selectedPiece;

    public GameController(Board board) {
        this.board = board;

        turn = false;
        selected = false;
        play();
    }

    private static void play() {
        if (turn) {
            for (Piece piece : board.white) {
                piece.getPosition().lock(true);
            }
            for (Piece piece : board.black) {
                piece.getPosition().lock(false);
            }
        } else {
            for (Piece piece : board.white) {
                piece.getPosition().lock(false);
            }
            for (Piece piece : board.black) {
                piece.getPosition().lock(true);
            }
        }
    }

    public static void select(Position position) {
        if (!selected) {
            if (position.hasPiece()) {
                from = position;
                selectedPiece = position.getPiece();
                selected = true;
            }
        } else {
            to = position;
            if(from.equals(to)) return;
            selectedPiece.move(from, to);
            selectedPiece = null;
            selected = false;
            turn = !turn;
            play();
        }
    }
}
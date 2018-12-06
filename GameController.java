package chess;

import chess.Piece.Type;
import chess.Position.Column;
import chess.Position.Row;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameController {

    private static boolean turn, selected;
    private static Position from;
    private static Piece selectedPiece;
    private static Piece[] white, black;
    private static int currentMove;


    public GameController(Board board) {
        white = board.white;
        black = board.black;

        turn = true;
        currentMove = 0;
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
                    Scene scene = new Scene(box, 170, 35);
                    Stage stage = new Stage();

                    for (Piece p : choices) {
                        box.getChildren().add(p);
                        p.setOnMousePressed(n -> {
                            piece.setType(p.getType());
                            stage.close();
                        });
                    }

                    Platform.setImplicitExit(false);
                    stage.setOnCloseRequest(n-> {
                        if (piece.getType() == Type.PAWN) {
                            n.consume();
                        }
                    });
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.setTitle("Select Piece");
                    stage.show();
                }
            }
        }
    }

    private static void checkCastle(Color color, Position from, Position to) {
        Piece[] array;
        Piece rook;
        Position pos;

        if (color == Color.WHITE) {
            array = white;
        } else {
            array = black;
        }

        if (!inCheck(color)) {
            selectedPiece.queue(to);
            from.queue(null, false);
            to.queue(selectedPiece, true);

            if (!inCheck(color)) {
                if (to.getColumn() == Column.B) {
                    if (array[12].getPosition() != null) {
                        rook = array[12];
                        if (rook.getPosition().getRow() == Row.ONE && rook.getPosition().getColumn() == Column.A && !rook.hasMoved()) {
                            selectedPiece.update(false);
                            from.update(false);
                            to.update(false);
                            pos = Board.findPosition(Column.C, Row.ONE);
                            rook.setPosition(pos);
                            pos.setPiece(rook);
                            turn = !turn;
                            selectedPiece.setLastMove(currentMove);
                        }
                    }
                } else if (to.getColumn() == Column.G) {
                    if (array[13].getPosition() != null) {
                        rook = array[13];
                        if (rook.getPosition().getRow() == Row.ONE && rook.getPosition().getColumn() == Column.H && !rook.hasMoved()) {
                            selectedPiece.update(false);
                            from.update(false);
                            to.update(false);
                            pos = Board.findPosition(Column.F, Row.ONE);
                            rook.setPosition(pos);
                            pos.setPiece(rook);
                            turn = !turn;
                            selectedPiece.setLastMove(currentMove);
                        }
                    }
                }
            } else {
                selectedPiece.update(true);
                from.update(true);
                from.update(true);
            }
        }
    }

    private static void checkEnPassant(Piece piece, Position from, Position to) {
        if (piece.getType() == Type.PAWN && !inCheck(piece.getColor())) {

            int currentCol = from.getColumn().getVal();
            int futureCol = to.getColumn().getVal();
            int colDiff = futureCol - currentCol;

            if (Math.abs(colDiff) == 1 && !to.hasPiece()) {
                Row row = null;
                if (piece.getColor() == Color.WHITE && from.getRow() == Row.FIVE) {
                    row = Row.FIVE;
                } else if (piece.getColor() == Color.BLACK && from.getRow() == Row.FOUR) {
                    row = Row.FOUR;
                }
                Position pawnPos = (Board.findPosition(to.getColumn(), row));
                if (pawnPos.hasPiece()) {
                    Piece pawn = pawnPos.getPiece();

                    if (pawn.getType() == Type.PAWN && pawn.getColor() != piece.getColor()) {
                        if (pawn.getLastMove() + 1 == currentMove) {
                            selectedPiece.queue(to);
                            from.queue(null, false);
                            to.queue(selectedPiece, true);

                            boolean check = inCheck(selectedPiece.getColor());
                            selectedPiece.update(check);
                            from.update(check);
                            to.update(check);

                            if (!check) {
                                turn = !turn;
                                pawn.setPosition(null);
                                pawnPos.removePiece();
                                selectedPiece.setLastMove(currentMove);
                            }
                        }
                    }
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
        currentMove++;
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
                if (piece.canMoveTo(king.getPosition(), false)[0]) {
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

            boolean[] results = selectedPiece.canMoveTo(position, true);

            checkEnPassant(selectedPiece, from, position);
            if (results[0]) {
                if (results[1]) {
                    checkCastle(selectedPiece.getColor(), from, position);
                } else {
                    selectedPiece.queue(position);
                    from.queue(null, false);
                    position.queue(selectedPiece, true);

                    boolean check = inCheck(selectedPiece.getColor());
                    selectedPiece.update(check);
                    from.update(check);
                    position.update(check);

                    if (!check) {
                        turn = !turn;
                        selectedPiece.setLastMove(currentMove);
                    }
                }
            }
            selectedPiece = null;
            selected = false;
            play();
        }
    }
}
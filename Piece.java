package chess;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.stream.IntStream;

public class Piece extends Circle {

    enum Type {
        PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING;
    }

    private Type type;
    private Position position;
    private Color color;

    public Piece() {
    }

    public Piece(Position position, Type type, Color color) {
        super(25, 25, 15, color);
        setStroke(Color.RED);

        this.type = type;
        this.position = position;
        this.color = color;
    }

    public void move(Position from, Position to) {
        if (checkValidMove(from, to)) {
            position.removePiece();
            to.setPiece(this);
            position = to;
        }
    }

    public Type getType() {
        return type;
    }

    private Color getColor() {
        return color;
    }

    private boolean checkCollisions(Position from, Position to, boolean diagonal) {
        //diagonal
        if (diagonal) {
            int currentCol = from.getColumn().getVal(), currentRow = from.getRow().getVal();
            int futureCol = to.getColumn().getVal(), futureRow = to.getRow().getVal();
            int rowDiff = futureRow - currentRow, colDiff = futureCol - currentCol;

            //up and to the right
            if (rowDiff > 0 && colDiff > 0) {
                int current = currentRow + 1;
                for (int i = currentCol + 1; i < futureCol; i++) {
                    for (int j = currentRow + 1; j < futureRow; j++) {
                        if (j == current) {
                            if (Board.findPosition(Board.columns[i - 1], Board.rows[8 - j]).hasPiece()) return false;
                        }
                    }
                    current++;
                }
            }

            //up and to the left
            if (rowDiff > 0 && colDiff < 0) {
                int current = currentRow + 1;
                for (int i = currentCol - 1; i > futureCol; i--) {
                    for (int j = currentRow + 1; j < futureRow; j++) {
                        if (j == current) {
                            if (Board.findPosition(Board.columns[i - 1], Board.rows[8 - j]).hasPiece()) return false;
                        }
                    }
                    current++;
                }
            }

            //down and to the right
            if (rowDiff < 0 && colDiff > 0) {
                int current = currentRow - 1;
                for (int i = currentCol + 1; i < futureCol; i++) {
                    for (int j = currentRow - 1; j > futureRow; j--) {
                        if (j == current) {
                            if (Board.findPosition(Board.columns[i - 1], Board.rows[8 - j]).hasPiece()) return false;
                        }
                    }
                    current--;
                }
            }

            //down and to the left
            if (rowDiff < 0 && colDiff < 0) {
                int current = currentRow - 1;
                for (int i = currentCol - 1; i > futureCol; i--) {
                    for (int j = currentRow - 1; j > futureRow; j--) {
                        if (j == current) {
                            if (Board.findPosition(Board.columns[i - 1], Board.rows[8 - j]).hasPiece()) return false;
                        }
                    }
                    current--;
                }
            }
        }

        //straight line
        else if (to.getRow() == from.getRow()) {
            int currentCol = from.getColumn().getVal();
            int futureCol = to.getColumn().getVal();
            for (int i = Math.min(currentCol, futureCol) + 1; i < Math.max(futureCol, currentCol); i++) {
                if (Board.findPosition(Board.columns[i - 1], from.getRow()).hasPiece()) return false;
            }
        } else if (to.getColumn() == from.getColumn()) {
            int currentRow = from.getRow().getVal();
            int futureRow = to.getRow().getVal();
            for (int i = Math.min(currentRow, futureRow) + 1; i < Math.max(futureRow, currentRow); i++) {
                if (Board.findPosition(from.getColumn(), Board.rows[8 - i]).hasPiece()) return false;
            }
        }

        if (to.hasPiece()) {
            return checkFinal(to);
        }

        return true;
    }

    private boolean checkFinal(Position to) {
        if (to.getPiece().getColor() == color) {
            return false;
        } else {
            to.getPiece().setPosition(null);
            to.removePiece();
            return true;
        }
    }

    private void setPosition(Position pos) {
        this.position = pos;
    }

    public boolean checkValidMove(Position from, Position to) {
        boolean valid = false;

        int currentRow = from.getRow().getVal();
        int currentCol = from.getColumn().getVal();
        int futureRow = to.getRow().getVal();
        int futureCol = to.getColumn().getVal();

        int rowDiff = futureRow - currentRow;
        int colDiff = futureCol - currentCol;

        switch (type) {
            //TODO: FIX PAWN COLLISION MECHANICS
            case PAWN:
                if (to.getColumn() == from.getColumn()) {
                    if ((currentRow == 2 && color == Color.WHITE) || (currentRow == 7 && color == Color.BLACK)) {
                        if (rowDiff <= 2 && rowDiff >= -2) valid = true;
                    } else if (rowDiff == 1 && color == Color.WHITE) valid = true;
                    else if (rowDiff == -1 && color == Color.BLACK) valid = true;
                }
                break;
            case KNIGHT:
                if (Math.abs(colDiff) == 2 && Math.abs(rowDiff) == 1) {
                    if (to.hasPiece()) checkFinal(to);
                    valid = true;
                } else if (Math.abs(colDiff) == 1 && Math.abs(rowDiff) == 2) {
                    if (to.hasPiece()) checkFinal(to);
                    valid = true;
                }
                break;
            case BISHOP:
                if ((Math.abs(rowDiff) == Math.abs(colDiff))) {
                    if (checkCollisions(from, to, Math.abs(rowDiff) == Math.abs(colDiff))) valid = true;
                }
                break;
            case ROOK:
                if ((currentCol == futureCol || currentRow == futureRow)) {
                    if (checkCollisions(from, to, Math.abs(rowDiff) == Math.abs(colDiff))) valid = true;
                }
                break;
            case QUEEN:
                if (Math.abs(rowDiff) == Math.abs(colDiff)) {
                    if (checkCollisions(from, to, Math.abs(rowDiff) == Math.abs(colDiff))) valid = true;
                } else if ((currentCol == futureCol || currentRow == futureRow)) {
                    if (checkCollisions(from, to, Math.abs(rowDiff) == Math.abs(colDiff))) valid = true;
                }
                break;
            case KING:
                if ((Math.abs(rowDiff) == Math.abs(colDiff) && Math.abs(colDiff) == 1 && Math.abs(rowDiff) == 1)) {
                    if (checkCollisions(from, to, Math.abs(rowDiff) == Math.abs(colDiff))) valid = true;
                } else if (((currentCol == futureCol || currentRow == futureRow) && (Math.abs(colDiff) == 1 || Math.abs(rowDiff) == 1))) {
                    if (checkCollisions(from, to, Math.abs(rowDiff) == Math.abs(colDiff))) valid = true;
                }
                break;
            default:
                break;
        }
        return valid;
    }
}
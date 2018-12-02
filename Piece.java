package chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Piece extends ImageView {

    enum Type {
        PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING;
    }

    private Type type;
    private Position position, past;
    private Color color;
    private boolean unlocked;

    public Piece(Position position, Type type, Color color) {
        fitWidthProperty().bind(position.widthProperty());
        fitHeightProperty().bind(position.heightProperty());

        switch (type) {
            case PAWN:
                if (color == Color.WHITE) setImage(new Image("chess/sprites/white-pawn.png"));
                else setImage(new Image("chess/sprites/black-pawn.png"));
                break;
            case KNIGHT:
                if (color == Color.WHITE) setImage(new Image("chess/sprites/white-knight.png"));
                else setImage(new Image("chess/sprites/black-knight.png"));
                break;
            case BISHOP:
                if (color == Color.WHITE) setImage(new Image("chess/sprites/white-bishop.png"));
                else setImage(new Image("chess/sprites/black-bishop.png"));
                break;
            case ROOK:
                if (color == Color.WHITE) setImage(new Image("chess/sprites/white-rook.png"));
                else setImage(new Image("chess/sprites/black-rook.png"));
                break;
            case QUEEN:
                if (color == Color.WHITE) setImage(new Image("chess/sprites/white-queen.png"));
                else setImage(new Image("chess/sprites/black-queen.png"));
                break;
            case KING:
                if (color == Color.WHITE) setImage(new Image("chess/sprites/white-king.png"));
                else setImage(new Image("chess/sprites/black-king.png"));
                break;
            default:
                break;
        }

        this.type = type;
        this.position = position;
        this.color = color;
    }


    public void queue(Position from, Position to) {
        boolean valid = checkValidMove(from, to);
        boolean noCollisions = checkCollisions(from, to);
        if ((valid && noCollisions && unlocked) || (type == Type.PAWN && valid && unlocked)) {
            past = position;
            to.queue(this);
            setPosition(to);
        }
    }

    public void update(boolean bad) {
        if (!bad) {
            if (past != null) past.removePiece();
            position.setPiece(this);

            past = null;
        } else {
            position.queue(null);
            setPosition(past);
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setLocked(boolean locked) {
        this.unlocked = !locked;
    }

    public Color getColor() {
        return color;
    }

    public boolean canMoveTo(Position pos) {
        boolean noCollisions = checkCollisions(position, pos);
        boolean validMove = checkValidMove(position, pos);
        if ((noCollisions || type == type.PAWN) && validMove) {
            return true;
        }
        return false;
    }

    private boolean checkCollisions(Position from, Position to) {
        int currentRow = from.getRow().getVal();
        int currentCol = from.getColumn().getVal();
        int futureRow = to.getRow().getVal();
        int futureCol = to.getColumn().getVal();

        int rowDiff = futureRow - currentRow;
        int colDiff = futureCol - currentCol;

        //diagonal
        if (Math.abs(rowDiff) == Math.abs(colDiff)) {
            int ddX = colDiff / Math.abs(colDiff), ddY = rowDiff / Math.abs(rowDiff);

            for (int i = 1; i < Math.abs(colDiff); i++) {
                if (Board.findPosition(Board.columns[currentCol + (i * ddX) - 1], Board.rows[8 - (currentRow + (i * ddY))]).hasPiece())
                    return false;
            }
        }

        //straight line
        else if (to.getRow() == from.getRow()) {
            for (int i = Math.min(currentCol, futureCol) + 1; i < Math.max(futureCol, currentCol); i++) {
                if (Board.findPosition(Board.columns[i - 1], from.getRow()).hasPiece()) return false;
            }
        } else if (to.getColumn() == from.getColumn()) {
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
        if (to.hasPiece()) {
            return to.getPiece().getColor() != color;
        } else return false;
    }

    private void setPosition(Position pos) {
        this.position = pos;
    }

    private boolean checkValidMove(Position from, Position to) {
        int currentRow = from.getRow().getVal();
        int currentCol = from.getColumn().getVal();
        int futureRow = to.getRow().getVal();
        int futureCol = to.getColumn().getVal();

        int rowDiff = futureRow - currentRow;
        int colDiff = futureCol - currentCol;

        switch (type) {
            case PAWN:
                if (to.getColumn() == from.getColumn()) {
                    if (to.hasPiece()) return false;
                    if ((currentRow == 2 && color == Color.WHITE) || (currentRow == 7 && color == Color.BLACK)) {
                        if (rowDiff <= 2 && rowDiff >= -2) return true;
                    } else if (Math.abs(rowDiff) == 1) return true;
                } else if (checkFinal(to) && Math.abs(colDiff) == 1 && ((rowDiff == 1 && color == Color.WHITE) || (rowDiff == -1 && color == Color.BLACK)))
                    return true;
                break;
            case KNIGHT:
                if (Math.abs(colDiff) == 2 && Math.abs(rowDiff) == 1) return true;
                else if (Math.abs(colDiff) == 1 && Math.abs(rowDiff) == 2) return true;
                break;
            case BISHOP:
                if ((Math.abs(rowDiff) == Math.abs(colDiff))) return true;
                break;
            case ROOK:
                if ((currentCol == futureCol || currentRow == futureRow)) return true;
                break;
            case QUEEN:
                if (Math.abs(rowDiff) == Math.abs(colDiff)) return true;
                else if ((currentCol == futureCol || currentRow == futureRow)) return true;
                break;
            case KING:
                if ((Math.abs(rowDiff) == Math.abs(colDiff) && Math.abs(colDiff) == 1 && Math.abs(rowDiff) == 1))
                    return true;
                else if (((currentCol == futureCol || currentRow == futureRow) && (Math.abs(colDiff) == 1 || Math.abs(rowDiff) == 1)))
                    return true;
                break;
            default:
                break;
        }
        return false;
    }
}
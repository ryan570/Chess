package chess;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece extends Circle {

    enum Type {
        PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING;
    }
    
    private Type type;
    private Position position;
    private Color color;

    public Piece() {}
    
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

    public Type getType() { return type; }

    private boolean checkCollisions(Position from, Position to) {
        boolean valid = false;
        


        return valid;
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
            case PAWN:
                if (to.getColumn() == from.getColumn()) {
                    if ((currentRow == 2 && color == Color.WHITE) || (currentRow == 7 && color == Color.BLACK)) {
                        if (rowDiff <= 2 && rowDiff >= -2) valid = true;
                    } else if (rowDiff == 1 && color == Color.WHITE) valid = true;
                    else if (rowDiff == -1 && color == Color.BLACK) valid = true;
                }
                break;
            case KNIGHT:
                if (Math.abs(colDiff) == 2 && Math.abs(rowDiff) == 1) valid = true;
                else if (Math.abs(colDiff) == 1 && Math.abs(rowDiff) == 2) valid = true;
                break;
            case BISHOP:
                if (Math.abs(rowDiff) == Math.abs(colDiff)) valid = true;
                break;
            case ROOK:
                if (currentCol == futureCol || currentRow == futureRow) valid = true;
                break;
            case QUEEN:
                if (Math.abs(rowDiff) == Math.abs(colDiff)) valid = true;
                else if (currentCol == futureCol || currentRow == futureRow) valid = true;
                break;
            case KING:
                if (Math.abs(rowDiff) == Math.abs(colDiff) && Math.abs(colDiff) == 1 && Math.abs(rowDiff) == 1) valid = true;
                else if ((currentCol == futureCol || currentRow == futureRow) && (Math.abs(colDiff) == 1 || Math.abs(rowDiff) ==1)) valid = true;
                break;
            default: break;
        }
        
        return valid;
    }
    
}
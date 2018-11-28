package chess;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece extends Circle {

    enum Type {
        PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING;
    }
    
    private Type type;
    private Position position;

    public Piece() {}
    
    public Piece(Position position, Type type, Color color) {
        super(25, 25, 15, color);
        setStroke(Color.RED);
        
        this.type = type;
        this.position = position;
    }
    
    public void move(Position from, Position to) {
        position.removePiece();
        to.setPiece(this);
    }

    public Type getType() { return type; }
    
    public boolean checkValidMove(Position from, Position to) {
        boolean valid = false;
        
        
        return valid;
    }
    
}
package chess;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece extends Circle {

    enum Type {
        PAWN, KNIGHT, BISHOP, ROOKE, QUEEN, KING;
    }
    
    private Type type;
    
    public Piece(Position position, Type type, Color color) {
        super(25, 25, 15, color);
        setStroke(Color.RED);
        
        this.type = type;
        
        setOnMousePressed(e -> {
            System.out.println(type);
        });
    }
    
    public void move(Position from, Position to) {
        
    }
    
    public boolean checkValidMove(Position from, Position to) {
        boolean valid = false;
        
        
        return valid;
    }
    
}
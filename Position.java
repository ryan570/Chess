package chess;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Position extends Pane {
    
    enum Column { A, B, C, D, E, F, G, H };
    enum Row { 
        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8);
        
        private int val;
        
        Row(int val) {
            this.val = val;
        }
        
        public int getVal() {
            return val;
        }
    }
    
    private Column column;
    private Row row;
    private Piece piece;
    
    public Position(Column column, Row row, boolean color) {
        setPrefSize(50, 50);
        
        Rectangle rect = new Rectangle(0, 0, 50, 50);
        if (color) rect.setFill(Color.BLACK);
        else rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);
        getChildren().add(rect);
        
        this.column = column;
        this.row = row;
        
        setOnMousePressed(e -> {
            System.out.println(column + "" + row.getVal());
        });
    }
    
    public void setPiece(Piece piece) {
        this.piece = piece;
        getChildren().add(piece);
    }
    
    public void removePiece() {
        getChildren().remove(piece);
        piece = null;
    }
    
    public void renderPiece() {
        if (piece != null) {
            getChildren().add(piece);
        }
    }
    
    public Piece getPiece() {
        return piece;
    }
    
    public Row getRow() {
        return row;
    }
    
    public Column getColumn() {
        return column;
    }
}
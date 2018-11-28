package chess;

import chess.Piece.Type;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Position extends Pane {
    
    enum Column {
        A(1), B(2), C(3), D(4), E(5), F(6), G(7), H(8);

        private int val;

        Column(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }
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

    public Position(){}

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
            System.out.println(column + "" + row.getVal() + ": " + getPieceType());


            Board.select(this);
        });
    }
    
    public void setPiece(Piece piece) {
        this.piece = piece;
        getChildren().add(piece);
    }
    
    public void removePiece() {
        getChildren().remove(piece);
        piece = null;
        System.out.println("removed");
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public Type getPieceType() {
        if (piece != null) {
            return piece.getType();
        }
        return null;
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
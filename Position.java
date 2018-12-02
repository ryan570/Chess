package chess;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class Position extends BorderPane {
    
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

    public Position(Column column, Row row, boolean color) {
        minWidthProperty().bind(Chess.scene.widthProperty().divide(8));
        minHeightProperty().bind(Chess.scene.heightProperty().divide(8));

        if (color) setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        else setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        
        this.column = column;
        this.row = row;

        setOnMousePressed(n-> GameController.select(this));
    }
    
    public void setPiece(Piece piece) {
        this.piece = piece;
        setCenter(piece);
    }

    public String toString() {
        return column + "" + row.getVal();
    }
    
    public void removePiece() {
        getChildren().remove(piece);
        piece = null;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public void queue(Piece piece) {
        this.piece = piece;
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
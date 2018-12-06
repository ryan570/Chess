package chess;

import chess.Piece.Type;
import chess.Position.Column;
import chess.Position.Row;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Board extends GridPane {

    public Piece[] black, white;
    public static ArrayList<Position> positions;
    public static Column[] columns;
    public static Row[] rows;
    private Piece[][] colors;

    public Board() {
        positions = new ArrayList<>();

        rows = new Row[]{Row.EIGHT, Row.SEVEN, Row.SIX, Row.FIVE, Row.FOUR, Row.THREE, Row.TWO, Row.ONE};
        columns = new Column[]{Column.A, Column.B, Column.C, Column.D, Column.E, Column.F, Column.G, Column.H};
        white = new Piece[16];
        black = new Piece[16];
        colors = new Piece[][] {white, black};

        //creates all chess board positions
        boolean color = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position position = new Position(columns[j], rows[i], color);
                positions.add(position);
                add(position, j, i);

                color = !color;
            }
            color = !color;
        }

        addPieces();
    }

    private void addPieces() {
        //adds pieces to both players
        int i = 0;
        for (Piece[] array : colors) {
            Row frontRow;
            Row backRow;
            Color color;
            if (i == 0) {
                color = Color.WHITE;
                frontRow = Row.TWO;
                backRow = Row.ONE;
            } else {
                color = Color.BLACK;
                frontRow = Row.SEVEN;
                backRow = Row.EIGHT;
            }
            
            //pawns
            for (int j = 0; j < 8; j++) {
                Position position = findPosition(columns[j], frontRow);
                array[j] = new Piece(position, Type.PAWN, color);
                position.setPiece(array[j]);
            }
            
            //knights
            array[8] = new Piece(findPosition(Column.B, backRow), Type.KNIGHT, color);
            findPosition(Column.B, backRow).setPiece(array[8]);

            array[9] = new Piece(findPosition(Column.G, backRow), Type.KNIGHT, color);
            findPosition(Column.G, backRow).setPiece(array[9]);
            
            //bishops
            array[10] = new Piece(findPosition(Column.C, backRow), Type.BISHOP, color);
            findPosition(Column.C, backRow).setPiece(array[10]);

            array[11] = new Piece(findPosition(Column.F, backRow), Type.BISHOP, color);
            findPosition(Column.F, backRow).setPiece(array[11]);
            
            //rooks
            array[12] = new Piece(findPosition(Column.A, backRow), Type.ROOK, color);
            findPosition(Column.A, backRow).setPiece(array[12]);

            array[13] = new Piece(findPosition(Column.H, backRow), Type.ROOK, color);
            findPosition(Column.H, backRow).setPiece(array[13]);
            
            //queen
            array[14] = new Piece(findPosition(Column.D, backRow), Type.QUEEN, color);
            findPosition(Column.D, backRow).setPiece(array[14]);
            
            //king
            array[15] = new Piece(findPosition(Column.E, backRow), Type.KING, color);
            findPosition(Column.E, backRow).setPiece(array[15]);
            
            i++;
        }
    }
    
    public static Position findPosition(Column column, Row row) {
        //returns a certain position based on row and column
        for (Position position : positions) {
            if (position.getRow() == row && position.getColumn() == column) {
                return position;
            }
        }
        return null;
    }

    public void reset() {
        //resets the board
        for (Position pos : positions) {
            pos.removePiece();
        }

        addPieces();
    }
}

package chess;

import chess.Piece.Type;
import chess.Position.Column;
import chess.Position.Row;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Board extends GridPane {

    public Piece[] black, white;
    private static ArrayList<Position> positions;
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
                int index = findIndex(columns[j], frontRow);
                Position position = positions.get(index);
                array[j] = new Piece(position, Type.PAWN, color);
                positions.get(index).setPiece(array[j]);
            }
            
            //knights
            int index = findIndex(Column.B, backRow);
            array[8] = new Piece(positions.get(index), Type.KNIGHT, color);
            positions.get(index).setPiece(array[8]);
            
            index = findIndex(Column.G, backRow);
            array[9] = new Piece(positions.get(index), Type.KNIGHT, color);
            positions.get(index).setPiece(array[9]);
            
            //bishops
            index = findIndex(Column.C, backRow);
            array[10] = new Piece(positions.get(index), Type.BISHOP, color);
            positions.get(index).setPiece(array[10]);
            
            index = findIndex(Column.F, backRow);
            array[11] = new Piece(positions.get(index), Type.BISHOP, color);
            positions.get(index).setPiece(array[11]);
            
            //rooks
            index = findIndex(Column.A, backRow);
            array[12] = new Piece(positions.get(index), Type.ROOK, color);
            positions.get(index).setPiece(array[12]);
            
            index = findIndex(Column.H, backRow);
            array[13] = new Piece(positions.get(index), Type.ROOK, color);
            positions.get(index).setPiece(array[13]);
            
            //queen
            index = findIndex(Column.D, backRow);
            array[14] = new Piece(positions.get(index), Type.QUEEN, color);
            positions.get(index).setPiece(array[14]);
            
            //king
            index = findIndex(Column.E, backRow);
            array[15] = new Piece(positions.get(index), Type.KING, color);
            positions.get(index).setPiece(array[15]);
            
            i++;
        }
    }

    private static int findIndex(Column column, Row row) {
        for (Position position : positions) {
            if (position.getRow() == row && position.getColumn() == column) {
                return positions.indexOf(position);
            }
        }
        return -1;
    }
    
    public static Position findPosition(Column column, Row row) {
        for (Position position : positions) {
            if (position.getRow() == row && position.getColumn() == column) {
                return position;
            }
        }
        return null;
    }
}

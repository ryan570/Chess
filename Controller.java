package chess;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class Controller extends BorderPane {

    public Controller(Board board) {
        //extra stage for resetting the chess board
        Button reset = new Button("Reset");
        reset.setOnAction(e -> {
            board.reset();
            GameController.turn = true;
            GameController.selected = false;
            GameController.currentMove = 0;
            GameController.play();
        });
        setCenter(reset);
    }
}

package chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Chess extends Application {

    public static Scene scene;

    @Override
    public void start(Stage primaryStage) {
        //prevents npe
        scene = new Scene(new Pane(), 400, 400);

        //creates the board
        Board root = new Board();
        scene.setRoot(root);

        //creates the logic engine
        new GameController(root);
        
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.R) {
                root.reset();
                GameController.turn = true;
                GameController.selected = false;
                GameController.currentMove = 0;
                GameController.play();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
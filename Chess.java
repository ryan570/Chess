package chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Chess extends Application {

    public static Scene scene;

    @Override
    public void start(Stage primaryStage) {
        scene = new Scene(new Pane(), 400, 400);

        Board root = new Board();
        new GameController(root);

        scene.setRoot(root);
        
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
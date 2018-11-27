package chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Chess extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Board root = new Board();
        
        Scene scene = new Scene(root, 400, 400);
        
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

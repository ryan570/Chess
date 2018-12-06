package chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Chess extends Application {

    public static Scene scene;

    @Override
    public void start(Stage primaryStage) {
        //prevents npe
        scene = new Scene(new Pane(), 400, 420);

        //creates the board
        Board root = new Board();
        scene.setRoot(root);

        //creates the logic engine
        new GameController(root);

        //creates the reset stage
        Controller controller = new Controller(root);

        Stage controllerStage = new Stage();
        Scene controllerScene = new Scene(controller, 125, 40);
        controllerStage.setScene(controllerScene);
        controllerStage.setTitle("Reset");
        controllerStage.setResizable(false);
        
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
        controllerStage.show();

        //both stages close at once
        primaryStage.setOnCloseRequest(e-> controllerStage.close());
        controllerStage.setOnCloseRequest(e-> primaryStage.close());
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
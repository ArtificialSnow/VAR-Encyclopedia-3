package main.java.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage _primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        _primaryStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/scenes/MainMenuScene.fxml"));
        primaryStage.setScene(new Scene(root, 1000, 500));
        //primaryStage.setTitle("VAR Encyclopedia");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

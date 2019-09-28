package main.java.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    FileDirectory _fileDirectory;

    @Override
    public void start(Stage primaryStage) throws Exception {
        _fileDirectory = new FileDirectory();
        _fileDirectory.create();

        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/scenes/MainMenuScene.fxml"));
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.setResizable(false);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("VAR-Encyclopedia");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

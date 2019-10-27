package main.java.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This Class is the main class of the application. It starts the application by calling launch(args) and sets the scene to the Main Menu.
 * It also makes a call to the FileDirectory class which checks if the necessary file structure has been created, and creates the files if they do not exist.
 */
public class Main extends Application {

    FileDirectory _fileDirectory;

    @Override
    public void start(Stage primaryStage) throws Exception {
        _fileDirectory = new FileDirectory();
        _fileDirectory.create();

        Parent root = FXMLLoader.load(getClass().getResource(SceneType.MainMenuScene.getPath()));
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

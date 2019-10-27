package main.java.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import main.java.app.SceneType;

import java.io.IOException;

/**
 * This class is an abstract class which provides shared functionality to all Scene classes in this Application.
 * The shared functionality includes changing scenes, and creating alerts.
 */
public abstract class ApplicationScene {

    /**
     * Changes the current scene to a Scene specified by a SceneType enum which provides the path to that Scene.
     * @param sceneType
     * @param event
     * @return The controller object of that scene.
     * @throws IOException
     */
    public ApplicationScene changeScene(SceneType sceneType, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneType.getPath()));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        return loader.getController();
    }

    public void createInformationAlert(String alertTitle, String alertMessage) {
        Alert noContentAlert = new Alert(Alert.AlertType.INFORMATION);
        noContentAlert.setTitle(alertTitle);
        noContentAlert.setHeaderText(null);
        noContentAlert.setContentText(alertMessage);
        noContentAlert.showAndWait();
    }

    public Alert createConfirmationAlert(String alertMessage) {
        Alert overrideAlert = new Alert(Alert.AlertType.CONFIRMATION, alertMessage, ButtonType.YES, ButtonType.NO);
        overrideAlert.setHeaderText(null);
        overrideAlert.showAndWait();

        return overrideAlert;
    }

    public void createQuitAlert() {
        Alert searchAlert = createConfirmationAlert("Are you sure you wish quit? You will lose all existing progress.");

        if (searchAlert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }
}

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

public abstract class ApplicationScene {

    public void changeScene(SceneType sceneType, ActionEvent event) throws IOException {
        Parent viewExistingCreationsParent = FXMLLoader.load(getClass().getResource(sceneType.getPath()));
        Scene viewExistingCreationsScene = new Scene(viewExistingCreationsParent);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(viewExistingCreationsScene);
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

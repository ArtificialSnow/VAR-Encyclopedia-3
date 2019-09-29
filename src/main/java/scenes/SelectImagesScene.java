package main.java.scenes;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.app.CreationFactory;
import main.java.app.SceneType;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectImagesScene extends ApplicationScene {

    @FXML private Button _homeButton;
    @FXML private Button _quitButton;
    @FXML private Button _addImageButton;
    @FXML private Button _removeImageButton;
    @FXML private Button _shiftImageUpButton;
    @FXML private Button _shiftImageDownButton;
    @FXML private Button _createCreationButton;
    @FXML private TextField _creationName;
    @FXML private ListView<String> _downloadedImagesListView;
    @FXML private ListView<String> _selectedImagesListView;

    private String _searchTerm;
    private CreationFactory _creationFactory;


    @FXML
    public void initialize() {
        _creationFactory = new CreationFactory();

        File[] downloadList = new File("./VAR-Encyclopedia/.temp/Images").listFiles();
        ArrayList<String> nameOfImages = new ArrayList<String>();
        for (File searchTermDirectory : downloadList) {
            nameOfImages.add(searchTermDirectory.getName());
            _downloadedImagesListView.getItems().add(searchTermDirectory.getName());
        }

        _downloadedImagesListView.setCellFactory(param -> new ListCell<String>(){
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty){
                super.updateItem(name,empty);
                if(empty){
                    setText(null);
                    setGraphic(null);
                }
                else {
                    File image = new File("./VAR-Encyclopedia/.temp/Images/" + name);
                    imageView.setImage(new Image(image.toURI().toString()));
                    setAlignment(Pos.CENTER);
                    setGraphic(imageView);
                }
            }
        });

        _selectedImagesListView.setCellFactory(param -> new ListCell<String>(){
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty){
                super.updateItem(name, empty);

                if(empty){
                    setText(null);
                    setGraphic(null);
                } else {
                    File image = new File("./VAR-Encyclopedia/.temp/Images/" + name);
                    imageView.setImage(new Image(image.toURI().toString()));
                    setAlignment(Pos.CENTER);
                    setGraphic(imageView);
                }
            }
        });
    }

    public void setSearchTerm(String searchTerm) {
        _searchTerm = searchTerm;
    }

    public void addImageButtonHandler() {
        String imageToAdd = _downloadedImagesListView.getSelectionModel().getSelectedItem();

        if (imageToAdd == null) {
            createInformationAlert("No Image Selected", "Please select a Image");
        } else {
            if (_selectedImagesListView.getItems().contains(imageToAdd)) {
                createInformationAlert("Image already added", "Image " + imageToAdd + " already added");
            } else {
                _selectedImagesListView.getItems().add(imageToAdd);
            }
        }
    }

    public void removeImageButtonHandler() {
        String imageToRemove = _selectedImagesListView.getSelectionModel().getSelectedItem();
        if (imageToRemove == null) {
            createInformationAlert("No Image Selected", "Please select an Image");
        } else {
            _selectedImagesListView.getItems().remove(imageToRemove);
        }
    }

    public void shiftImageUpButtonHandler() {
        int selectedImage = _selectedImagesListView.getSelectionModel().getSelectedIndex();
        if (selectedImage == -1) {
            createInformationAlert("No Image selected", "Please select a Image");
        } else if (selectedImage == 0){
            createInformationAlert("Cannot shift Image up further", "Cannot shift Image up further");
        } else {
            String audioChunk = _selectedImagesListView.getItems().get(selectedImage);
            _selectedImagesListView.getItems().remove(selectedImage);
            _selectedImagesListView.getItems().add((selectedImage - 1), audioChunk);
            _selectedImagesListView.getSelectionModel().select(selectedImage - 1);
        }
    }

    public void shiftImageDownButtonHandler() {
        int selectedImage = _selectedImagesListView.getSelectionModel().getSelectedIndex();
        if (selectedImage == -1) {
            createInformationAlert("No Image selected", "Please select a Image");
        } else if (selectedImage == _selectedImagesListView.getItems().size() - 1){
            createInformationAlert("Cannot shift Image down further", "Cannot shift Image down further");
        } else {
            String audioChunk = _selectedImagesListView.getItems().get(selectedImage);
            _selectedImagesListView.getItems().remove(selectedImage);
            _selectedImagesListView.getItems().add((selectedImage + 1), audioChunk);
            _selectedImagesListView.getSelectionModel().select(selectedImage + 1);
        }
    }

    public void createCreationButtonHandler(ActionEvent event) throws IOException {
        String creationName = _creationName.getText().replaceAll("(^\\s+)|(\\s+$)", "");
        int numberOfImages = _selectedImagesListView.getItems().size();

        if (creationName == null || creationName.length() == 0) {
            createInformationAlert("No Creation Name entered", "Please enter a name for your creation.");
        } else if (numberOfImages == 0) {
            createInformationAlert("No Images selected", "Please select Images for your Creation");
        } else {

            File[] previousCreations = new File("./VAR-Encyclopedia/Creations").listFiles();
            for (File previousCreation : previousCreations) {
                String previousCreationName = previousCreation.getName();
                if (previousCreationName.substring(0, previousCreationName.length() - 4).equals(creationName)) {
                    Alert overrideAlert = createConfirmationAlert("A Creation with that name already exists. Would you like to override " + creationName + "?");

                    if (overrideAlert.getResult() == ButtonType.YES) {
                        createCreation(creationName, numberOfImages, event);
                    }
                }
                createCreation(creationName, numberOfImages, event);
            }
        }
    }

    public void createCreation(String creationName, int numberOfImages, ActionEvent event) {
        String imageNames = "";
        for (String imageName : _selectedImagesListView.getItems()) {
            imageNames += imageName + " ";
        }

        String finalImageNames = imageNames;
        new Thread(new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                _creationFactory.combineImagesToVideo(finalImageNames, numberOfImages);
                _creationFactory.combineVideoAndText(_searchTerm);
                _creationFactory.combineVideoAndAudio(creationName);
                return null;
            }

            @Override
            protected void done() {
                Platform.runLater( () -> {
                    try {
                        changeScene(SceneType.MainMenuScene, event);
                    } catch (Exception e){
                        System.out.println("Error changing back to MainMenu Scene");
                    }
                });
            }
        });
    }

    public void homeButtonHandler(ActionEvent event) throws IOException {
        Alert homeAlert = createConfirmationAlert("Are you sure you want to go back to the Main Menu? You will lose all existing progress");
        if (homeAlert.getResult() == ButtonType.YES) {
            changeScene(SceneType.MainMenuScene, event);
        }
    }

    public void quitButtonHandler() {
        createQuitAlert();
    }
}

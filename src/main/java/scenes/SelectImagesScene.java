package main.java.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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


    //"./VAR-Encyclopedia/.temp/tempCombinedChunks.wav"
    //"./VAR-Encyclopedia/downloads"
    @FXML
    public void initialize() {
        _creationFactory = new CreationFactory();

        File[] downloadList = new File("./VAR-Encyclopedia/.temp/Images").listFiles();
        ArrayList<String> nameOfImages = new ArrayList<String>();
        for (File searchTermDirectory : downloadList) {
           nameOfImages.add(searchTermDirectory.getName());
            _downloadedImagesListView.getItems().add(searchTermDirectory.getName());
        }

        Image[] imageList = new Image[downloadList.length];
        for (int i = 0; i < downloadList.length;i++ ) {
            File image = new File("./VAR-Encyclopedia/.temp/Images/"+downloadList[i].getName());
            String filelocation = image.toURI().toString();
            Image fxImage = new Image(filelocation);
            imageList[i]=fxImage;
        }

        _downloadedImagesListView.setCellFactory(param -> new ListCell<String>(){
            private ImageView imageview = new ImageView();
            @Override
            public void updateItem(String name, boolean empty){
                super.updateItem(name,empty);
                if(empty){
                    setText(null);
                    setGraphic(null);
                }
                else {
                    for (int i = 0; i < downloadList.length;i++ ) {
                        if (name.equals(nameOfImages.get(i))){
                        imageview.setImage(imageList[i]);
                    }
                        setText(name);
                        setGraphic(imageview);
                    }
                }
            }
        });
    }

    public void setSearchTerm(String searchTerm) {
        _searchTerm = searchTerm;
    }

    public void updateDownloadList(){
        File[] downloadList = new File("./downloads").listFiles();
        for(File fileToDelete : downloadList){
            if(_downloadedImagesListView.getItems().contains(fileToDelete.getName()) && _selectedImagesListView.getItems().contains(fileToDelete.getName())){

            }else{
                fileToDelete.delete();
            }
        }
    }
    public void addImageButtonHandler() {
        String imageToAdd = _downloadedImagesListView.getSelectionModel().getSelectedItem();

        if (imageToAdd == null) {
            createInformationAlert("No image Selected", "Please select a image");
        } else {
            if (_selectedImagesListView.getItems().contains(imageToAdd)) {
                createInformationAlert("image already added", "Image " + imageToAdd + " already added");
            } else {
                _selectedImagesListView.getItems().add(imageToAdd);
            }
        }
    }

    public void removeImageButtonHandler() { String imageToAdd = _downloadedImagesListView.getSelectionModel().getSelectedItem();
        String imageToRemove = _downloadedImagesListView.getSelectionModel().getSelectedItem();
        if (imageToRemove == null) {
            createInformationAlert("No Audio Chunk Selected", "Please select an Audio Chunk");
        } else {
            _selectedImagesListView.getItems().remove(imageToRemove);
        }
    }

    public void shiftImageUpButtonHandler() {
        int selectedImage = _selectedImagesListView.getSelectionModel().getSelectedIndex();
        if (selectedImage == -1) {
            createInformationAlert("No Image selected", "Please select a image");
        } else if (selectedImage == 0){
            createInformationAlert("Cannot shift image up further", "Cannot shift image up further");
        } else {
            String audioChunk = _selectedImagesListView.getItems().get(selectedImage);
            _selectedImagesListView.getItems().remove(selectedImage);
            _selectedImagesListView.getItems().add((selectedImage - 1), audioChunk);
        }
    }

    public void shiftImageDownButtonHandler() {
        int selectedImage = _selectedImagesListView.getSelectionModel().getSelectedIndex();
        if (selectedImage == -1) {
            createInformationAlert("No Image selected", "Please select a image");
        } else if (selectedImage == _selectedImagesListView.getItems().size() - 1){
            createInformationAlert("Cannot shift image down further", "Cannot shift image down further");
        } else {
            String audioChunk = _selectedImagesListView.getItems().get(selectedImage);
            _selectedImagesListView.getItems().remove(selectedImage);
            _selectedImagesListView.getItems().add((selectedImage + 1), audioChunk);
        }
    }

    public void createCreationButtonHandler(ActionEvent event) throws IOException {
        updateDownloadList();
        _creationFactory.combineImagesToVideo(_selectedImagesListView.getItems().size());
        System.out.println("finish combine images");
        _creationFactory.combineVideoAndText(_searchTerm);
        System.out.println("finish combine video and text");
        _creationFactory.combineVideoAndAudio(_creationName.getText());
        System.out.println("finish combine video and audio");

        changeScene(SceneType.MainMenuScene, event);
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

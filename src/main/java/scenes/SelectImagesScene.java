package main.java.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.app.CreationFactory;
import main.java.app.SceneType;

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
    @FXML private ListView<String> _imageDownloand;
    @FXML private ListView<String> _imageSelected;

    private String _searchTerm;
    private CreationFactory _creationFactory;


    //"./VAR-Encyclopedia/.temp/tempCombinedChunks.wav"
    //"./VAR-Encyclopedia/downloads"
    @FXML
    public void initialize() {
        _creationFactory = new CreationFactory();

        File[] downloadList = new File("./downloads").listFiles();
        ArrayList<String> nameOfImages = new ArrayList<String>();
        for (File searchTermDirectory : downloadList) {
           nameOfImages.add(searchTermDirectory.getName());
            _imageDownloand.getItems().add(searchTermDirectory.getName());
        }

        Image[] imageList = new Image[downloadList.length];
        for (int i = 0; i < downloadList.length;i++ ) {
            File image = new File("./downloads/"+downloadList[i].getName());
            String filelocation = image.toURI().toString();
            Image fxImage = new Image(filelocation);
            imageList[i]=fxImage;
        }

        _imageDownloand.setCellFactory(param -> new ListCell<String>(){
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
            if(_imageDownloand.getItems().contains(fileToDelete.getName()) && _imageSelected.getItems().contains(fileToDelete.getName())){

            }else{
                fileToDelete.delete();
            }
        }
    }
    public void addImageButtonHandler() {
        String imageToAdd = _imageDownloand.getSelectionModel().getSelectedItem();

        if (imageToAdd == null) {
            createInformationAlert("No image Selected", "Please select a image");
        } else {
            if (_imageSelected.getItems().contains(imageToAdd)) {
                createInformationAlert("image already added", "Image " + imageToAdd + " already added");
            } else {
                _imageSelected.getItems().add(imageToAdd);
            }
        }
    }

    public void removeImageButtonHandler() { String imageToAdd = _imageDownloand.getSelectionModel().getSelectedItem();
        String imageToRemove = _imageDownloand.getSelectionModel().getSelectedItem();
        if (imageToRemove == null) {
            createInformationAlert("No Audio Chunk Selected", "Please select an Audio Chunk");
        } else {
            _imageSelected.getItems().remove(imageToRemove);
        }
    }

    public void shiftImageUpButtonHandler() {
        int selectedImage = _imageSelected.getSelectionModel().getSelectedIndex();
        if (selectedImage == -1) {
            createInformationAlert("No Image selected", "Please select a image");
        } else if (selectedImage == 0){
            createInformationAlert("Cannot shift image up further", "Cannot shift image up further");
        } else {
            String audioChunk = _imageSelected.getItems().get(selectedImage);
            _imageSelected.getItems().remove(selectedImage);
            _imageSelected.getItems().add((selectedImage - 1), audioChunk);
        }
    }

    public void shiftImageDownButtonHandler() {
        int selectedImage = _imageSelected.getSelectionModel().getSelectedIndex();
        if (selectedImage == -1) {
            createInformationAlert("No Image selected", "Please select a image");
        } else if (selectedImage == _imageSelected.getItems().size() - 1){
            createInformationAlert("Cannot shift image down further", "Cannot shift image down further");
        } else {
            String audioChunk = _imageSelected.getItems().get(selectedImage);
            _imageSelected.getItems().remove(selectedImage);
            _imageSelected.getItems().add((selectedImage + 1), audioChunk);
        }
    }

    public void createCreationButtonHandler() {
        updateDownloadList();
        _creationFactory.combineImagesToVideo(_imageSelected.getItems().size());
        _creationFactory.combineVideoAndText(_searchTerm);
        _creationFactory.combineVideoAndAudio(_creationName.getText());
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

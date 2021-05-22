import ForInterface.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainSceneController {
    public Label user;
    public Button help;
    public Button info;
    public Button history;
    public Button clear;
    public Button add;
    public Button add_if_min;
    public Button remove_lower;
    public Button ifFilter;
    public Button group_counting_by_id;
    public Button update_id;
    public Button remove_by_id;
    public Button execute_script;
    public Button count_by_albums_count;
    public Button count_greater_than_albums_count;
    public Button exit;
    public VBox idColumn;
    public VBox nameColumn;
    public VBox coordinateXColumn;
    public VBox coordinateYColumn;
    public VBox creationDateColumn;
    public VBox numberOfParticipantsColumn;
    public VBox albumsCountColumn;
    public VBox establishmentDateColumn;
    public VBox genreColumn;
    public VBox frontNameColumn;
    public VBox frontmanWeightColumn;
    public VBox frontmanEyeColorColumn;
    public VBox frontmanHairColorColumn;
    public VBox frontmanNationalityColumn;
    public VBox userColumn;
    public void start(String login) throws IOException {
        user.setText(login);
        show();
    }

    public void clickHelp() throws IOException {
        Window.info(help.getText(), request("help"));
    }

    public void clickInfo() throws IOException {
        Window.info(info.getText(), request("info"));
    }

    public void clickHistory() throws IOException {
        Window.info(history.getText(), request("history"));
    }

    public void clickGroupCountingById() throws IOException {
        Window.info(group_counting_by_id.getText(), request("group_counting_by_id"));
    }

    public void clickClear() throws IOException {
        request("clear");
        show();
    }

    public void clickExit() {
        System.exit(0);
    }

    public void clickAdd(){
        bigDialogWindow("add");
    }

    public void clickAddIfMin(){
        bigDialogWindow("add_if_min");
    }

    public void clickRemoveLower(){
        bigDialogWindow("remove_lower");
    }

    public void clickUpdateId(){
        smallDialogWindow("update_id", "id", false);
    }

    public void clickRemoveById(){

        smallDialogWindow("remove_by_id", "id", false);
    }

    public void clickExecuteScript(){
        smallDialogWindow("execute_script", "script", false);
    }

    public void clickCountByAlbumsCount(){
        smallDialogWindow("count_by_albums_count", "albumsCount", true);
    }

    public void clickGreaterThanCountByAlbumsCount(){
        smallDialogWindow("count_greater_than_albums_count", "albumsCount", true);
    }

    public void smallDialogWindow(String command, String textFieldName, boolean albumsCount){
        Stage dialogStage = new Stage();
        dialogStage.getIcons().add(new Image("media/note.png"));
        dialogStage.setTitle(command);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setMinWidth(350);
        VBox vBox = new VBox();
        vBox.getStylesheets().add(MainSceneController.class.getResource("scripts/buttonStyle.css").toExternalForm());
        vBox.setSpacing(4.0);
        Button buttonOK = buttonDialog("ОК", 350,25);
        TextField textField = textFieldDialog(textFieldName);
        Label space1 = new Label();
        Label space2 = new Label();
        space1.setText("");
        space2.setText("");
        vBox.getChildren().addAll(space1, textField, space2, buttonOK);
        dialogStage.setScene(new Scene(vBox));
        dialogStage.show();
        buttonOK.setOnAction((event) -> {
            if (textField.getCharacters().isEmpty()){
                Window.warning(textFieldName + " warning", "поле " + textFieldName + " не может быть пустым");
            } else {
                try {
                    String answer = request(command + " " + textField.getCharacters());
                    dialogStage.close();
                    if (albumsCount){
                        Window.info(command, answer);
                    } else{
                        if (!answer.equals("Команда выполнена\n")){
                            Window.error("Command " + command + " error", answer);
                        }
                    }
                    show();
                } catch (IOException e) {
                    Window.error("Shit", "shit happens :(");
                }
            }
        });
    }


    public void bigDialogWindow(String command){
        Stage dialogStage = new Stage();
        dialogStage.getIcons().add(new Image("media/note.png"));
        dialogStage.setTitle(command);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setMinWidth(350);
        dialogStage.setMinHeight(419);
        VBox vBox = new VBox();
        vBox.getStylesheets().add(MainSceneController.class.getResource("scripts/buttonStyle.css").toExternalForm());
        vBox.setSpacing(4.0);

        ObservableList<String> genreElements = FXCollections.observableArrayList("JAZZ", "SOUL", "POST_PUNK");
        ComboBox<String> genre = new ComboBox<String>(genreElements);
        genre.setPromptText("genre");
        genre.setMinWidth(350);
        genre.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: hand; -fx-font-family: 'SFNS Display'");

        ObservableList<String> frontmanColorElements = FXCollections.observableArrayList("BLACK", "YELLOW", "ORANGE", "BROWN");
        ComboBox<String> frontmanEyeColor = new ComboBox<String>(frontmanColorElements);
        frontmanEyeColor.setPromptText("frontmanEyeColor");
        frontmanEyeColor.setMinWidth(350);
        frontmanEyeColor.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: hand; -fx-font-family: 'SFNS Display'");
        ComboBox<String> frontmanHairColor = new ComboBox<String>(frontmanColorElements);
        frontmanHairColor.setPromptText("frontmanHairColor");
        frontmanHairColor.setMinWidth(350);
        frontmanHairColor.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: hand; -fx-font-family: 'SFNS Display'");

        ObservableList<String> frontmanNationalityElements = FXCollections.observableArrayList("UNITED_KINGDOM", "USA", "ITALY", "JAPAN");
        ComboBox<String> frontmanNationality = new ComboBox<String>(frontmanNationalityElements);
        frontmanNationality.setPromptText("frontmanNationality");
        frontmanNationality.setMinWidth(350);
        frontmanNationality.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: hand; -fx-font-family: 'SFNS Display'");

        TextField nameField = textFieldDialog("name");
        TextField coordinateXField = textFieldDialog("coordinateX");
        TextField coordinateYField = textFieldDialog("coordinateY");
        TextField numberOfParticipants = textFieldDialog("numberOfParticipants");
        TextField albumsCount = textFieldDialog("albumsCount");
        TextField establishmentDate = textFieldDialog("establishmentDate");
        TextField frontmanName = textFieldDialog("frontmanName");
        TextField frontmanWeight = textFieldDialog("frontmanWeight");
        Label space1 = new Label();
        Label space2 = new Label();
        space1.setText("");
        space2.setText("");
        Button buttonOK = buttonDialog("ОК", 350,25);
        vBox.getChildren().addAll(space1, nameField, coordinateXField, coordinateYField, numberOfParticipants, albumsCount, establishmentDate);
        vBox.getChildren().addAll(genre, frontmanName, frontmanWeight, frontmanEyeColor, frontmanHairColor, frontmanNationality, space2,  buttonOK);
        dialogStage.setScene(new Scene(vBox));
        dialogStage.show();
        buttonOK.setOnAction((event) -> {
            if (nameField.getCharacters().isEmpty()){
                Window.warning("nameField warning", " поле nameField не может быть пустым");
            } else if (coordinateXField.getCharacters().isEmpty()){
                Window.warning("coordinateXField warning", " поле coordinateXField не может быть пустым");
            } else if (coordinateYField.getCharacters().isEmpty()){
                Window.warning("coordinateYField warning", " поле coordinateYField не может быть пустым");
            } else if (numberOfParticipants.getCharacters().isEmpty()){
                Window.warning("numberOfParticipants warning", " поле numberOfParticipants не может быть пустым");
            } else if (albumsCount.getCharacters().isEmpty()){
                Window.warning("albumsCount warning", " поле albumsCount не может быть пустым");
            } else if (establishmentDate.getCharacters().isEmpty()){
                Window.warning("establishmentDate warning", " поле establishmentDate не может быть пустым");
            } else if (genre.getValue() == null){
                Window.warning("genre warning", " поле genre не может быть пустым");
            } else if (frontmanName.getCharacters().isEmpty()){
                Window.warning("frontmanName warning", " поле frontmanName не может быть пустым");
            } else if (frontmanWeight.getCharacters().isEmpty()){
                Window.warning("frontmanWeight warning", " поле frontmanWeight не может быть пустым");
            } else if (frontmanEyeColor.getValue() == null){
                Window.warning("frontmanEyeColor warning", " поле frontmanEyeColor не может быть пустым");
            } else if (frontmanHairColor.getValue() == null){
                Window.warning("frontmanHairColor warning", " поле frontmanHairColor не может быть пустым");
            } else if (frontmanNationality.getValue() == null){
                Window.warning("frontmanNationality warning", " поле frontmanNationality не может быть пустым");
            } else {
                try {
                    String answer = request(command + " " + nameField.getCharacters() + "," + coordinateXField.getCharacters() + "," + coordinateYField.getCharacters() + ","
                            + numberOfParticipants.getCharacters() + "," + albumsCount.getCharacters() + "," + establishmentDate.getCharacters() + ","
                            + genre.getValue() + "," + frontmanName.getCharacters() + "," + frontmanWeight.getCharacters() + ","
                            + frontmanEyeColor.getValue() + "," + frontmanHairColor.getValue() + "," + frontmanNationality.getValue());
                    dialogStage.close();
                    if (!answer.equals("Команда выполнена\n")){
                        Window.error("Command " + command + " error", answer);
                    }
                    show();
                } catch (IOException e) {
                    Window.error("Shit", "shit happens :(");
                }
            }
        });
    }

    public TextField textFieldDialog(String name){
        TextField textField = new TextField();
        textField.setPromptText(name);
        textField.setAlignment(Pos.CENTER_LEFT);
        textField.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: text; -fx-font-family: 'SFNS Display'");
        return textField;
    }

    public Button buttonDialog(String name, double x, double y){
        Button button = new Button();
        button.setText(name);
        button.setAlignment(Pos.CENTER);
        button.setMinSize(x,y);
        button.setStyle("-fx-border-color: #EC0B43; -fx-background-color:  #EC0B43; -fx-cursor: hand; -fx-text-fill: #FFFFFF; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-font-family: 'SFNS Display'");
        return button;
    }



    public void show() throws IOException {
        idColumn.getChildren().size();
        for (int i = 1; i < idColumn.getChildren().size();){
            idColumn.getChildren().remove(i);
            nameColumn.getChildren().remove(i);
            coordinateXColumn.getChildren().remove(i);
            coordinateYColumn.getChildren().remove(i);
            creationDateColumn.getChildren().remove(i);
            numberOfParticipantsColumn.getChildren().remove(i);
            albumsCountColumn.getChildren().remove(i);
            establishmentDateColumn.getChildren().remove(i);
            genreColumn.getChildren().remove(i);
            frontNameColumn.getChildren().remove(i);
            frontmanWeightColumn.getChildren().remove(i);
            frontmanEyeColorColumn.getChildren().remove(i);
            frontmanHairColorColumn.getChildren().remove(i);
            frontmanNationalityColumn.getChildren().remove(i);
            userColumn.getChildren().remove(i);
        }
        String collection = request("show");
        String[] collectionHalfFields = collection.split("\n");
        for (int j = 0; j < collectionHalfFields.length; j++) {
            String[] collectionFields = collectionHalfFields[j].split(",");
            if (collectionFields.length == 15) {
                for (int i = 0; i < collectionFields.length; i++) {
                    if (((i + 1) % 15) == 1) {
                        idColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 2) {
                        nameColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 3) {
                        coordinateXColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 4) {
                        coordinateYColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 5) {
                        creationDateColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 6) {
                        numberOfParticipantsColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 7) {
                        albumsCountColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 8) {
                        establishmentDateColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 9) {
                        genreColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 10) {
                        frontNameColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 11) {
                        frontmanWeightColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 12) {
                        frontmanEyeColorColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 13) {
                        frontmanHairColorColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 14) {
                        frontmanNationalityColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    } else if (((i + 1) % 15) == 0) {
                        userColumn.getChildren().addAll(collectionElementPart(collectionFields[i]));
                    }
                }
            }
        }
    }

    public TextField collectionElementPart(String name){
        TextField textField = new TextField();
        textField.setText(name);
        textField.setAlignment(Pos.CENTER);
        textField.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: text; -fx-text-fill: #1946ba; -fx-font-family: 'SFNS Display'");
        return textField;
    }


    public String request(String message) throws IOException {
        ClientInterface.write(message);
        return ClientInterface.read();
    }
}

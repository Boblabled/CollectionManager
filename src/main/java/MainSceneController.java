import Elements.MusicBand;
import Window.Window;
import Manager.Manager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import Manager.LocaleManager;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.DateFormat.*;

public class MainSceneController {
    public Label user;
    public Button help;
    public Button info;
    public Button history;
    public Button clear;
    public Button add;
    public Button add_if_min;
    public Button remove_lower;
    public Button idSorter;
    public Button coordinateXSorter;
    public Button coordinateYSorter;
    public Button creationDateSorter;
    public Button numberOfParticipantsSorter;
    public Button albumsCountSorter;
    public Button establishmentDateSorter;
    public Button genreSorter;
    public Button frontmanNameSorter;
    public Button frontmanWeightSorter;
    public Button frontmanEyeColorSorter;
    public Button frontmanHairColorSorter;
    public Button frontmanNationalitySorter;
    public Button userSorter;
    public Button group_counting_by_id;
    public Button update_id;
    public Button remove_by_id;
    public Button execute_script;
    public Button count_by_albums_count;
    public Button count_greater_than_albums_count;
    public Button exit;
    public Button nameFilter;
    public GridPane mainTable;
    public RowConstraints filterRow;
    public TextField filter;
    public ComboBox<String> language;
    public Label commandsLabel;
    public TextField localDate;
    public static TextField[][] elementsTextField = new TextField[0][0];
    public static ComboBox[][] elementsComboBox = new ComboBox[0][0];
    public static Button[][] functionButtons = new Button[0][0];
    public List<MusicBand> collection = new ArrayList<MusicBand>();

    public void start(String login) throws IOException {
        user.setText(login);
        setLocale(LocaleManager.getLocale());
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
        dialogStage.setMinHeight(137);
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
                try {
                    Window.warning(textFieldName + " " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " " + textFieldName + " " + LocaleManager.localizer("window.empty"));
                } catch (UnsupportedEncodingException e) {
                    Window.error("Shit", "shit happens :(");
                }
            } else {
                try {
                    String answer = request(command + " " + textField.getCharacters());
                    dialogStage.close();
                    if (albumsCount){
                        Window.info(command, answer);
                    } else{
                        if (!answer.equals(LocaleManager.localizer("execution.success") + "\n")){
                            Window.error(LocaleManager.localizer("window.command") + " " + command + " " + LocaleManager.localizer("window.error"), answer);
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
        dialogStage.setMinHeight(463);
        VBox vBox = new VBox();
        vBox.getStylesheets().add(MainSceneController.class.getResource("scripts/buttonStyle.css").toExternalForm());
        vBox.setSpacing(4.0);

        ObservableList<String> genreElements = FXCollections.observableArrayList("JAZZ", "SOUL", "POST_PUNK");
        ComboBox<String> genre = new ComboBox<String>(genreElements);
        genre.setPromptText("genre");
        genre.setMinWidth(350);
        genre.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: hand; -fx-font-family: 'SFNS Display'");
        genre.setButtonCell(new ListCell(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item==null){
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                } else {
                    setStyle("-fx-text-fill: -fx-text-inner-color");
                    setText(item.toString());
                }
            }

        });

        ObservableList<String> frontmanColorElements = FXCollections.observableArrayList("BLACK", "YELLOW", "ORANGE", "BROWN");
        ComboBox<String> frontmanEyeColor = new ComboBox<String>(frontmanColorElements);
        frontmanEyeColor.setPromptText("frontmanEyeColor");
        frontmanEyeColor.setMinWidth(350);
        frontmanEyeColor.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: hand; -fx-font-family: 'SFNS Display'");
        frontmanEyeColor.setButtonCell(new ListCell(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item==null){
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                } else {
                    setStyle("-fx-text-fill: -fx-text-inner-color");
                    setText(item.toString());
                }
            }

        });
        ComboBox<String> frontmanHairColor = new ComboBox<String>(frontmanColorElements);
        frontmanHairColor.setPromptText("frontmanHairColor");
        frontmanHairColor.setMinWidth(350);
        frontmanHairColor.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: hand; -fx-font-family: 'SFNS Display'");
        frontmanHairColor.setButtonCell(new ListCell(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item==null){
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                } else {
                    setStyle("-fx-text-fill: -fx-text-inner-color");
                    setText(item.toString());
                }
            }

        });

        ObservableList<String> frontmanNationalityElements = FXCollections.observableArrayList("UNITED_KINGDOM", "USA", "ITALY", "JAPAN");
        ComboBox<String> frontmanNationality = new ComboBox<String>(frontmanNationalityElements);
        frontmanNationality.setPromptText("frontmanNationality");
        frontmanNationality.setMinWidth(350);
        frontmanNationality.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: hand; -fx-font-family: 'SFNS Display'");
        frontmanNationality.setButtonCell(new ListCell(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item==null){
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                } else {
                    setStyle("-fx-text-fill: -fx-text-inner-color");
                    setText(item.toString());
                }
            }

        });
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
            try {
                if (nameField.getCharacters().isEmpty()) {
                    Window.warning("nameField " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " nameField " + LocaleManager.localizer("window.empty"));
                } else if (coordinateXField.getCharacters().isEmpty()) {
                    Window.warning("coordinateXField " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " coordinateXField " + LocaleManager.localizer("window.empty"));
                } else if (coordinateYField.getCharacters().isEmpty()) {
                    Window.warning("coordinateYField " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " coordinateYField " + LocaleManager.localizer("window.empty"));
                } else if (numberOfParticipants.getCharacters().isEmpty()) {
                    Window.warning("numberOfParticipants " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " numberOfParticipants " + LocaleManager.localizer("window.empty"));
                } else if (albumsCount.getCharacters().isEmpty()) {
                    Window.warning("albumsCount " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " albumsCount " + LocaleManager.localizer("window.empty"));
                } else if (establishmentDate.getCharacters().isEmpty()) {
                    Window.warning("establishmentDate " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " establishmentDate " + LocaleManager.localizer("window.empty"));
                } else if (genre.getValue() == null) {
                    Window.warning("genre " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " genre " + LocaleManager.localizer("window.empty"));
                } else if (frontmanName.getCharacters().isEmpty()) {
                    Window.warning("frontmanName " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " frontmanName " + LocaleManager.localizer("window.empty"));
                } else if (frontmanWeight.getCharacters().isEmpty()) {
                    Window.warning("frontmanWeight " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " frontmanWeight " + LocaleManager.localizer("window.empty"));
                } else if (frontmanEyeColor.getValue() == null) {
                    Window.warning("frontmanEyeColor " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " frontmanEyeColor " + LocaleManager.localizer("window.empty"));
                } else if (frontmanHairColor.getValue() == null) {
                    Window.warning("frontmanHairColor " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " frontmanHairColor " + LocaleManager.localizer("window.empty"));
                } else if (frontmanNationality.getValue() == null) {
                    Window.warning("frontmanNationality " + LocaleManager.localizer("window.warning"), LocaleManager.localizer("window.field") + " frontmanNationality " + LocaleManager.localizer("window.empty"));
                } else {
                    try {
                        String answer = request(command + " " + nameField.getCharacters() + "," + coordinateXField.getCharacters() + "," + coordinateYField.getCharacters() + ","
                                + numberOfParticipants.getCharacters() + "," + albumsCount.getCharacters() + "," + establishmentDate.getCharacters() + ","
                                + genre.getValue() + "," + frontmanName.getCharacters() + "," + frontmanWeight.getCharacters() + ","
                                + frontmanEyeColor.getValue() + "," + frontmanHairColor.getValue() + "," + frontmanNationality.getValue());
                        dialogStage.close();
                        if (!answer.equals(LocaleManager.localizer("execution.success") + "\n")) {
                            Window.error(LocaleManager.localizer("window.command") + " " + command + " " + LocaleManager.localizer("window.error"), answer);
                        }
                        show();
                    } catch (IOException e) {
                        Window.error("Shit", "shit happens :(");
                    }
                }
            } catch (UnsupportedEncodingException e) {
                Window.error("Shit", "shit happens :(");
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
        for (int i = 0; i < elementsTextField.length; i++){
            mainTable.getChildren().removeAll(functionButtons[i][0]);
            mainTable.getChildren().removeAll(functionButtons[i][1]);
            for (int j = 0; j < 15; j++){
                if ((j == 13 || j == 12 || j == 11 || j == 8)){
                    mainTable.getChildren().removeAll(elementsComboBox[i][j]);
                } else mainTable.getChildren().removeAll(elementsTextField[i][j]);
            }
        }
        String collectionOfElements = request("show");
        String[] collectionHalfFields = collectionOfElements.split("\n");
        elementsTextField = new TextField[collectionHalfFields.length][15];
        elementsComboBox = new ComboBox[collectionHalfFields.length][15];
        functionButtons = new Button[collectionHalfFields.length][2];
        collection.clear();
        for (int row = 0; row < collectionHalfFields.length; row++) {
            if (!collectionHalfFields[0].equals(LocaleManager.localizer("execution.collectionEmpty"))) {
                Manager manager = new Manager();
                manager.fill(collectionHalfFields[row],collection);
            }
            String[] collectionFields = collectionHalfFields[row].split(",");
            visualiser(row, collectionFields);
        }
        clickIdSorter();
    }

    public void visualiser(int row, String[] collectionFields){
        if (collectionFields.length == 15) {
            Button trashButton = functionButton(true, row, 1);
            Button visionButton = functionButton(false, row, 0);
            mainTable.add(trashButton,1,row+1);
            mainTable.add(visionButton,0, row+1);
            functionButtons[row][0] = visionButton;
            functionButtons[row][1] = trashButton;
            for (int column = 0; column < collectionFields.length; column++) {
                if (((column + 1) % 15) == 1) {
                    TextField textField = collectionElementPart(collectionFields[column], row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsTextField[row][column] = textField;
                    mainTable.add(textField,column+2,row+1);
                } else if (((column + 1) % 15) == 2) {
                    TextField textField = collectionElementPart(collectionFields[column], row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsTextField[row][column] = textField;
                    mainTable.add(textField,column+2,row+1);
                } else if (((column + 1) % 15) == 3) {
                    TextField textField = collectionElementPart(collectionFields[column], row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsTextField[row][column] = textField;
                    mainTable.add(textField,column+2,row+1);
                } else if (((column + 1) % 15) == 4) {
                    TextField textField = collectionElementPart(collectionFields[column], row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsTextField[row][column] = textField;
                    mainTable.add(textField,column+2,row+1);
                } else if (((column + 1) % 15) == 5) {
                    TextField textField = collectionElementPart(collectionFields[column], row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsTextField[row][column] = textField;
                    mainTable.add(textField,column+2,row+1);
                } else if (((column + 1) % 15) == 6) {
                    TextField textField = collectionElementPart(collectionFields[column], row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsTextField[row][column] = textField;
                    mainTable.add(textField,column+2,row+1);
                } else if (((column + 1) % 15) == 7) {
                    TextField textField = collectionElementPart(collectionFields[column], row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsTextField[row][column] = textField;
                    mainTable.add(textField,column+2,row+1);
                } else if (((column + 1) % 15) == 8) {
                    TextField textField = collectionElementPart(collectionFields[column], row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsTextField[row][column] = textField;
                    mainTable.add(textField,column+2,row+1);
                } else if (((column + 1) % 15) == 9) {
                    ObservableList<String> genreElements = FXCollections.observableArrayList("JAZZ", "SOUL", "POST_PUNK");
                    ComboBox<String> comboBox = collectionElementPart(collectionFields[column], genreElements, row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsComboBox[row][column] = comboBox;
                    mainTable.add(comboBox,column+2,row+1);
                } else if (((column + 1) % 15) == 10) {
                    TextField textField = collectionElementPart(collectionFields[column], row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsTextField[row][column] = textField;
                    mainTable.add(textField,column+2,row+1);
                } else if (((column + 1) % 15) == 11) {
                    TextField textField = collectionElementPart(collectionFields[column], row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsTextField[row][column] = textField;
                    mainTable.add(textField,column+2,row+1);
                } else if (((column + 1) % 15) == 12) {
                    ObservableList<String> frontmanColorElements = FXCollections.observableArrayList("BLACK", "YELLOW", "ORANGE", "BROWN");
                    ComboBox<String> comboBox = collectionElementPart(collectionFields[column], frontmanColorElements, row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsComboBox[row][column] = comboBox;
                    mainTable.add(comboBox,column+2,row+1);
                } else if (((column + 1) % 15) == 13) {
                    ObservableList<String> frontmanColorElements = FXCollections.observableArrayList("BLACK", "YELLOW", "ORANGE", "BROWN");
                    ComboBox<String> comboBox = collectionElementPart(collectionFields[column], frontmanColorElements, row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsComboBox[row][column] = comboBox;
                    mainTable.add(comboBox,column+2,row+1);
                } else if (((column + 1) % 15) == 14) {
                    ObservableList<String> frontmanNationalityElements = FXCollections.observableArrayList("UNITED_KINGDOM", "USA", "ITALY", "JAPAN");
                    ComboBox<String> comboBox = collectionElementPart(collectionFields[column], frontmanNationalityElements, row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsComboBox[row][column] = comboBox;
                    mainTable.add(comboBox,column+2,row+1);
                } else if (((column + 1) % 15) == 0) {
                    TextField textField = collectionElementPart(collectionFields[column], row, column, Color.decode(String.format("#%X",collectionFields[14].hashCode())));
                    elementsTextField[row][column] = textField;
                    mainTable.add(textField,column+2,row+1);
                }
            }
            idSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(idSorter, Priority.ALWAYS);
            coordinateXSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(coordinateXSorter, Priority.ALWAYS);
            coordinateYSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(coordinateYSorter, Priority.ALWAYS);
            nameFilter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(nameFilter, Priority.ALWAYS);
            numberOfParticipantsSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(numberOfParticipantsSorter, Priority.ALWAYS);
            creationDateSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(creationDateSorter, Priority.ALWAYS);
            albumsCountSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(albumsCountSorter, Priority.ALWAYS);
            establishmentDateSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(establishmentDateSorter, Priority.ALWAYS);
            genreSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(genreSorter, Priority.ALWAYS);
            frontmanNameSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(frontmanNameSorter, Priority.ALWAYS);
            frontmanWeightSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(frontmanWeightSorter, Priority.ALWAYS);
            frontmanEyeColorSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(frontmanEyeColorSorter, Priority.ALWAYS);
            frontmanHairColorSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(frontmanHairColorSorter, Priority.ALWAYS);
            frontmanNationalitySorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(frontmanNationalitySorter, Priority.ALWAYS);
            userSorter.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(userSorter, Priority.ALWAYS);
        }
    }

    public Button functionButton(boolean trash, int indexRow, int indexColumn){
        Button button = new Button();
        Image image;
        mainTable.getStylesheets().add(MainSceneController.class.getResource("scripts/buttonAttractive.css").toExternalForm());
        if (trash) image = new Image(getClass().getResourceAsStream("media/garbage.png"));
        else image = new Image(getClass().getResourceAsStream("media/eye.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        button.setGraphic(imageView);
        button.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px");
        if (trash) button.setOnAction((event) -> {
            String element = elementsTextField[indexRow][0].getText();
            for (int i = 1; i < 15; i++){
                if (i == 13 || i == 12 || i == 11 || i == 8){
                    element = element + "," + elementsComboBox[indexRow][i].getPromptText();
                } else element = element + "," + elementsTextField[indexRow][i].getText();
            }
            try {
                String answer = request("remove " + element);
                if (!answer.equals(LocaleManager.localizer("execution.success") +"\n")){
                    Window.warning(LocaleManager.localizer("window.command") + " remove " + LocaleManager.localizer("window.warning"), answer);
                }
                show();
            } catch (IOException e) {
                Window.error("Shit", "shit happens :(");
            }
        });
        else button.setOnAction(actionEvent -> {
            String element = elementsTextField[indexRow][0].getText();
            for (int i = 1; i < 15; i++){
                if (i == 13 || i == 12 || i == 11 || i == 8){
                    element = element + "," + elementsComboBox[indexRow][i].getPromptText();
                } else element = element + "," + elementsTextField[indexRow][i].getText();
            }
            String[] elementFields = element.split(",");
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image("media/eye.png"));
            dialogStage.setTitle("Visualisator 3000");
            Group group = new Group();
            javafx.scene.canvas.Canvas canvas = new javafx.scene.canvas.Canvas(400,400);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            drawElement(gc, elementFields[1], elementFields[11], elementFields[12], elementFields[8], elementFields[13]);
            group.getChildren().add(canvas);
            dialogStage.setScene(new Scene(group));
            dialogStage.setMaxWidth(520);
            dialogStage.setMaxHeight(540);
            dialogStage.show();
        });
        return button;
    }

    public void drawElement(GraphicsContext gc, String bandName, String eyeColor, String hairColor, String genre, String nationality){
        gc.setLineWidth(5);
        Timeline timeline = new Timeline();

        // тело
        for (int i = 0; i < 200; i++) {
            int finalI = i*2;
            KeyFrame body = new KeyFrame(Duration.millis(i*10), actionEvent -> {
                switch (genre) {
                    case "JAZZ" -> gc.setFill(javafx.scene.paint.Color.ROYALBLUE);
                    case "SOUL" -> gc.setFill(javafx.scene.paint.Color.GREY);
                    case "POST_PUNK" -> gc.setFill(javafx.scene.paint.Color.PURPLE);
                }
                gc.fillOval(200 - finalI / 2, 200, finalI, 500);
            });
            timeline.getKeyFrames().addAll(body);
        }

        // надпись
        KeyFrame print = new KeyFrame(Duration.millis(2002), actionEvent -> {
            gc.setFill(javafx.scene.paint.Color.BLACK);
            Font font = new Font("SFNS Display", 40);
            gc.setFont(font);
            int alignment = 200 - (bandName.length() * 10);
            gc.fillText(bandName, alignment, 300);
        });
        timeline.getKeyFrames().addAll(print);

        // голова
        for (int i = 0; i < 150; i++) {
            int finalI = i;
            KeyFrame head = new KeyFrame(Duration.millis(i*10), actionEvent -> {
                gc.clearRect(125, 52, 150, finalI - 1);
                switch (nationality) {
                    case "USA" -> gc.setFill(javafx.scene.paint.Color.BROWN);
                    case "JAPAN" -> gc.setFill(javafx.scene.paint.Color.LIGHTGOLDENRODYELLOW);
                    case "UNITED_KINGDOM", "ITALY" -> gc.setFill(javafx.scene.paint.Color.PINK);
                }
                gc.fillOval(125, 52, 150, finalI);
            });
            timeline.getKeyFrames().addAll(head);
        }

        // лицо
        KeyFrame face = new KeyFrame(Duration.millis(2002), actionEvent -> {
            // волосы
            switch (hairColor) {
                case "YELLOW" -> gc.setFill(javafx.scene.paint.Color.YELLOW);
                case "BLACK" -> gc.setFill(javafx.scene.paint.Color.BLACK);
                case "BROWN" -> gc.setFill(javafx.scene.paint.Color.SADDLEBROWN);
                case "ORANGE" -> gc.setFill(javafx.scene.paint.Color.ORANGE);
            }
            gc.fillArc(120, 52, 160, 160, 20, 150, ArcType.OPEN);

            // глаза
            if (!nationality.equals("JAPAN")) {
                gc.setFill(javafx.scene.paint.Color.WHITE);
                gc.fillOval(155, 115, 30, 30);
                gc.fillOval(215, 115, 30, 30);
                switch (eyeColor) {
                    case "YELLOW" -> gc.setFill(javafx.scene.paint.Color.YELLOW);
                    case "BLACK" -> gc.setFill(javafx.scene.paint.Color.BLACK);
                    case "BROWN" -> gc.setFill(javafx.scene.paint.Color.SADDLEBROWN);
                    case "ORANGE" -> gc.setFill(javafx.scene.paint.Color.ORANGE);
                }
                gc.fillOval(162, 122, 15, 15);
                gc.fillOval(223, 122, 15, 15);
                gc.setFill(javafx.scene.paint.Color.BLACK);
                gc.setStroke(javafx.scene.paint.Color.BLACK);
                gc.strokeLine(155, 115, 185, 115);
                gc.strokeLine(215, 115, 245, 115);
                gc.fillOval(164, 124, 10, 10);
                gc.fillOval(225, 124, 10, 10);

                //Цепь
                if (nationality.equals("USA")) {
                    gc.setStroke(javafx.scene.paint.Color.GOLDENROD);
                    gc.strokeArc(160, 168, 80, 80, 180, 180, ArcType.OPEN);
                }
            } else {
                // глаза
                gc.setStroke(javafx.scene.paint.Color.BLACK);
                gc.strokeLine(155, 130, 185, 135);
                gc.strokeLine(215, 135, 245, 130);
            }
            // рот
            gc.setStroke(javafx.scene.paint.Color.DARKRED);
            gc.strokeArc(170, 120, 60, 60, 225, 90, ArcType.OPEN);
        });
        timeline.getKeyFrames().addAll(face);
        timeline.play();
    }

    public ComboBox<String> collectionElementPart (String name, ObservableList<String> variants, int indexRow, int indexColumn, Color color){
        String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        ComboBox<String> comboBox = new ComboBox<String>(variants);
        comboBox.setPromptText(name);
        comboBox.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: hand; -fx-text-fill: #1946ba; -fx-font-family: 'SFNS Display'");
        comboBox.setButtonCell(new ListCell(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item==null){
                    setStyle("-fx-text-fill: " + hex);
                } else {
                    setStyle("-fx-text-fill: " + hex);
                    setText(item.toString());
                }
            }
        });
        mainTable.getStylesheets().add(MainSceneController.class.getResource("scripts/elementSelected.css").toExternalForm());
        comboBox.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(comboBox, Priority.ALWAYS);
        comboBox.setOnAction(actionEvent -> {
            String element = "";
            String elementOriginal = "";
            String allow = elementsTextField[indexRow][14].getText().toLowerCase();
            if (indexColumn != 0) {
                element = elementsTextField[indexRow][0].getText();
                elementOriginal = elementsTextField[indexRow][0].getText();
            }
            else {
                element = comboBox.getValue();
                elementOriginal = name;
            }
            int i;
            for (i = 1; i < indexColumn; i++){
                if ((i == 13 || i == 12 || i == 11 || i == 8)) {
                    element = element + "," + elementsComboBox[indexRow][i].getPromptText();
                    elementOriginal = elementOriginal + "," + elementsComboBox[indexRow][i].getPromptText();
                } else {
                    element = element + "," + elementsTextField[indexRow][i].getText();
                    elementOriginal = elementOriginal + "," + elementsTextField[indexRow][i].getText();
                }
            }
            if (indexColumn != 0) {
                element = element + "," + comboBox.getValue();
                elementOriginal = elementOriginal + "," + name;
                i = i + 1 ;
            }
            for (; i < 15; i++){
                if ((i == 13 || i == 12 || i == 11 || i == 8)) {
                    element = element + "," + elementsComboBox[indexRow][i].getPromptText();
                    elementOriginal = elementOriginal + "," + elementsComboBox[indexRow][i].getPromptText();
                } else {
                    element = element + "," + elementsTextField[indexRow][i].getText();
                    elementOriginal = elementOriginal + "," + elementsTextField[indexRow][i].getText();
                }
            }
            String answer = null;
            try {
                if (allow.equals(user.getText().toLowerCase())) {
                    answer = request("remove " + elementOriginal);
                    answer = request("add " + element);
                    if (!answer.equals(LocaleManager.localizer("execution.success") + "\n")) {
                        request("add " + elementOriginal);
                        Window.error(LocaleManager.localizer("window.command") + " add " + LocaleManager.localizer("window.error"), LocaleManager.localizer("execution.incorrectEnter"));
                    }
                } else Window.warning(LocaleManager.localizer("window.allowError"),LocaleManager.localizer("window.allowError.message1") + " " + user.getText() + " " + LocaleManager.localizer("window.allowError.message2"));
                show();
                clickIdSorter();
            } catch (IOException e) {
                Window.error("Shit", "shit happens :(");
            }
        });
        return comboBox;
    }

    public TextField collectionElementPart(String name, int indexRow, int indexColumn, Color color){
        String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        TextField textField = new TextField();
        mainTable.getStylesheets().add(MainSceneController.class.getResource("scripts/elementSelected.css").toExternalForm());
        textField.setText(name);
        textField.setAlignment(Pos.CENTER);
        textField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(textField, Priority.ALWAYS);
        if (indexColumn == 0 || indexColumn ==  4 || indexColumn == 14) {
            textField.editableProperty().setValue(false);
            textField.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: default; -fx-font-family: 'SFNS Display'; -fx-text-fill: " + hex);
        } else textField.setStyle("-fx-border-color: #fafafa; -fx-background-color: #ffffff; -fx-cursor: text;  -fx-font-family: 'SFNS Display'; -fx-text-fill: " + hex);
        textField.setOnAction(actionEvent -> {
            String element = "";
            String elementOriginal = "";
            String allow = elementsTextField[indexRow][14].getText().toLowerCase();
            if (indexColumn != 0) {
                element = elementsTextField[indexRow][0].getText();
                elementOriginal = elementsTextField[indexRow][0].getText();
            }
            else {
                element = textField.getText();
                elementOriginal = name;
            }
            int i;
            for (i = 1; i < indexColumn; i++){
                if ((i == 13 || i == 12 || i == 11 || i == 8)) {
                    element = element + "," + elementsComboBox[indexRow][i].getPromptText();
                    elementOriginal = elementOriginal + "," + elementsComboBox[indexRow][i].getPromptText();
                } else {
                    element = element + "," + elementsTextField[indexRow][i].getText();
                    elementOriginal = elementOriginal + "," + elementsTextField[indexRow][i].getText();
                }
            }
            if (indexColumn != 0) {
                element = element + "," + textField.getText();
                elementOriginal = elementOriginal + "," + name;
                i = i + 1 ;
            }
            for (; i < 15; i++){
                if ((i == 13 || i == 12 || i == 11 || i == 8)) {
                    element = element + "," + elementsComboBox[indexRow][i].getPromptText();
                    elementOriginal = elementOriginal + "," + elementsComboBox[indexRow][i].getPromptText();
                } else {
                    element = element + "," + elementsTextField[indexRow][i].getText();
                    elementOriginal = elementOriginal + "," + elementsTextField[indexRow][i].getText();
                }
            }
            String answer = null;
            try {
                if (allow.equals(user.getText().toLowerCase())) {
                    answer = request("remove " + elementOriginal);
                    answer = request("add " + element);
                    if (!answer.equals(LocaleManager.localizer("execution.success") + "\n")) {
                        request("add " + elementOriginal);
                        Window.error(LocaleManager.localizer("window.command") + " add " + LocaleManager.localizer("window.error"), LocaleManager.localizer("execution.incorrectEnter"));
                    }
                } else Window.warning(LocaleManager.localizer("window.allowError"),LocaleManager.localizer("window.allowError.message1") + " " + user.getText() + " " + LocaleManager.localizer("window.allowError.message2"));
                show();
                clickIdSorter();
            } catch (IOException e) {
                Window.error("Shit", "shit happens :(");
            }
        });
        return textField;
    }

    public void clickIdSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getId)).collect(Collectors.toList());
        sorter();
    }

    public void clickNameSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getName)).collect(Collectors.toList());
        sorter();
    }

    public void clickCoordinateXSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getCoordinateX)).collect(Collectors.toList());
        sorter();
    }

    public void clickCoordinateYSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getCoordinateY)).collect(Collectors.toList());
        sorter();
    }

    public void clickCreationDateSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getCreationDate)).collect(Collectors.toList());
        sorter();
    }

    public void clickNumberOfParticipantsSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getNumberOfParticipants)).collect(Collectors.toList());
        sorter();
    }

    public void clickAlbumsCountSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getAlbumsCount)).collect(Collectors.toList());
        sorter();
    }

    public void clickEstablishmentDateSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getEstablishmentDate)).collect(Collectors.toList());
        sorter();
    }

    public void clickGenreSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getGenre)).collect(Collectors.toList());
        sorter();
    }

    public void clickFrontmanNameSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getFrontManName)).collect(Collectors.toList());
        sorter();
    }

    public void clickFrontmanWeightSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getFrontManWeight)).collect(Collectors.toList());
        sorter();
    }

    public void clickFrontmanEyeColorSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getFrontManEyeColor)).collect(Collectors.toList());
        sorter();
    }

    public void clickFrontmanHairColorSorter(){
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getFrontManHairColor)).collect(Collectors.toList());
        sorter();
    }

    public void clickFrontmanNationalitySorter() {
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getFrontManNationality)).collect(Collectors.toList());
        sorter();
    }

    public void clickUserSorter() {
        collection = collection.stream().sorted(Comparator.comparing(MusicBand::getUser)).collect(Collectors.toList());
        sorter();
    }

    public void sorter(){
        for (int i = 0; i < elementsTextField.length; i++){
            mainTable.getChildren().removeAll(functionButtons[i][0]);
            mainTable.getChildren().removeAll(functionButtons[i][1]);
            for (int j = 0; j < 15; j++){
                if ((j == 13 || j == 12 || j == 11 || j == 8)){
                    mainTable.getChildren().removeAll(elementsComboBox[i][j]);
                } else mainTable.getChildren().removeAll(elementsTextField[i][j]);
            }
        }
        for (int row = 0; row < collection.size(); row++) {
            String[] collectionFields = collection.get(row).toString().split(",");
            visualiser(row, collectionFields);
        }
    }

    public void clickFilter(){
        try {
            if (!filter.getText().isEmpty()) {
                List<MusicBand> filteredCollection = collection.stream().filter((s) -> s.toString().contains(filter.getText())).collect(Collectors.toList());
                for (int i = 0; i < elementsTextField.length; i++){
                    mainTable.getChildren().removeAll(functionButtons[i][0]);
                    mainTable.getChildren().removeAll(functionButtons[i][1]);
                    for (int j = 0; j < 15; j++){
                        if ((j == 13 || j == 12 || j == 11 || j == 8)){
                            mainTable.getChildren().removeAll(elementsComboBox[i][j]);
                        } else mainTable.getChildren().removeAll(elementsTextField[i][j]);
                    }
                }
                for (int row = 0; row < filteredCollection.size(); row++) {
                    String[] collectionFields = filteredCollection.get(row).toString().split(",");
                    visualiser(row, collectionFields);
                }
            } else show();
        } catch (IOException e) {
            Window.error("Shit", "shit happens :(");
        }
    }

    public void clickLanguage() throws IOException {
        String[] locale = {"ru", "RU"};
        switch (language.getValue()) {
            case "Русский" -> locale = new String[]{"ru", "Ru"};
            case "Norsk" -> locale = new String[]{"no", "NO"};
            case "Català" -> locale = new String[]{"ca", "AD"};
            case "Español (Puerto Rico)" -> locale = new String[]{"es", "PR"};
        }
        LocaleManager.setLanguage(locale[0], locale[1]);
        request("language " + locale[0] + " " +  locale[1]);
        filter.setPromptText(LocaleManager.localizer("textField.filter"));
        commandsLabel.setText(LocaleManager.localizer("label.commands"));
        localDate.setText(setDate(locale));
    }

    public void setLocale(String[] locale) throws UnsupportedEncodingException {
        switch (locale[0]) {
            case "ru" -> language.setValue("Русский");
            case "no" -> language.setValue("Norsk");
            case "ca" -> language.setValue("Català");
            case "es" -> language.setValue("Español (Puerto Rico)");
        }
        filter.setPromptText(LocaleManager.localizer("textField.filter"));
        commandsLabel.setText(LocaleManager.localizer("label.commands"));
        localDate.setText(setDate(locale));
    }

    public String setDate(String[] locale){
        Locale local = new Locale(locale[0], locale[1]);
        DateFormat dateFormat = getDateInstance(LONG, local);
        return dateFormat.format(new Date());
    }

    public String request(String message) throws IOException {
        ClientInterface.write(message);
        return ClientInterface.read();
    }
}

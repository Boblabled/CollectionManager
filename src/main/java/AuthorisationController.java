import Window.Window;
import Manager.LocaleManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AuthorisationController {
    private boolean firstClickEnter = false;
    private boolean firstClickRegister = false;

    @FXML
    private ComboBox<String> language;

    @FXML
    private Button backButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button enterButton;

    @FXML
    private Button registerButton;

    public void clickEnter() throws IOException {
        if (!firstClickEnter) {
            backButton.setVisible(true);
            registerButton.setVisible(false);
            enterButton.setText(LocaleManager.localizer("button.enterButton.clicked"));
            for (int i = 216; i < 280; i++) {
                enterButton.setLayoutY(i);
            }
            loginField.setVisible(true);
            passwordField.setVisible(true);
            firstClickEnter = true;
        } else {
            getAllForLogIn();
        }
    }

    public void clickRegister() throws IOException {
        if (!firstClickRegister) {
            backButton.setVisible(true);
            registerButton.setText(LocaleManager.localizer("button.registerButton.clicked"));
            registerButton.setStyle("-fx-font-size: 18;  -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-background-color: #EC0B43; -fx-border-color: #EC0B43; -fx-border-width: 2; -fx-text-fill: #FFFFFF; -fx-font-family: 'SFNS Display';");
            enterButton.setVisible(false);
            loginField.setVisible(true);
            passwordField.setVisible(true);
            firstClickRegister = true;
        } else {
            getAllForRegistration();
        }
    }

    public void clickBack() throws UnsupportedEncodingException {
        firstClickEnter = false;
        firstClickRegister = false;
        enterButton.setText(LocaleManager.localizer("button.enterButton"));
        registerButton.setText(LocaleManager.localizer("button.registerButton"));
        registerButton.setStyle("-fx-font-size: 20;  -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-background-color: #EC0B43; -fx-border-color: #EC0B43; -fx-border-width: 2; -fx-text-fill: #FFFFFF; -fx-font-family: 'SFNS Display';");
        backButton.setVisible(false);
        loginField.setVisible(false);
        passwordField.setVisible(false);
        enterButton.setVisible(true);
        for (int i = 280; i > 216; i--) {
            enterButton.setLayoutY(i);
        }
        registerButton.setVisible(true);
    }

    public void getAllForRegistration() throws IOException {
        if (loginField.getCharacters().isEmpty()|| passwordField.getCharacters().isEmpty()){
            Window.warning(LocaleManager.localizer("server.write.authorisation2.failed"),LocaleManager.localizer("window.wrongEnter"));
        } else {
            String login = loginField.getCharacters().toString();
            String password = passwordField.getCharacters().toString();
            ClientInterface.write(login + " " + password + " 2");
            String messageFromServer = ClientInterface.read();
            if (messageFromServer.equals("\n" + LocaleManager.localizer("server.write.authorisation.success"))) {
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.close();
                mainScene(stage, login);
            }
            else {
                Window.warning(LocaleManager.localizer("server.write.authorisation2.failed"),LocaleManager.localizer("window.registrationFailed"));
            }
        }
    }

    public void getAllForLogIn() throws IOException {
        if (loginField.getCharacters().isEmpty()|| passwordField.getCharacters().isEmpty()){
            Window.warning(LocaleManager.localizer("server.write.authorisation2.failed"),LocaleManager.localizer("window.wrongEnter"));
        } else {
            String login = loginField.getCharacters().toString();
            String password = passwordField.getCharacters().toString();
            ClientInterface.write(login.toLowerCase() + " " + password + " 1");
            String messageFromServer = ClientInterface.read();
            if (messageFromServer.equals("\n" + LocaleManager.localizer("server.write.authorisation.success"))) {
                Stage stage = (Stage) enterButton.getScene().getWindow();
                stage.close();
                mainScene(stage, login);
            }
            else {
                Window.warning(LocaleManager.localizer("server.write.authorisation2.failed"),LocaleManager.localizer("window.enterFailed"));
            }
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
        ClientInterface.write("language " + locale[0] + " " + locale[1]);
        ClientInterface.read();
        if (registerButton.visibleProperty().get() && enterButton.visibleProperty().get()){
            registerButton.setText(LocaleManager.localizer("button.registerButton"));
            enterButton.setText(LocaleManager.localizer("button.enterButton"));
        } else {
            registerButton.setText(LocaleManager.localizer("button.registerButton.clicked"));
            enterButton.setText(LocaleManager.localizer("button.enterButton.clicked"));
        }
        loginField.setPromptText(LocaleManager.localizer("textField.login"));
        passwordField.setPromptText(LocaleManager.localizer("textField.password"));
        backButton.setText(LocaleManager.localizer("button.back"));
    }

    public void mainScene(Stage stage, String login) throws IOException{
        FXMLLoader fxmlLoader  = new FXMLLoader(getClass().getResource("/mainScene.fxml"));
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        MainSceneController mainSceneController = fxmlLoader.getController();
        stage.show();
        mainSceneController.start(login);
    }
}

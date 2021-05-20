import ForInterface.Window;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AuthorisationController {
    private boolean firstClickEnter = false;
    private boolean firstClickRegister = false;

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
            enterButton.setText("ВОЙТИ");
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
            registerButton.setText("ЗАРЕГИСТРИРОВАТЬСЯ");
            enterButton.setVisible(false);
            loginField.setVisible(true);
            passwordField.setVisible(true);
            firstClickRegister = true;
        } else {
            getAllForRegistration();
        }
    }

    public void clickBack() {
        firstClickEnter = false;
        firstClickRegister = false;
        enterButton.setText("ВХОД");
        registerButton.setText("РЕГИСТРАЦИЯ");
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
            Window.warning("Ошибка авторизации","Неправильный ввод данных");
        } else {
            String login = loginField.getCharacters().toString();
            String password = passwordField.getCharacters().toString();
            ClientInterface.write(login + " " + password + " 2");
            String messageFromServer = ClientInterface.read();
            if (messageFromServer.equals("\nВы успешно авторизировались")) {
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.close();
                mainScene(stage, login);
            }
            else {
                Window.warning("Ошибка авторизации","Неправильный логин или пароль");
            }
        }
    }

    public void getAllForLogIn() throws IOException {
        if (loginField.getCharacters().isEmpty()|| passwordField.getCharacters().isEmpty()){
            Window.warning("Ошибка авторизации","Неправильный ввод данных");
        } else {
            String login = loginField.getCharacters().toString();
            String password = passwordField.getCharacters().toString();
            ClientInterface.write(login.toLowerCase() + " " + password + " 1");
            String messageFromServer = ClientInterface.read();
            if (messageFromServer.equals("\nВы успешно авторизировались")) {
                Stage stage = (Stage) enterButton.getScene().getWindow();
                stage.close();
                mainScene(stage, login);
            }
            else {
                Window.warning("Ошибка авторизации","Такой пользователь уже существует");
            }
        }
    }

    public void mainScene(Stage stage, String login) throws IOException{
        FXMLLoader fxmlLoader  = new FXMLLoader(getClass().getResource("/mainScene.fxml"));
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        MainSceneController mainSceneController = (MainSceneController)(fxmlLoader.getController());
        stage.show();
        mainSceneController.start(login);
    }
}

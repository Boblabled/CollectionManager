import Window.Window;
import Manager.LocaleManager;
import Serialization.Serialization;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientInterface extends Application {
    private static Socket clientSocket; // сокет для общения
    private static BufferedReader reader; // ридер читающий с консоли
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static final int port = 4004; // порт для подключения
    private static final Serialization serialization = new Serialization(); // сериализптор/десериализатор
    private static final String temp = System.getenv().get("MusicBandPATH3"); // переменная окружения
    private static final String serializedDate = "serializedDate.txt"; // файл для передачи сериализованных сообщений

    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/media/ALSSchlangesans-Bold.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/media/ALSSchlangesans.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/media/SF.ttf"), 14);
        stage.setTitle("CollectionManager 3000 Pro");
        stage.getIcons().add(new Image("media/dinosaur.png"));
        authorisation(stage);
    }

    public void authorisation(Stage stage) throws UnsupportedEncodingException {
        try {
            connection(true);
            FXMLLoader fxmlLoader  = new FXMLLoader(getClass().getResource("/authorisationScene.fxml"));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            Window.error(LocaleManager.localizer("window.connectionError.head"),LocaleManager.localizer("window.connectionError.body"));
        }
    }

    /**
     * Модуль отправки запросов на сервер
     *
     * @param message - сообщение серверу
     * @return - введённая команда
     * @throws IOException - ошибка чтения
     */
    public static String write(String message) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        serialization.SerializeObject((Object) message, temp, serializedDate);
        out.write(message + "\n");
        out.flush();
        return message;
    }

    /**
     * Модуль принятия сообщений от сервера
     *
     * @return - сообщение от сервера
     * @throws IOException - ошибка принятия сообщений
     */
    public static String read() throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String serverWord = in.readLine();
        serverWord = serialization.DeserializeObject(temp, serializedDate);
        return serverWord;
    }

    /**
     * Модуль соединения с сервером
     *
     * @param connect - режим работы (отключиться/подключиться)
     * @throws IOException - ошибка подключения
     */
    public static void connection(boolean connect) throws IOException {
        if (connect) {
            clientSocket = new Socket("localhost", port);
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        if (!connect) {
            clientSocket.close();
            in.close();
            out.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

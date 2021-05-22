import ForInterface.Window;
import Serialization.Serialization;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

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

    public void authorisation(Stage stage){
        try {
            connection(true);
            FXMLLoader fxmlLoader  = new FXMLLoader(getClass().getResource("/authorisationScene.fxml"));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            Window.error("Ошибка подкоючения","Невозможно установить соединение с сервером");
        }
    }

    /*
    public void connection(Stage stage) throws IOException{
        FXMLLoader fxmlLoader  = new FXMLLoader(getClass().getResource("/connectionScene.fxml"));
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
     */

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
     * Модуль авторизации пользователя
     */
    public static void authorisation() {
        System.out.println("Для подключения к базе данных необходимо авторизоваться");
        while (true){
            try {
                System.out.println("1 - Вход || 2 - Регистрация");
                System.out.print("Введите команду: ");
                String message = reader.readLine();
                String password;
                String login;
                if (message.equals("1") || message.equals("Вход")){
                    System.out.print("Введите логин: ");
                    login = reader.readLine();
                    login = login.toLowerCase();
                    System.out.print("Введите пароль: ");
                    password = reader.readLine();
                    write(login + " " + password + " 1");
                    String messageFromServer = read();
                    System.out.println(messageFromServer + "\n");
                    if (messageFromServer.equals("\nВы успешно авторизировались")) break;
                } else if (message.equals("2") || message.equals("Регистрация")){
                    System.out.print("Введите логин: ");
                    login = reader.readLine();
                    login = login.toLowerCase();
                    System.out.print("Введите пароль: ");
                    password = reader.readLine();
                    write(login + " " + password + " 2");
                    String messageFromServer = read();
                    System.out.println(messageFromServer + "\n");
                    if (messageFromServer.equals("\nВы успешно авторизировались")) break;
                } else {
                    System.err.print("Неизвестная команда\n\n");
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                System.err.print("Ошибка авторизации\n");
            }
        }
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

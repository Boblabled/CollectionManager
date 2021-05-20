import Serialization.Serialization;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Клиент
 */
public class Client implements Serializable {

    private static Socket clientSocket; // сокет для общения
    private static BufferedReader reader; // ридер читающий с консоли
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static final int port = 4004; // порт для подключения
    private static final Serialization serialization = new Serialization(); // сериализптор/десериализатор
    private static final String temp = System.getenv().get("MusicBandPATH3"); // переменная окружения
    private static final String serializedDate = "serializedDate.txt"; // файл для передачи сериализованных сообщений

    /**
     * Это main)
     */
    public static void main(String[] args) {
        try {
            connection(true);
            authorisation();
            while (true){
                System.out.print("Введите команду: ");
                String message = write(commandPreparation());
                System.out.println(read());
                if (message.equals("exit") | message.equals("close server")){
                    connection(false);
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Невозможно установить соединение с сервером");
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
            System.out.println("Соединение с сервером установлено");
        }
        if (!connect) {
            System.out.println("Клиент был закрыт...");
            clientSocket.close();
            in.close();
            out.close();
        }
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
                    login = reader.readLine().toLowerCase();
                    System.out.print("Введите пароль: ");
                    password = reader.readLine();
                    write(login + " " + password + " 1");
                    String messageFromServer = read();
                    System.out.println(messageFromServer + "\n");
                    if (messageFromServer.equals("\nВы успешно авторизировались")) break;
                } else if (message.equals("2") || message.equals("Регистрация")){
                    System.out.print("Введите логин: ");
                    login = reader.readLine().toLowerCase();
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
     * Для красивого ввода команд
     *
     * @return - команда
     * @throws IOException - ошибка ввода
     */
    public static String commandPreparation() throws IOException{
        String result;
        String command1 = reader.readLine();
        if (command1.equals("add") || command1.equals("add_if_min") || command1.equals("remove_lower")) {
            System.out.print("Введите значение поля name: ");
            String command2 = reader.readLine();
            System.out.print("Введите значение поля coordinates_x: ");
            String command3 = reader.readLine();
            System.out.print("Введите значение поля coordinates_y: ");
            String command4 = reader.readLine();
            System.out.print("Введите значение поля numberOfParticipants: ");
            String command5 = reader.readLine();
            System.out.print("Введите значение поля albumsCount: ");
            String command6 = reader.readLine();
            System.out.print("Введите значение поля establishmentDate: ");
            String command7 = reader.readLine();
            System.out.print("Введите значение поля genre: ");
            String command8 = reader.readLine();
            System.out.print("Введите значение поля frontMan_name: ");
            String command9 = reader.readLine();
            System.out.print("Введите значение поля frontMan_weight: ");
            String command10 = reader.readLine();
            System.out.print("Введите значение поля frontMan_eyeColor: ");
            String command11 = reader.readLine();
            System.out.print("Введите значение поля frontMan_hairColor: ");
            String command12 = reader.readLine();
            System.out.print("Введите значение поля frontMan_nationality: ");
            String command13 = reader.readLine();
            result = command1 + " " + command2 + "," + command3 + "," +
                    command4 + "," + command5 + "," + command6 + "," +
                    command7 + "," + command8 + "," + command9 + "," +
                    command10 + "," + command11 + "," + command12 + "," +
                    command13;

        } else if (command1.equals("update_id") || command1.equals("remove_by_id")){
            System.out.print("Введите значение поля id: ");
            String command2 = reader.readLine();
            result = command1 + " " + command2;
        } else if (command1.equals("execute_script")){
            System.out.print("Введите название файла: ");
            String command2 = reader.readLine();
            result = command1 + " " + command2;
        } else if (command1.equals("count_by_albums_count") || command1.equals("count_greater_than_albums_count")){
            System.out.print("Введите значение поля albumsCount: ");
            String command2 = reader.readLine();
            result = command1 + " " + command2;
        } else result = command1;
        return result;
    }
}

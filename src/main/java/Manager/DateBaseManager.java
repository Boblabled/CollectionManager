package Manager;

import Elements.MusicBand;
import Manager.Manager;
import Manager.LocaleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.LinkedHashSet;

public class DateBaseManager {
    private static final Logger logger = LogManager.getLogger();
    private static String url = "jdbc:postgresql://localhost:5432/Collection";
    private static Statement statement;
    private static Connection connection;

    /**
     * Подключение к базе данных
     *
     * @param collection - коллекция
     */
    public static void connect(LinkedHashSet<MusicBand> collection) throws UnsupportedEncodingException {
        try {
            connection = DriverManager.getConnection(url, "postgres", "a123456789M");
            logger.info(LocaleManager.localizer("dataBase.connection.success"));
            statement = connection.createStatement();
            load(collection);
        } catch (Exception e) {
            logger.error(LocaleManager.localizer("dataBase.connection.failed"));
            logger.error(e);
            System.exit(0);
        }
    }

    /**
     * Выгружает коллекцию из БД в программу
     *
     * @param collection - коллекция
     */
    public static void load(LinkedHashSet<MusicBand> collection){
        try {
            Manager manager = new Manager();
            collection.clear();
            ResultSet rs = statement.executeQuery("SELECT * FROM MusicBand");
            while (rs.next()) {
                String element = rs.getString(1) + "," + rs.getString(2) + "," + rs.getFloat(3) + "," + rs.getFloat(4);
                for (int i = 5; i < 16; i++) {
                    element = element + "," + rs.getString(i);
                }
                manager.fill(element, collection);
            }
            rs.close();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * Отключение от базы данных
     */
    public static void disconnect(){
        try {
            statement.close();
            connection.close();
            logger.info(LocaleManager.localizer("dataBase.disconnection"));
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * Исполнение команд на sql
     *
     * @param command - команда
     * @return - результат работы
     */
    public static String executeCommand(String command) throws UnsupportedEncodingException {
        String answer = LocaleManager.localizer("execution.success") + "\n";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.execute();
        } catch (Exception e) {
            answer = e.getMessage() + "\n";
            logger.error(e.getMessage());
        }
        return answer;
    }

    /**
     * Добавление нового пользователя
     *
     * @param login - логин
     * @param password - пароль
     * @return - результат работы
     */
    public static boolean addUser(String login, String password) throws UnsupportedEncodingException {
        logger.info(LocaleManager.localizer("dataBase.addTry"));
        try {
            String query = "CREATE ROLE " + login + " WITH\n" +
                    "  LOGIN\n" +
                    "  SUPERUSER\n" +
                    "  INHERIT\n" +
                    "  NOCREATEDB\n" +
                    "  NOCREATEROLE\n" +
                    "  NOREPLICATION\n" +
                    "  ENCRYPTED PASSWORD '" + SHA_224(password) + "';\n" +
                    "GRANT users TO " + login + ";";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            logger.info(LocaleManager.localizer("dataBase.addSuccess1") + " " + login + " " + LocaleManager.localizer("dataBase.addSuccess2"));
            return true;
        } catch (SQLException e) {
            logger.error(LocaleManager.localizer("dataBase.addFailed1") + " " + login + " " + LocaleManager.localizer("dataBase.addFailed2"));
            return false;
        }
    }

    /**
     * Авторизация существующего пользователя
     *
     * @param login - логин
     * @param password - пароль
     * @return - результат работы
     */
    public static boolean login(String login, String password) throws UnsupportedEncodingException {
        try (Connection connection = DriverManager.getConnection(url, login, SHA_224(password))) {
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM musicband");
            logger.info(LocaleManager.localizer("dataBase.addSuccess1") + " " + login + " " + LocaleManager.localizer("dataBase.addSuccess3"));
            return true;
        } catch (SQLException e) {
            logger.error(LocaleManager.localizer("dataBase.addFailed3"));
            return false;
        }
    }

    /**
     * Хэширование пароля по алгоритму SHA-224
     *
     * @param password - пароль
     * @return - хэшированный пароль
     */
    public static String SHA_224(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

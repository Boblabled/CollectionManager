import Commands.CommandChangeLanguage;
import Commands.CommandExecution;
import Manager.DateBaseManager;
import Manager.LocaleManager;
import Serialization.Serialization;
import Elements.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Сервер
 */
public class Server extends RecursiveTask<String> {

    private static Socket clientSocket; // сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static String clientLogin = null; // имя пользователя
    private static final LinkedHashSet<MusicBand> collection = new LinkedHashSet<MusicBand>(); // коллекция
    private static final int port = 4004; // порт для подключения
    private static final Serialization serialization = new Serialization(); // сериализатор/десериализатор
    private static final LocalDateTime today = LocalDateTime.now(); //
    private static final String temp = System.getenv().get("MusicBandPATH3"); // переменная окружения
    private static final String serializedDate = "serializedDate.txt"; // файл для передачи сериализованных сообщений
    private static final Logger logger = LogManager.getLogger(); // логгер
    private static Boolean work;


    /**
     * Это main)
     */
    public static void main(String[] args) {
        try {
            ForkJoinPool commonPool = new ForkJoinPool(4);
            logger.info(LocaleManager.localizer("server.launched") + "!");
            DateBaseManager.connect(collection);
            while (true) {
                work = true;
                connection(true, "non");
                authorisation();
                while (work) {
                    String message = commonPool.invoke(new Server());
                    if (message != null) {
                        write(message);
                        TimeUnit.MILLISECONDS.sleep(100);
                        if (message.equals("exit")) {
                            connection(false, "non");
                            break;
                        } else if (message.equals("close server")) {
                            connection(false,"close server");
                            break;
                        }
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Модуль выполнения команд
     *
     * @param message - сообщение принятое от клиента
     * @param today - текущая дата
     */
    public static String execution(String message, LocalDateTime today) throws IOException, ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        Future<Object> outPut = pool.submit(() -> {
            String command;
            String[] field;
            field = message.split(" ");
            command = field[0];
            Object result;
            lock.writeLock().lock();
            try {
                result = CommandExecution.action(collection, message, command, today, clientLogin);
            } finally {
                lock.writeLock().unlock();
            }
            return result;
        });
        return (String) outPut.get();
    }

    /**
     * Модуль приёма подключений
     *
     * @param connect - режим работы (отключиться/подключиться)
     * @param close - звкрытие сервера
     * @throws IOException - ошибка подключения
     */
    public static void connection(boolean connect, String close) throws IOException {
        if (connect) {
            CommandChangeLanguage.action("Language ru RU");
            server = new ServerSocket(port);
            logger.info(LocaleManager.localizer("server.connection.waiting") + "...");
            clientSocket = server.accept();
            logger.info(LocaleManager.localizer("server.connection.clientConnected"));
        }
        if (!connect) {
            logger.info(LocaleManager.localizer("server.connection.clientDisconnected"));
            clientSocket.close();
            server.close();
            if (close.equals("close server")){
                DateBaseManager.disconnect();
                logger.info(LocaleManager.localizer("server.closed") + "!");
                System.exit(0);
            }
        }
    }

    /**
     * Модуль чтения запроса
     *
     * @return - возвращает десериализованную команду
     * @throws IOException - ошибка чтения запроса
     */
    public static String read() throws IOException {
        String message = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            message = in.readLine();
            message = serialization.DeserializeObject(temp, serializedDate);
        } catch (IOException e) {
            try {
                connection(false, "non");
            } catch (IOException ioException) {
                logger.error(ioException.getMessage());
            }
            work = false;
        }
        return message;
    }

    /**
     * Модуль отправки ответов клиенту
     *
     * @param message - сообщение от клиента
     * @throws IOException - ошибка чтения запроса
     */
    public static void write(String message) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        Runnable task = () -> {
            try {
                logger.info(LocaleManager.localizer("server.write.clientWrite1") + " " + clientLogin + " " + LocaleManager.localizer("server.write.clientWrite2") + ": " + message);
                String messageToClient = execution(message, today);
                serialization.SerializeObject(messageToClient, temp, serializedDate);
                out.write("\n");
                out.flush();
            } catch (ExecutionException | IOException | InterruptedException ignored) {
                try {
                    logger.error(LocaleManager.localizer("server.write.failed"));
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage());
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    /**
     * Модуль отправки ответов клиенту
     *
     * @param work - режим работы
     * @throws IOException - ошибка чтения запроса
     */
    public static void write(boolean work) throws IOException {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        Runnable task = () -> {
            try {
                String messageToClient = "";
                if (work) messageToClient = "\n" + LocaleManager.localizer("server.write.authorisation.success");
                else messageToClient = "\n" + LocaleManager.localizer("server.write.authorisation1.failed");
                serialization.SerializeObject(messageToClient, temp, serializedDate);
                out.write("\n");
                out.flush();
            } catch (IOException e) {
                try {
                    logger.error(LocaleManager.localizer("server.write.failed"));
                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    logger.error(unsupportedEncodingException.getMessage());
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    /**
     * Модуль авторизации
     */
    public static void authorisation() throws UnsupportedEncodingException {
        while (work) {
            try {
                String message = read();
                if (message != null) {
                    String[] fields = message.split(" ");
                    boolean work = false;
                    if (fields.length == 3){
                        clientLogin = fields[0].toLowerCase();
                        if (fields[2].equals("2")) work = DateBaseManager.addUser(clientLogin, fields[1]);
                        else if (fields[2].equals("1")) work = DateBaseManager.login(clientLogin, fields[1]);
                        else execution(message, today);
                    }
                    write(work);
                    if (work) break;
                }
            } catch (IOException | ExecutionException | InterruptedException e) {
                logger.error(LocaleManager.localizer("server.write.authorisation2.failed"));
            }
        }
    }

    /**
     * Многопоточное чтение зпросов
     *
     * @return - сообщение от пользователя
     */
    @Override
    protected String compute() {
        String message = null;
        try {
            message = read();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return message;
    }
}

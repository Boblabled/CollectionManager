package Commands;

import DateBase.DateBase;
import Elements.MusicBand;
import Manager.Manager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashSet;

/**
 * Класс команды которая обновляет id элемента
 */
public class CommandUpdateId extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который обновляет id элемента
     *
     * @param command - команда которую вводят с консоли
     * @param collection - коллекция
     */
    public static Object action(String command, LinkedHashSet<MusicBand> collection, String user){
        Object message = "";
        Manager manager = new Manager();
        long id = 0;
        String[] field;
        MusicBand[] arr;
        boolean work;
        work = false;
        int index;
        field = command.split(" ");
        arr = collection.toArray(new MusicBand[0]);
        if (field.length == 1){
            message ="id отсутствует";
            logger.error(message);
            message = message + "\n";
        } else try {
            id = Long.parseLong(field[1]);
            for (index = 0; index < collection.size(); index++) {
                if (id == arr[index].getId() && user.equals(arr[index].getUser())) {
                    String element = arr[index].toString();
                    collection.remove((arr[index]));
                    CommandSave.action(collection);
                    manager.add(element, collection);
                    message = "Команда выполнена";
                    logger.info(message);
                    message = message + "\n";
                    work = true;
                    break;
                }
            }
            if (!work) {
                message = "Такого элемента не существует или у вас нет к нему доступа";
                logger.error(message);
                message = message + "\n";
            }
        } catch (NumberFormatException e) {
            message = "id неверный формат строки!";
            logger.error(message);
            message = message + "\n";
        }
        return message;
    }
}

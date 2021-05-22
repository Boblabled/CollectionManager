package Commands;

import Elements.MusicBand;
import Manager.Manager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Класс команды который удаляет из коллекции все элементы, меньшие, чем заданный
 */
public class CommandRemoveLower extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который удаляет из коллекции все элементы, меньшие, чем заданный
     *
     * @param command - команда которую вводят с консоли
     * @param collection - коллекция
     */
    public static Object action(String command, LinkedHashSet<MusicBand> collection, String user){
        Manager manager = new Manager();
        Object message = "";
        String[] field;
        int size = collection.size();
        int index;
        String element;
        field = command.split(" ");
        if (field.length == 1){
            message = "Элемент отсутствует";
            logger.error(message);
            message = message + "\n";
        } else  if (field.length >= 2){
            element = field[1];
            if (field.length > 2){
                for (index = 2; index<field.length; index++) {
                    element = element + " " + field[index];
                }
            }
            if (element.split(",").length == 12) {
                String elementPrepared = element;
                collection.removeAll(collection.stream().filter((mb) -> mb.getUser().equals(user)).filter((mb) -> mb.hashCode() == (manager.set(elementPrepared, user).hashCode())).collect(Collectors.toSet()));
                if (size != collection.size()) message = "Команда выполнена";
                else message = "Не найденно элементов меньше заданного";
                logger.info(message);
                message = message + "\n";
            } else {
                message = "Неверный формат ввода данных";
                logger.error(message);
                message = message + "\n";
            }
        } else {
            message = "Неверный формат ввода данных";
            logger.error(message);
            message = message + "\n";
        }
        return message;
    }
}

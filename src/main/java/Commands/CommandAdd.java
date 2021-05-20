package Commands;
import DateBase.DateBase;
import Elements.MusicBand;
import Manager.Manager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

/**
 * Класс команды которая добавляет новый элемент в коллекцию
 */
public class CommandAdd extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который добавляет новый элемент в коллекцию
     *
     * @param command - строка котрую вводят с консоли
     * @param collection - коллекция
     */
    public static Object action(String command, LinkedHashSet<MusicBand> collection, String user){
        Object message = "";
        Manager manager = new Manager();
        String element = "";
        String[] field;
        int index;
        field = command.split(" ");
        if (field.length == 1){
            message = ("Элемент отсутствует\n");
            logger.error(message);
            message = message + "\n";
        } else  if (field.length >= 2){
                element = field[1];
                if (field.length > 2){
                    for (index = 2; index<field.length; index++) {
                        element = element + " " + field[index];
                    }
                }
                element = element + "," + user;
                message = manager.add(element, collection);
        } else {
            element = field[1];
            element = element + "," + user;
            message = manager.add(element, collection);
        }
        return message;
    }
}

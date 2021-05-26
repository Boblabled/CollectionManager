package Commands;

import Elements.MusicBand;
import Manager.Manager;
import Manager.LocaleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
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
    public static Object action(String command, LinkedHashSet<MusicBand> collection, String user) throws UnsupportedEncodingException {
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
            message ="id " + LocaleManager.localizer("execution.element.missingString2");
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
                    message = LocaleManager.localizer("execution.success");
                    logger.info(message);
                    message = message + "\n";
                    work = true;
                    break;
                }
            }
            if (!work) {
                message = LocaleManager.localizer("execution.allowDeniedOrNotExist");
                logger.error(message);
                message = message + "\n";
            }
        } catch (NumberFormatException e) {
            message = "id " + LocaleManager.localizer("execution.incorrectStringEnter");
            logger.error(message);
            message = message + "\n";
        }
        return message;
    }
}

package Commands;

import Elements.MusicBand;
import Manager.LocaleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Класс команды которая добавляет элемент в коллекци, если его значение меньше, чем у наименьшего элемента этой коллекции
 */
public class CommandAddIfMin extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который добавляет элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     *
     * @param command - строка котрую вводят с консоли
     * @param collection - коллекция
     */
    public static Object action(String command, LinkedHashSet<MusicBand> collection, String user) throws UnsupportedEncodingException {
        Object message = "";
        String[] field;
        String element;
        boolean work;
        work = false;
        int index;
        field = command.split(" ");
        if (field.length == 1){
            message = LocaleManager.localizer("execution.element.missing");
            logger.error(message);
            message = message + "\n";
        } else  if (field.length >= 2){
            element = field[1];
            if (field.length > 2){
                for (index = 2; index<field.length; index++) {
                    element = element + " " + field[index];
                }
            }
            if (element.hashCode() < collection.stream().min(MusicBand::compareTo).get().hashCode()) {
                CommandAdd.action(command, collection, user);
                work = true;
            }
            if (!work) {
                message = LocaleManager.localizer("execution.element.notLower");
                logger.info(message);
                message = message + "\n";
            } else {
                message = LocaleManager.localizer("execution.success");
                logger.info(message);
                message = message + "\n";
            }
        } else {
            message = LocaleManager.localizer("execution.incorrectEnter");
            logger.error(message);
            message = message + "\n";
        }
        return message;
    }
}

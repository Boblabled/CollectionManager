package Commands;

import Elements.MusicBand;
import Manager.LocaleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashSet;

/**
 * Класс команды которая выводит количество элементов, значение поля albumsCount которых равно заданному
 */
public class CommandCountByAlbumsCount extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который выводит количество элементов, значение поля albumsCount которых равно заданному
     *
     * @param command - строка котрую вводят с консоли
     * @param collection - коллекция
     */
    public static Object action(String command, LinkedHashSet<MusicBand> collection) throws UnsupportedEncodingException {
        Object message = "";
        String[] fields;
        fields = command.split(" ");
        if (fields.length == 2 ){
            try {
                long albumsCount = Long.parseLong(fields[1]);
                long count = collection.stream().filter((mb) -> mb.getAlbumsCount().equals(albumsCount)).count();
                if (count != 0) message = LocaleManager.localizer("albumsCount.execution.success") + " albumsCount: " + count + "\n";
                else message = LocaleManager.localizer("execution.element.missingString") + " albumsCount";
                logger.info(LocaleManager.localizer("execution.success"));
            } catch (NumberFormatException e) {
                message = "albumsCount " + LocaleManager.localizer("execution.incorrectStringEnter");
                logger.error(message);
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

package Commands;

import Elements.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashSet;

/**
 * Класс команды которая выводит количество элементов, значение поля albumsCount которых больше заданного
 */
public class CommandCountGreaterThanAlbumsCountAlbumsCount extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который выводит количество элементов, значение поля albumsCount которых больше заданного
     *
     * @param command - строка котрую вводят с консоли
     * @param collection - коллекция
     */
    public static Object action(String command, LinkedHashSet<MusicBand> collection){
        Object message = "";
        String[] fields;
        fields = command.split(" ");
        if (fields.length == 2 ) {
            try {
                long albumsCount = Long.parseLong(fields[1]);
                long count = collection.stream().filter((mb) -> mb.getAlbumsCount() > (albumsCount)).count();
                message = "Количество элементов больше такого albumsCount: " + count + "\n";
                logger.info("Команда выполнена");
            } catch (NumberFormatException e) {
                message = "albumsCount неверный формат строки!";
                logger.error(message);
                message = message + "\n";
            }
        }
        return message;
    }
}

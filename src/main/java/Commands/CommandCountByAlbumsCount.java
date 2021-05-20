package Commands;

import Elements.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public static Object action(String command, LinkedHashSet<MusicBand> collection){
        Object message = "";
        String[] fields;
        fields = command.split(" ");
        if (fields.length == 2 ){
            try {
                long albumsCount = Long.parseLong(fields[1]);
                long count = collection.stream().filter((mb) -> mb.getAlbumsCount().equals(albumsCount)).count();
                if (count != 0) message = "Количество элементов с таким albumsCount: " + count + "\n";
                else message = "Не найдены элементы с таким albumsCount\n";
                logger.info("Команда выполнена");
            } catch (NumberFormatException e) {
                message = "albumsCount неверный формат строки!";
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

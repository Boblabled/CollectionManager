package Commands;

import Elements.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Класс команды которая удаляет все элементы из коллекции
 */
public class CommandClear extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который удаляет все элементы из коллекции
     *
     * @param collection - коллекция
     */
    public static Object action(LinkedHashSet<MusicBand> collection, String user){
        Object message = "";
        collection.removeAll(collection.stream().filter((mb) -> mb.getUser().equals(user)).collect(Collectors.toList()));
        message = "Элементы принадлежащие пользователю " + user + " успешно удалены";
        logger.info(message);
        message = message + "\n";
        return message;
    }
}

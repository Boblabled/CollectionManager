package Commands;

import Elements.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;

/**
 * Класс команды которая выводит всю информацию о коллекции
 */
public class CommandInfo extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который выводит всю информацию о коллекции
     *
     * @param collection - коллекция
     * @param time - время создания коллекции
     */
    public static Object action(LinkedHashSet<MusicBand> collection, LocalDateTime time){
        Object message= ("Тип коллекции: java.util.LinkedHashSet" +
                "\nДата создания: " + time +
                "\nСтруктура элемента: {id, name, coordinates.x, coordinates.y, creationDate, " +
                "numberOfParticipants, albumsCount, establishmentDate, genre, frontMan.name, frontMan.weight, " +
                "frontMan.eyeColor, frontMan.hairColor, frontMan.nationality}" +
                "\nКолличество элементов: " + collection.size() + "\n");
        logger.info("Команда выполнена");
        return message;
    }
}

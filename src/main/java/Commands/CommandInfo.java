package Commands;

import Elements.MusicBand;
import Manager.LocaleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
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
    public static Object action(LinkedHashSet<MusicBand> collection, LocalDateTime time) throws UnsupportedEncodingException {
        Object message= (LocaleManager.localizer("commandInfo.collectionType") + ": java.util.LinkedHashSet" +
                "\n" + LocaleManager.localizer("commandInfo.creationDate") + ": " + time +
                "\n" + LocaleManager.localizer("commandInfo.structure") + ": {id, name, coordinates.x, coordinates.y, creationDate, " +
                "numberOfParticipants, albumsCount, establishmentDate, genre, frontMan.name, frontMan.weight, " +
                "frontMan.eyeColor, frontMan.hairColor, frontMan.nationality}" +
                "\n" + LocaleManager.localizer("commandInfo.size") + ": " + collection.size() + "\n");
        logger.info(LocaleManager.localizer("execution.success"));
        return message;
    }
}

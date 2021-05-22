package Commands;

import Elements.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Класс команды которая удаляет все элементы с указанным id
 */
public class CommandRemoveById extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который удаляет все элементы с указанным id
     *
     * @param command - команда которую вводят с консоли
     * @param collection - коллекция
     */
    public static Object action(String command, LinkedHashSet<MusicBand> collection, String user){
        Object message = "";
        String[] fields;
        fields = command.split(" ");
        int size = collection.size();
        if (fields.length == 2){
            try {
                collection.removeAll(collection.stream().filter((mb) -> mb.getUser().equals(user)).filter((mb) -> mb.getId() == (Long.parseLong(fields[1]))).collect(Collectors.toList()));
                if (size != collection.size()) message = "Команда выполнена";
                else message = "Не найдено элементов с таким id";
                logger.info(message);
                message = message + "\n";
            } catch (NumberFormatException e) {
                message = "id неверный формат строки!";
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

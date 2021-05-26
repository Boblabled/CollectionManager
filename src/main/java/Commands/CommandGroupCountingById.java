package Commands;

import Elements.MusicBand;
import Manager.LocaleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Класс команды которая группирует элементы по их id
 */
public class CommandGroupCountingById extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Команда которая группирует элементы по их id
     *
     * @param collection - коллекция
     */
    public static Object action(LinkedHashSet<MusicBand> collection) throws UnsupportedEncodingException {
        Object message = "";
        String[] fields;
        Object[] arr;
        int index;
        if (collection.size() >= 1) {
            String[] id = new String[collection.size()];
            int[] idCount = new int[collection.size()];
            arr = collection.toArray();
            int i = 0;
            for (index = 0; index < collection.size(); index++) {
                boolean work;
                work = false;
                fields = (arr[index].toString()).split(",");
                for (i = 0; i < id.length; i++) {
                    if (id[i] != null) if (id[i].equals(fields[0])) work = true;
                }
                if (!work) id[index] = fields[0];
            }

            for (index = 0; index < collection.size(); index++) {
                fields = (arr[index].toString()).split(",");
                for (i = 0; i < id.length; i++) {
                    if (id[i] != null) if (id[i].equals(fields[0])) idCount[i]++;
                }
            }

            index = 0;
            while (id[index] != null) {
                message = message + LocaleManager.localizer("id.execution.count") + " id = " + id[index] + ": " + idCount[index] + "\n";
                index++;
                if (index == collection.size()) break;
            }
            logger.info(LocaleManager.localizer("execution.success"));
        } else {
            message = LocaleManager.localizer("execution.collectionEmpty");
            logger.error(message);
            message = message + "\n";
        }
        return message;
    }
}

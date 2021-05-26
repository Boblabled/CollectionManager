package Commands;

import Elements.MusicBand;
import Manager.LocaleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashSet;

/**
 * Класс команды которая выводит все элементы коллекции
 */
public class CommandShow extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который выводит все элементы коллекции
     *
     * @param collection - коллекция
     */
    public static Object action(LinkedHashSet<MusicBand> collection) throws UnsupportedEncodingException {
        Object message= "";
        MusicBand[] arr;
        arr = collection.toArray(new MusicBand[0]);
        for (int i =0; i < collection.size(); i++){
            if (i == 0 && i == collection.size()-1){
                message = (arr[i].toString() + "\n");
            } else if (i == 0) {
                message = (arr[i].toString());
            } else if (i == collection.size()-1) {
                message = (message + "\n" +  arr[i].toString() + "\n");
            } else message = (message + "\n" + arr[i].toString());
        }
        logger.info(LocaleManager.localizer("execution.success"));
        if (message.equals("")) message = LocaleManager.localizer("execution.collectionEmpty") + "\n";
        return message;
    }
}

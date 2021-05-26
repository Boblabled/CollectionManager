package Commands;

import Manager.LocaleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;

/**
 * Класс который хранит и выводит 12 последних команд
 */
public class CommandHistory extends Command{
    private static final Logger logger = LogManager.getLogger();
    public static String[] storage = new String[12];
    public static int Index;

    /**
     *Метод который запускает вывод 12 последних команд
     */
    public static Object action() throws UnsupportedEncodingException {
        Object message = "";
        int i;
        message = LocaleManager.localizer("commandHistory.execution");
        for (i = 0; i<Index; i++){
            message = message + "\n" + storage[i];
        }
        message = message + "\n";
        logger.info(LocaleManager.localizer("execution.success"));
        return message;
    }

    /**
     * Метод который сохраняет команду
     *
     * @param command - коммандв которую вводят с консоли
     */
    public static void save(String command){
        if (Index < storage.length){
            storage[Index] = command;
            Index++;
        } else{
            int i;
            for (i = 0; i+1 < storage.length; i++){
                storage[i] = storage[i+1];
            }
            storage[11] = command;
        }
    }
}

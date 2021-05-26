package Commands;

import java.io.UnsupportedEncodingException;

/**
 * Класс откоторого унаследованны все команды
 */
abstract public class Command {
    /**
     * Метод который запускает выполнение команды
     * @return
     */
    public static Object action() throws UnsupportedEncodingException {
        return null;
    }
}

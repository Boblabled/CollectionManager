package Commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс команды которая выводит справку по всем командам
 */
public class CommandHelp extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который выводит справку по всем командам
     */
    public static Object action(){
        Object messsage = ("""
                help : вывести справку по доступным командам
                info : вывести в стандартный поток вывода информацию о коллекции
                show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении"
                add {element} : добавить новый элемент в коллекцию
                update_id id : обновить значение id у элемента коллекции
                remove_by_id id : удалить элемент из коллекции по его id
                clear : очистить коллекцию
                execute_script file_name : считать и исполнить скрипт из указанного файла
                exit : завершить программу (без сохранения в файл)
                add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
                remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный
                history : вывести последние 12 команд (без их аргументов)
                group_counting_by_id : сгруппировать элементы коллекции по значению поля id, вывести количество элементов в каждой группе
                count_by_albums_count albumsCount : вывести количество элементов, значение поля albumsCount которых равно заданному
                count_greater_than_albums_count albumsCount : вывести количество элементов, значение поля albumsCount которых больше заданного
                """);
        logger.info("Команда выполнена");
        return messsage;
    }
}

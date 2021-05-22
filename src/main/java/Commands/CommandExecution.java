package Commands;

import Elements.MusicBand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;

/**
 * Класс команды которая исполняет все команды
 */
public class CommandExecution extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Команда которая исполняет все команды
     *
     * @param collection - коллекция
     * @param line - строка котрую вводят с консоли
     * @param command - комманда котрую вводят с консоли
     * @param time - текущее время
     * @return - сообщение клиенту
     */
    public static Object action(LinkedHashSet<MusicBand> collection, String line, String command, LocalDateTime time, String user){
        Object message = "";
        if (line.equals("help")){
            message = CommandHelp.action();
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if (line.equals("info")){
            message = CommandInfo.action(collection, time);
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if (line.equals("close server")){
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if (line.equals("exit")){
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if (line.equals("show")){
            message = CommandShow.action(collection);
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if (command.equals("add")){
            message = CommandAdd.action(line, collection, user);
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if ((command).equals("update_id")){
            message = CommandUpdateId.action(line, collection, user);
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if ((command).equals("remove_by_id")){
            message = CommandRemoveById.action(line, collection, user);
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if ((command).equals("add_if_min")){
            message = CommandAddIfMin.action(line, collection, user);
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if ((command).equals("remove_lower")){
            message = CommandRemoveLower.action(line, collection, user);
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if ((line).equals("group_counting_by_id")){
            message = CommandGroupCountingById.action(collection);
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if ((command).equals("count_by_albums_count")){
            message = CommandCountByAlbumsCount.action(line, collection);
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if ((command).equals("count_greater_than_albums_count")){
            message = CommandCountGreaterThanAlbumsCountAlbumsCount.action(line, collection);
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if ((line).equals("clear")){
            message = CommandClear.action(collection, user);
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if ((line).equals("history")){
            message = CommandHistory.action();
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else if ((command).equals("execute_script")){
            message = CommandExecuteScript.action(collection, line, command, time, user);
            CommandSave.action(collection);
            CommandHistory.save(command);
        } else {
            message= ("""
                    Неизвестная команда
                    help : вывести справку по доступным командам
                    """);
            logger.error("Неизвестная команда");
        }
        return message;
    }
}

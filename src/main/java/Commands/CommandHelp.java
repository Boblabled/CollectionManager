package Commands;

import Manager.LocaleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;

/**
 * Класс команды которая выводит справку по всем командам
 */
public class CommandHelp extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который выводит справку по всем командам
     */
    public static Object action() throws UnsupportedEncodingException {
        Object message = ("help : " + LocaleManager.localizer("commandHelp.help") +
                    "\ninfo : " + LocaleManager.localizer("commandHelp.info") +
                    "\nshow : " + LocaleManager.localizer("commandHelp.show") +
                    "\nadd " + LocaleManager.localizer("commandHelp.add") +
                    "\nupdate_id id : " + LocaleManager.localizer("commandHelp.update_id") +
                    "\nremove_by_id id : " + LocaleManager.localizer("commandHelp.remove_by_id") +
                    "\nclear : " + LocaleManager.localizer("commandHelp.clear") +
                    "\nexecute_script file_name : " + LocaleManager.localizer("commandHelp.execute_script") +
                    "\nexit : " + LocaleManager.localizer("commandHelp.exit") +
                    "\nadd_if_min {element} : " + LocaleManager.localizer("commandHelp.add_if_min") +
                    "\nremove_lower {element} : " + LocaleManager.localizer("commandHelp.remove_lower") +
                    "\nhistory : " + LocaleManager.localizer("commandHelp.history") +
                    "\ngroup_counting_by_id : " + LocaleManager.localizer("commandHelp.group_counting_by_id") +
                    "\ncount_by_albums_count albumsCount : " + LocaleManager.localizer("commandHelp.count_by_albums_count") +
                    "\ncount_greater_than_albums_count albumsCount : " + LocaleManager.localizer("commandHelp.count_greater_than_albums_count"));
        logger.info(LocaleManager.localizer("execution.success"));
        return message;
    }
}

package Commands;

import Manager.LocaleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class CommandChangeLanguage extends Command{
    private static final Logger logger = LogManager.getLogger();

    public static Object action(String line) throws UnsupportedEncodingException {
        Object message;
        String[] locale = line.split(" ");
        if (locale.length == 3) {
            LocaleManager.setLanguage(locale[1], locale[2]);
            message = LocaleManager.localizer("commandChangeLanguage");
            logger.info(message);
        } else {
            message = LocaleManager.localizer("execution.incorrectEnter");
            logger.error("execution.incorrectEnter");
        }
        message = message + "\n";
        return message;
    }
}

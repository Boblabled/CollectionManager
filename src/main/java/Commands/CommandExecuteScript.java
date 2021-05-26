package Commands;

import Elements.MusicBand;
import Manager.LocaleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;

/**
 * Класс команды которая считывает команды из файла и выполняет их
 */
public class CommandExecuteScript extends Command{
    private static final Logger logger = LogManager.getLogger();

    /**
     * Метод который считывает команды из файла и выполняет их
     *
     * @param collection - коллекция
     * @param line - строка котрую вводят с консоли
     * @param command - комманда котрую вводят с консоли
     * @param time - текущее время
     */
    public static Object action(LinkedHashSet<MusicBand> collection, String line, String command, LocalDateTime time, String user) throws UnsupportedEncodingException {
        //execute_script commands.txt
        Object message = "";
        String temp = System.getenv().get("MusicBandPATH3");
        String[] fields;
        fields = line.split(" ");
        int index;
        String InPut;

        if (fields.length >= 2) {
            InPut = temp + fields[1];
            if (fields.length >= 3){
                for (index = 2; index < fields.length; index++) {
                    InPut = InPut + " " + fields[index];
                }
            }
            File way = new File(InPut);
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(way);
            } catch (FileNotFoundException e) {
                message = LocaleManager.localizer("commandExecuteScript.file.notFound");
                logger.error(message);
                message = message + "\n";
            }

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String newLine = null;
            while (true) {
                try {
                    newLine = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (newLine == null) break;
                String[] newCommand;
                newCommand = newLine.split(" ");
                logger.info(newCommand[0]);
                Object currentMessage = CommandExecution.action(collection, newLine, newCommand[0], time, user);
                message = message + currentMessage.toString();
            }
        } else {
            message = LocaleManager.localizer("execution.incorrectEnter");
            logger.error(message);
            message = message + "\n";
        }
        return message;
    }
}

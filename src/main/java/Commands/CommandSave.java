package Commands;

import DateBase.DateBase;
import Elements.MusicBand;

import java.util.LinkedHashSet;

/**
 * Класс команды который сохраняет коллекцию
 */
public class CommandSave extends Command{

    /**
     * Метод который сохраняет коллекцию
     *
     * @param collection - коллекция
     */
    public static void action(LinkedHashSet<MusicBand> collection){
        Object[] elements = collection.toArray();
        DateBase.executeCommand("TRUNCATE TABLE musicband");
        for (Object element : elements) {
            String[] fields = element.toString().split(",");
            String command = ("INSERT INTO MusicBand (id, name, coordinate_X, coordinate_Y, creationDate, numberOfParticipants, " +
                    "albumsCount, establishmentDate, genre, frontMan_Name, frontMan_Weight, frontMan_EyeColor, frontMan_HairColor, " +
                    "frontMan_Nationality, \"user\") VALUES (" + fields[0] + ", '" + fields[1] + "', " + fields[2] + ", " + fields[3] + ", '" +
                    fields[4] + "', " + fields[5] + ", " + fields[6] + ", '" + fields[7] + "', '" + fields[8] + "', '" + fields[9] +
                    "', " + fields[10] + ", '" + fields[11] + "', '" + fields[12] + "', '" + fields[13] + "', '" + fields[14] + "')");
            DateBase.executeCommand(command);
        }
    }
}

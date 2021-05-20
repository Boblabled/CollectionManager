package Manager;

import Elements.MusicBand;
import DateBase.DateBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * Класс который работает с коллекцией
 */
public class Manager {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Заполнение коллекции при первом запуске
     *
     * @param element - элемент из БД
     * @param collection - коллекция
     */
    public void fill(String element, LinkedHashSet<MusicBand> collection) {
        String[] fields;
        int index;
        MusicBand musicband = new MusicBand();
        fields = element.split(",");

        for (index = 0; index<fields.length; index++){
            if (fields[index].equals("null")){
                    fields[index] = null;
            }
        }
        musicband.setId(fields[0]);
        musicband.setName(fields[1]);
        musicband.setCoordinates(fields[2], fields[3]);
        musicband.setCreationDate(fields[4]);
        musicband.setNumberOfParticipants(fields[5]);
        musicband.setAlbumsCount(fields[6]);
        musicband.setEstablishmentDate(fields[7]);
        musicband.setGenre(fields[8]);
        musicband.setFrontMan(fields[9], fields[10], fields[11], fields[12], fields[13]);
        musicband.setUser(fields[14]);
        collection.add(musicband);
    }

    /**
     * Добавляет новый файл в коллекцию
     *
     * @param element - элемент который вводят с консоли
     * @param collection - коллекция
     */
    public Object add (String element, LinkedHashSet<MusicBand> collection){
        Object message = "";
        LocalDateTime today = LocalDateTime.now();
        String[] fields;
        int index;
        fields = element.split(",");
        if (fields.length == 13) {
            for (index = 0; index < fields.length; index++) {
                if (fields[index].equals("null")) {
                    fields[index] = null;
                }
            }
            String command = ("INSERT INTO MusicBand (id, name, coordinate_X, coordinate_Y, creationDate, numberOfParticipants, " +
                    "albumsCount, establishmentDate, genre, frontMan_Name, frontMan_Weight, frontMan_EyeColor, frontMan_HairColor, " +
                    "frontMan_Nationality, \"user\") VALUES (nextval('randomid'), '" + fields[0] + "', " + fields[1] + ", " + fields[2] + ", '" +
                    today.toString() + "', " + fields[3] + ", " + fields[4] + ", '" + fields[5] + "', '" + fields[6] + "', '" + fields[7] +
                    "', " + fields[8] + ", '" + fields[9] + "', '" + fields[10] + "', '" + fields[11] + "', '"+ fields[12] + "')");
            message = DateBase.executeCommand(command);
            DateBase.load(collection);
        } else if (fields.length == 15) {
            for (index = 0; index < fields.length; index++) {
                if (fields[index].equals("null")) {
                    fields[index] = null;
                }
            }
            String command = ("INSERT INTO MusicBand (id, name, coordinate_X, coordinate_Y, creationDate, numberOfParticipants, " +
                    "albumsCount, establishmentDate, genre, frontMan_Name, frontMan_Weight, frontMan_EyeColor, frontMan_HairColor, " +
                    "frontMan_Nationality, \"user\") VALUES (nextval('randomid'), '" + fields[1] + "', " + fields[2] + ", " + fields[3] + ", '" +
                    fields[4] + "', " + fields[5] + ", " + fields[6] + ", '" + fields[7] + "', '" + fields[8] + "', '" + fields[9] +
                    "', " + fields[10] + ", '" + fields[11] + "', '" + fields[12] + "', '" + fields[13]  + "', '" + fields[14] + "')");
            message = DateBase.executeCommand(command);
            DateBase.load(collection);
        } else {
            message = "Неверный формат элемента\n";
            logger.error(message);
        }
        return message;
    }

    /**
     * Устанавливает id и CreationDate элементу коллекции
     *
     * @param element - неполный элемент коллекции
     * @return - полный элемент коллекции
     */
    public MusicBand set(String element, String user){
        MusicBand musicband = new MusicBand();
        String[] fields;
        fields = element.split(",");
        LocalDateTime today = LocalDateTime.now();
        String id = String.valueOf(Math.round(Math.random() * 1000000 + 1));
        musicband.setId(id);
        musicband.setName(fields[0]);
        musicband.setCoordinates(fields[1], fields[2]);
        musicband.setCreationDate(today.toString());
        musicband.setNumberOfParticipants(fields[3]);
        musicband.setAlbumsCount(fields[4]);
        musicband.setEstablishmentDate(fields[5]);
        musicband.setGenre(fields[6]);
        musicband.setFrontMan(fields[7], fields[8], fields[9], fields[10], fields[11]);
        musicband.setUser(user);
        return (musicband);
    }
}

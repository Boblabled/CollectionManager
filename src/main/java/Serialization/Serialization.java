package Serialization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;

/**
 * Класс, проводящий сериализацию и десериализацию объектов
 */
public class Serialization {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Сериализует объект
     *
     * @param <T> тип объекта
     * @return массив байтов
     */
    public <T> byte[] SerializeObject(T input, String temp, String file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(temp + file))) {
            T object = input;
            oos.writeObject(object);
        } catch (IOException e) {
            logger.error("Ошибка сериализации");
        }
        return null;
    }

    /**
     * Десериализует объект
     *
     * @param <T> тип объекта
     * @return объект
     */
    public <T> T DeserializeObject(String path, String filename) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path + filename)))
        {
            return (T)ois.readObject();
        }
        catch(Exception ex){
            logger.error("Ошибка десериализации");
        }
        return null;
    }
}

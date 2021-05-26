package Elements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * Класс элементов коллекции
 */
public class MusicBand implements Comparable<MusicBand>{
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates = new Coordinates(); //Поле не может быть null
    private java.time.LocalDateTime creationDate = LocalDateTime.now(); //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long numberOfParticipants; //Значение поля должно быть больше 0
    private Long albumsCount; //Поле может быть null, Значение поля должно быть больше 0
    private java.util.Date establishmentDate = new Date(); //Поле не может быть null
    private MusicGenre genre; //Поле может быть null
    private Person frontMan = new Person(); //Поле не может быть null
    private String user = "superuser";
    private static final Logger logger = LogManager.getLogger();

    /**
     * Устанавливает новое значение переменной id
     *
     * @param id - значении перемнной id
     */
    public void setId(String id) {
        try {
            this.id = Long.parseLong(id);
        } catch (NumberFormatException e) {
            logger.error("id неверный формат строки!");
            System.exit(0);
        }
        if (this.id <= 0) {
            throw new IllegalStateException("id должно быть больше 0");
        }
    }

    /**
     * Устанавливает новое значение переменной name
     *
     * @param name - значении перемнной name
     */
    public void setName(String name) {
        if (name == null){
            logger.error("name не может быть null");
            System.exit(0);
        } else if (name.equals("")){
            logger.error("name не может быть пустой строкой");
            System.exit(0);
        } else this.name = name;
    }

    /**
     * Устанавливает новое значение переменной coordinates
     *
     * @param x - значении перемнной x
     * @param y - значении перемнной y
     */
    public void setCoordinates(String x, String y) {
        this.coordinates.setX(x);
        this.coordinates.setY(y);
    }

    /**
     * Устанавливает новое значение переменной creationDate
     *
     * @param creationDate - значении перемнной creationDate
     */
    public void setCreationDate(String creationDate) {
        try {
            if (creationDate == null){
                logger.error("LocalDateTime не может быть null");
                System.exit(0);
            }else this.creationDate = LocalDateTime.parse(creationDate);
        } catch (NumberFormatException e) {
            logger.error("creationDate неверный формат строки!");
            System.exit(0);
        }
    }

    /**
     * Устанавливает новое значение переменной numberOfParticipants
     *
     * @param numberOfParticipants - значении перемнной numberOfParticipants
     */
    public void setNumberOfParticipants(String numberOfParticipants) {
        try {
            if (Long.parseLong(numberOfParticipants) <= 0){
                logger.error("numberOfParticipants не может меньше или равно 0");
                System.exit(0);
            } else this.numberOfParticipants = Long.parseLong(numberOfParticipants);
        } catch (NumberFormatException e) {
            logger.error("numberOfParticipants неверный формат строки!");
            System.exit(0);
        }
    }

    /**
     * Устанавливает новое значение переменной albumsCount
     *
     * @param albumsCount - значении перемнной albumsCount
     */
    public void setAlbumsCount(String albumsCount) {
        try {
            if (albumsCount == null){
                logger.error("albumsCount не может быть null");
                System.exit(0);
            } else this.albumsCount = Long.parseLong(albumsCount);
        } catch (NumberFormatException e) {
            logger.error("albumsCount неверный формат строки!");
            System.exit(0);
        }
    }

    /**
     * Устанавливает новое значение переменной establishmentDate
     *
     * @param establishmentDate - значении перемнной establishmentDate
     */
    public void setEstablishmentDate(String establishmentDate) {
        try {
            if (establishmentDate == null){
                logger.error("establishmentDate не может быть null");
                System.exit(0);
            } else {
                DateFormat pr = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                this.establishmentDate = pr.parse(establishmentDate);
            }
        } catch (NumberFormatException | ParseException e) {
            logger.error("establishmentDate неверный формат строки!");
            this.establishmentDate = new Date();
        }
    }

    /**
     * Устанавливает новое значение переменной genre
     *
     * @param genre - значении перемнной genre
     */
    public void setGenre(String genre) {
        try {
            if (genre == null){
                logger.error("genre не может быть null");
                System.exit(0);
            } else if (genre.equals(MusicGenre.JAZZ.toString())) {
                this.genre = MusicGenre.JAZZ;
            } else if (genre.equals(MusicGenre.SOUL.toString())) {
                this.genre = MusicGenre.SOUL;
            } else if (genre.equals(MusicGenre.POST_PUNK.toString())) {
                this.genre = MusicGenre.POST_PUNK;
            } else {
                logger.error("genre неверный формат строки!");
                System.exit(0);
            }
        } catch (NumberFormatException e) {
            logger.error("genre неверный формат строки!");
            System.exit(0);
        }
    }

    /**
     * Устанавливает новое значение переменной frontMan
     *
     * @param name - значении перемнной name
     * @param weight - значении перемнной weight
     * @param eyeColor - значении перемнной eyeColor
     * @param hairColor - значении перемнной hairColor
     * @param nationality - значении перемнной nationality
     */
    public void setFrontMan(String name, String weight, String eyeColor, String hairColor, String nationality) {
     frontMan.setName(name);
     frontMan.setWeight(weight);
     frontMan.setEyeColor(eyeColor);
     frontMan.setHairColor(hairColor);
     frontMan.setNationality(nationality);
     if (frontMan.getName() == null | frontMan.getWeight() == null | frontMan.getEyeColor() == null | frontMan.getHairColor() == null | frontMan.getNationality() == null){
         frontMan = null;
         System.err.println("frontMan не может быть null");
         System.exit(0);
     }
    }

    /**
     * Устанавливает новое значение пользователя
     *
     * @param user - пользователь
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Геттер колличества альбомов
     *
     * @return - альбомы
     */
    public Long getAlbumsCount() {
        return albumsCount;
    }

    /**
     * Геттер id
     *
     * @return - id)
     */
    public long getId() {
        return id;
    }

    /**
     * Геттер пользователя
     *
     * @return - пользователь
     */
    public String getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public Float getCoordinateX() {
        return coordinates.getX();
    }

    public double getCoordinateY() {
        return coordinates.getY();
    }

    public Date getEstablishmentDate() {
        return establishmentDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public String getFrontManName() {
        return frontMan.getName();
    }

    public Color getFrontManEyeColor() {
        return frontMan.getEyeColor();
    }

    public Color getFrontManHairColor() {
        return frontMan.getHairColor();
    }

    public Country getFrontManNationality() {
        return frontMan.getNationality();
    }

    public Integer getFrontManWeight() {
        return frontMan.getWeight();
    }

    /**
     * Почти toString() но лучше
     *
     * @return - элемент коллекции
     */
    public String show(){
        return (id + "," + name + "," + coordinates + "," + creationDate + "," +
                numberOfParticipants + "," + albumsCount + "," + establishmentDate + "," +
                genre + "," + frontMan + " — " + user);
    }

    /**
     * Ну это toString()
     *
     * @return - элемент коллекции
     */
    @Override
    public String toString() {
        return (id + "," + name + "," + coordinates + "," + creationDate + "," +
                numberOfParticipants + "," + albumsCount + "," + establishmentDate + "," +
                genre + "," + frontMan + "," + user);
    }

    /**
     * Сравниватель по hashCode
     *
     * @param o - элемент коллекции
     * @return - элемент коллекции
     */
    @Override
    public int compareTo(MusicBand o) {
        return o.hashCode();
    }
}
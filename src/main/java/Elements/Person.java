package Elements;

import Elements.Color;
import Elements.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс Person который храниться в коллекции MusicBand
 */
public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Integer weight; //Поле не может быть null, Значение поля должно быть больше 0
    private Color eyeColor; //Поле может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле не может быть null
    private static final Logger logger = LogManager.getLogger();

    /**
     * Устанавливает значение переменной name
     *
     * @param name - значение перемнной name
     */
    public void setName(String name) {
        if (name == null){
            logger.error("frontMan.name не может быть null");
            System.exit(0);
        } else if (name.equals("")){
            logger.error("frontMan.name не может быть пустой строкой");
            System.exit(0);
        } else this.name = name;
    }

    /**
     * Устанавливает значение переменной weight
     *
     * @param weight - значение перемнной weight
     */
    public void setWeight(String weight) {
        try {
            if (weight == null) {
                logger.error("frontMan.weight не может быть null");
                System.exit(0);
            } else if (Integer.parseInt(weight) <= 0){
                logger.error("frontMan.weight должно быть больше 0");
                System.exit(0);
            } else this.weight = Integer.parseInt(weight);
        } catch (NumberFormatException e) {
            logger.error("frontMan.weight неверный формат строки!");
            System.exit(0);
        }
    }

    /**
     * Устанавливает значение переменной eyeColor
     *
     * @param eyeColor - значение перемнной eyeColor
     */
    public void setEyeColor(String eyeColor) {
        try {
            if (eyeColor == null) {
                logger.error("frontMan.eyeColor не может быть null");
                System.exit(0);
            } else if (eyeColor.equals(Color.BLACK.toString())) {
                this.eyeColor = Color.BLACK;
            } else if (eyeColor.equals(Color.YELLOW.toString())) {
                this.eyeColor = Color.YELLOW;
            } else if (eyeColor.equals(Color.ORANGE.toString())) {
                this.eyeColor = Color.ORANGE;
            } else if (eyeColor.equals(Color.BROWN.toString())) {
                this.eyeColor = Color.BROWN;
            } else {
                logger.error("frontMan.eyeColor неверный формат строки!");
                System.exit(0);
            }
        } catch (NumberFormatException e) {
            logger.error("frontMan.eyeColor неверный формат строки!");
            System.exit(0);
        }
    }

    /**
     * Устанавливает значение переменной hairColor
     *
     * @param hairColor - значение перемнной hairColor
     */
    public void setHairColor(String hairColor) {
        try {
            if (hairColor == null) {
                logger.error("frontMan.hairColor не может быть null");
                System.exit(0);
            } else if (hairColor.equals(Color.BLACK.toString())) {
                this.hairColor = Color.BLACK;
            } else if (hairColor.equals(Color.YELLOW.toString())) {
                this.hairColor = Color.YELLOW;
            } else if (hairColor.equals(Color.ORANGE.toString())) {
                this.hairColor = Color.ORANGE;
            } else if (hairColor.equals(Color.BROWN.toString())) {
                this.hairColor = Color.BROWN;
            } else {
                logger.error("frontMan.hairColor неверный формат строки!");
                System.exit(0);
            }

        } catch (NumberFormatException e) {
            logger.error("frontMan.hairColor неверный формат строки!");
            System.exit(0);
        }
    }

    /**
     * Устанавливает значение переменной nationality
     *
     * @param nationality - значение перемнной nationality
     */
    public void setNationality(String nationality) {
        try {
            if (nationality == null) {
                logger.error("frontMan.nationality не может быть null");
                System.exit(0);
            } else if (nationality.equals(Country.UNITED_KINGDOM.toString())) {
                this.nationality = Country.UNITED_KINGDOM;
            } else if (nationality.equals(Country.USA.toString())) {
                this.nationality = Country.USA;
            } else if (nationality.equals(Country.ITALY.toString())) {
                this.nationality = Country.ITALY;
            } else if (nationality.equals(Country.JAPAN.toString())) {
                this.nationality = Country.JAPAN;
            } else {
                logger.error("frontMan.nationality неверный формат строки!");
                System.exit(0);
            }
        } catch (NumberFormatException e) {
            logger.error("frontMan.nationality неверный формат строки!");
            System.exit(0);
        }
    }

    @Override
    public String toString(){
        String result;
        result = this.name + "," + this.weight + "," + this.eyeColor + "," + this.hairColor + "," + this.nationality;
        return (result);
    }

    /**
     * Возвращает переменную name
     *
     * @return переменная name
     */
    public String getName() {
        return (this.name);
    }

    /**
     * Возвращает переменную weight
     *
     * @return переменная weight
     */
    public Integer getWeight() {
        return (this.weight);
    }

    /**
     * Возвращает переменную eyeColor
     *
     * @return переменная eyeColor
     */
    public Color getEyeColor() {
        return (this.eyeColor);
    }

    /**
     * Возвращает переменную hairColor
     *
     * @return переменная hairColor
     */
    public Color getHairColor() {
        return (this.hairColor);
    }

    /**
     * Возвращает переменную nationality
     *
     * @return переменная nationality
     */
    public Country getNationality() {
        return (this.nationality);
    }
}
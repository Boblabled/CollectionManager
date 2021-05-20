package Elements;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс координат MusicBand
 */
public class Coordinates {
    private static final Logger logger = LogManager.getLogger();
    private Float x; //Максимальное значение поля: 765, Поле не может быть null
    private double y; //Максимальное значение поля: 540

    /**
     * Метод который установливает значение переменной x
     *
     * @param x - значение координаты x
     */
    public void setX(String x){
        try {
            if (x == null) {
                logger.error("coordinates.x не может быть null");
                System.exit(0);
            } else if (Float.parseFloat(x) > 765) {
                logger.error("coordinates.x не может быть больше 765");
                System.exit(0);
            } else this.x = Float.parseFloat(x);
        } catch (NumberFormatException e) {
            logger.error("coordinates.x неверный формат строки!");
            System.exit(0);
        }
    }

    /**
     * Метод который установливает значение переменной y
     *
     * @param y - значение координаты y
     */
    public void setY(String y){
        try {
            if (y == null) {
                logger.error("coordinates.y не может быть null");
                System.exit(0);
            } else if (Double.parseDouble(y) > 540) {
                logger.error("coordinates.y не может быть больше 540");
                System.exit(0);
            } else this.y = Double.parseDouble(y);
        } catch (NumberFormatException e) {
            logger.error("coordinates.y неверный формат строки!");
            System.exit(0);
        }
    }

    @Override
    public String toString(){
        String result;
        result = x + ","+ y;
        return (result);
    }
}

package Window;

import javafx.scene.control.Alert;

public class Window {
    public static void error(String name, String message){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.ERROR);
        alert.setTitle(name);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    public static void warning(String name, String message){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle(name);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void info(String name, String message){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(name);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}

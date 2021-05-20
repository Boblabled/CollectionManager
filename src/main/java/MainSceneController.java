import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.logging.log4j.core.jmx.Server;

public class MainSceneController {
    public Button help;

    public Label user;

    public void start(String login){
        user.setText(login);
    }

    public void clickHelp(){
       start("Жопа");
    }
}

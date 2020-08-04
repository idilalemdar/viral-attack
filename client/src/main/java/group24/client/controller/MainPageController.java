package group24.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/***
 * MainPageControllerClass is responsible for
 * showing the user the main page screen
 * with options of starting the game or navigating to the scoreboard
 */

@Component
public class MainPageController extends Controller {

    @Value("classpath:/static/virus.jpg") public Resource virus;
    @FXML public VBox generalLayout;
    @FXML public Button button_start;
    @FXML public Button button_scoreboard;

    public MainPageController() {
        super();
        fxmlLoader = new FXMLLoader(MainPageController.class.getClassLoader().getResource("GameMain.fxml"));
        fxmlLoader.setController(this);
        try {
            load = fxmlLoader.load();
        }catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @FXML
    public Button getButton_start() {
        return button_start;
    }

    @FXML
    public Button getButton_scoreboard(){
        return button_scoreboard;
    }

}

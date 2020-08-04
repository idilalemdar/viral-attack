package group24.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.io.IOException;

/***
 * HomePageController is responsible for handling
 * the very first page the user encounters. There will be
 * login and register buttons on it.
 */

@Component
public class HomePageController extends Controller{

    @FXML public VBox generalLayout;
    @FXML public Button button_login;
    @FXML public Button button_register;

    public HomePageController() {
        super();
        fxmlLoader = new FXMLLoader(HomePageController.class.getClassLoader().getResource("HomePage.fxml"));
        fxmlLoader.setController(this);
        try {
            load = fxmlLoader.load();
        }catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @FXML
    public Button getRegisterButton(){
        return button_register;
    }

    @FXML
    public Button getLoginButton(){
        return button_login;
    }
}

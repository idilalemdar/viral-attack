package group24.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import org.springframework.web.client.RestTemplate;

/***
 * Main Controller class.
 * All other controllers will extend this class.
 */

public class Controller {
    protected final RestTemplate restTemplate;
    @FXML protected Parent load;
    @FXML protected FXMLLoader fxmlLoader;
    protected String apiAddress;

    public Controller() {
        restTemplate = new RestTemplate();
    }

    @FXML
    public Parent getParent() {
        return load;
    }

    @FXML
    public FXMLLoader getFxmlLoader(){
        return fxmlLoader;
    }

    @FXML
    protected void alert(String content, Alert.AlertType type, String header, String title) {
        CheckBox cb = new CheckBox();
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

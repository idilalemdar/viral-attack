package group24.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;

import java.io.IOException;

/**
 * RegisterController is responsible for sign up
 * operations of newcoming users. It also warns the user
 * if the username is already taken or some fields are missing.
 */

public class RegisterController extends Controller {

    @FXML public VBox generalLayout;
    @FXML public Button button_back;
    @FXML public Button button_submit;
    @FXML public TextField username;
    @FXML public PasswordField password;
    @FXML public Label label;

    public RegisterController(String api) {
        super();
        fxmlLoader = new FXMLLoader(RegisterController.class.getClassLoader().getResource("Register.fxml"));
        fxmlLoader.setController(this);
        try {
            load = fxmlLoader.load();
        }catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        apiAddress = api;
    }

    /**
     * communicates with the server when user clicks
     * submit button in the register method
     */
    @FXML
    public boolean clickSubmitButton() {
        boolean success = true;
        String name = username.getText();
        String pword = password.getText();
        if(!name.isEmpty() && !pword.isEmpty()){
            try {
                String jsonString = new JSONObject()
                        .put("userName", name)
                        .put("password", pword).toString();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
                ResponseEntity<String> response = restTemplate.exchange(apiAddress + "/user/register", HttpMethod.POST, entity, String.class);
                if (!response.hasBody()) {
                    label.setText("Username already taken.");
                    alert("Username already taken.", Alert.AlertType.ERROR, "Register", "Error");
                    success = false;
                } else {
                    label.setText("Registration successful!");
                    alert("Registration successful!", Alert.AlertType.INFORMATION, "Register","Success!");

                }
            } catch (JSONException ignored) {}
        }
        else {
            label.setText("Some fields are missing.");
            alert("Some fields are missing.", Alert.AlertType.ERROR, "Register","Error");
            success = false;
        }
        return success;
    }

    public Button getSubmitButton(){
        return button_submit;
    }
    public Button getBackButton(){
        return button_back;
    }

}

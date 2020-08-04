package group24.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;

import java.io.IOException;
import java.util.Objects;

/***
 * LoginController class is responsible for
 * querying the user of their username and password,
 * and then submitting it to be evaluated.
 * In case of successful login, the controller navigates
 * the user to the main game screen. Else, it prompts user
 * about what is wrong.
 */

public class LoginController extends Controller {

    @FXML public Button button_back;
    @FXML public Button button_signin;
    @FXML public TextField username;
    @FXML public PasswordField password;
    @FXML public Label label;
    @FXML public VBox generalLayout;

    public LoginController(String api) {
        super();
        fxmlLoader = new FXMLLoader(LoginController.class.getClassLoader().getResource("Login.fxml"));
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
     * sign in button in the login method
     * @return username of the logged in user,
     * null if the login operation is not successful
     */

    @FXML
    public String clickLoginButton() {
        String uname = null;
        String name = username.getText();
        String pword = password.getText();

        if(!(name.isEmpty() || pword.isEmpty())){
            try {
                String jsonString = new JSONObject()
                        .put("userName", name)
                        .put("password", pword).toString();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
                ResponseEntity<String> response =
                        restTemplate.exchange(apiAddress + "/user/login", HttpMethod.POST, entity, String.class);
                if (!response.hasBody()) {
                    label.setText("Wrong credentials provided. Try again.");
                    alert("Wrong credentials provided. Try again.", Alert.AlertType.ERROR, "Login", "Error");
                } else {
                    String [] split = Objects.requireNonNull(response.getBody()).split("\"empty\":");
                    if (split[1].startsWith("true")) {
                        label.setText("User has already logged in.");
                        alert("User has already logged in.", Alert.AlertType.ERROR, "Login", "Error");
                    } else {
                        label.setText("Login successful!");
                        alert("Login successful!", Alert.AlertType.INFORMATION, "Login", "Success!");
                        uname = name;
                    }
                }
            } catch (JSONException ignored) {}
        }
        else {
            label.setText("Some fields are missing.");
            alert("Some fields are missing.", Alert.AlertType.ERROR, "Login", "Error");
        }
        return uname;
    }


    public Button getSigninButton(){
        return button_signin;
    }
    public Button getBackButton(){
        return button_back;
    }

}

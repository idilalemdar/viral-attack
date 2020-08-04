package group24.client.controller;

import group24.client.models.ScoreBoard;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.sql.Date;

import static java.lang.Integer.parseInt;

/***
 * ScoreBoardController is responsible for handling
 * leaderboard requests. It shows the games with the best scores
 * regarding the past 7 or 30 days.
 */

public class ScoreBoardController extends Controller {

    @FXML public TableView<ScoreBoard> table;
    @FXML public TableColumn<ScoreBoard, Integer> scoreColumn;
    @FXML public TableColumn<ScoreBoard, String> usernameColumn;
    @FXML public TableColumn<ScoreBoard, Integer> highScoreColumn;
    @FXML public TableColumn<ScoreBoard, Date> dateColumn;
    @FXML public Button button_week;
    @FXML public Button button_month;
    @FXML public Button button_back;
    @FXML public GridPane generalLayout;

    public ScoreBoardController(String api) {
        super();
        fxmlLoader = new FXMLLoader(ScoreBoardController.class.getClassLoader().getResource("ScoreBoard.fxml"));
        fxmlLoader.setController(this);
        try {
            load = fxmlLoader.load();
        }catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

        apiAddress = api;
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        usernameColumn.setCellValueFactory(param -> {
                    ScoreBoard value = param.getValue();
                    return new SimpleObjectProperty<>(value.getUser());
        });
        highScoreColumn.setCellValueFactory(param -> {
            ScoreBoard value = param.getValue();
            return new SimpleObjectProperty<>(value.getHighScore());
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    /**
     * communicates with the server when user clicks on
     * Last Week or Last Month button to get the scoreboard
     * on those time intervals
     */
    @FXML
    public void clickScoreBoard(String days) {
        try {
            String route = apiAddress + "/scoreboard/" + days;
            ResponseEntity<String> response = restTemplate
                    .exchange(route, HttpMethod.GET,
                            null, new ParameterizedTypeReference<>() {
                            });
            String body = response.getBody();
            assert body != null;
            body = body.substring(2, body.length() - 2);
            String[] objects = body.split("},\\{");
            ObservableList<ScoreBoard> oblist = FXCollections.observableArrayList();
            for (String object: objects) {
                String [] fields = object.split(",");
                String [] dateField = fields[1].split(":");
                String [] scoreField = fields[2].split(":");
                String [] userNameField = fields[7].split(":");
                userNameField[1] = userNameField[1].substring(1, userNameField[1].length() - 2);
                String [] highScoreField = fields[5].split(":");
                oblist.add(new ScoreBoard(dateField[1], parseInt(scoreField[1]), userNameField[1], parseInt(highScoreField[1])));
            }
            table.setItems(oblist);
        }
        catch(NullPointerException ignored){}
    }

    @FXML
    public Button getButton_week(){
        return button_week;
    }

    @FXML
    public Button getButton_month() {
        return button_month;
    }

    @FXML
    public Button getButton_back() {
        return button_back;
    }

    @FXML
    public void clearTable() {
        table.getItems().clear();
    }
}

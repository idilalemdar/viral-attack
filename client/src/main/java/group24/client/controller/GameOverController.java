package group24.client.controller;

import group24.client.Constants;
import group24.client.models.LongValueUpdate;
import group24.client.models.entities.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.io.IOException;

/***
 * GameOverControllerClass is responsible for
 * showing the user the Game Over screen
 * and navigating them back to the main page.
 */

@Component
public class GameOverController extends Controller {

    @FXML public BorderPane generalLayout;
    @FXML public Button button_back;

    public GameOverController() {
        super();
        fxmlLoader = new FXMLLoader(HomePageController.class.getClassLoader().getResource("GameOver.fxml"));
        fxmlLoader.setController(this);
        try {
            load = fxmlLoader.load();
        }catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @FXML
    public Button getButton_back() {
        return button_back;
    }

    /**
     * Called for showing the final information on the screen
     * @param level used for checking whether it is the multiplayer level
     * @param player used for getting the player data
     * @param opponentName used for showing the opponent name on the game over screen
     * @param opponentFinalscore used for showing the opponent score on the game over screen
     * @param hpTaken used for calculating how much HP did the opponent took from the final boss
     */
    @FXML
    public void setPlayerInfo(int level, Player player, String opponentName, LongValueUpdate opponentFinalscore, long hpTaken) {
        String message = player.getName() + ", your score is: " + player.getScore() + "!";
        if (level == 5) {
            message += "\nYour opponent " + opponentName + "'s score is: " + opponentFinalscore.value
                +"\n"+ Constants.WinnerBonus + " bonus points already applied to ";
            if (opponentFinalscore.value > player.getScore()) {
                message += opponentName + "!";
            } else {
                message += player.getName() + "!";
            }
            message += "\nTotal points by killing the boss: " + (player.getHPTaken() + hpTaken);
            message += "\n" + player.getName() + " took " + player.getHPTaken() + " points.";
            message += "\n" + opponentName + " took " + hpTaken + " points.";
        }
        Text text = new Text(message);
        text.setFont(Font.font("System", FontWeight.BOLD, 28));
        text.setFill(Color.color(Constants.R, Constants.G, Constants.B));
        generalLayout.setCenter(text);
    }
}

package group24.client.controller;

import group24.client.Constants;
import group24.client.models.LongValueUpdate;
import group24.client.models.entities.Player;
import group24.client.models.entities.aliens.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.Integer.parseInt;

/***
 * GameControllerClass is responsible for
 * leveling up throughout the game and
 * finally, sending the game data to the backend.
 */

public class GameController extends Controller{
    private Player player;
    private final String apiAddress;
    public GameController(String api) {
        super();
        player = null;
        apiAddress = api;
    }

    /**
     * This method is the public interface for launching given level.
     * Corresponding private methods then populate the initial level screen.
     * @param alienList list of aliens in that level
     * @param cw cell width of a standard alien (i.e. except for the final boss)
     * @param ch cell width of a standard alien (i.e. except for the final boss)
     */
    public void initializeLvl(int lvl, ArrayList<Alien> alienList, int cw, int ch) {
        assert alienList.isEmpty();
        switch (lvl){
            case 1:
                level1(alienList, cw, ch);
                break;
            case 2:
                level2(alienList, cw, ch);
                break;
            case 3:
                level3(alienList, cw, ch);
                break;
            case 4:
                level4(alienList, cw, ch);
                break;
            case 5:
                level5(alienList, cw, ch);
                break;
        }
    }

    /**
     * Level 5 initialization. There will only be the final boss COVID19 (shooting alien)
     */
    private void level5(ArrayList<Alien> alienList, int cw, int ch) {
        COVID19 covid19 = new COVID19(Constants.CovidInitX, Constants.CovidInitY);
        covid19.setImage("static/bomb.png", cw * Constants.CovidScale, ch * Constants.CovidScale);
        alienList.add(covid19);
    }

    /**
     * Level 4 initialization. There will be 3 SARS and 3 MERS (both shooting aliens)
     */
    private void level4(ArrayList<Alien> alienList, int cw, int ch) {
        double row = Constants.level4InitRow;
        for (int i = 0 ; i < 6 ; i++) {
            if (i % 2 == 1) {
                SARS sars = new SARS(0, row - 40);
                sars.setImage("static/SARS.png", cw, ch);
                alienList.add(sars);
            } else {
                MERS mers = new MERS(750, row);
                mers.setImage("static/MERS.png", cw, ch);
                alienList.add(mers);
            }
            row += 80;
        }
    }

    /**
     * Level 3 initialization. There will be 12 H5N1's (non-shooting alien)
     * plus 1 SARS (shooting alien) and 1 MERS (shooting alien)
     */
    private void level3(ArrayList<Alien> alienList, int cw, int ch) {
        level2(alienList, cw, ch);
        MERS mers = new MERS(Constants.MERSInitX,Constants.MERSInitY);
        mers.setImage("static/MERS.png", cw, ch);
        alienList.add(mers);
    }

    /**
     * Level 2 initialization. There will be 12 H5N1's (non-shooting alien)
     * plus 1 SARS (shooting alien)
     */
    private void level2(ArrayList<Alien> alienList, int cw, int ch) {
        level1(alienList, cw, ch);
        SARS sars = new SARS(Constants.SARSInitX,Constants.SARSInitY);
        sars.setImage("static/SARS.png", cw, ch);
        alienList.add(sars);
    }

    /**
     * Level 1 initialization. There will be 12 H5N1's (non-shooting alien)
     */

    private void level1(ArrayList<Alien> alienList, int cw, int ch){
        double x = 0;
        double y = 0;
        for (int j = 0; j < Constants.H5N1RowCount; j++) {
            for (int i = 0; i < Constants.H5N1ColumnCount; i++) {
                Alien alien = new H5N1(x, y);
                alien.setImage("static/H5N1.png", cw, ch);
                alienList.add(alien);
                x += Constants.H5N1ColumnSpace;
            }
            x = 0;
            y += Constants.H5N1RowSpace;
        }
    }

    /**
     * Public interface for sending data to the backend, as well as
     * applying bonus points to the player who won.
     * @param level used for checking whether it is the final level
     * @param player used for getting player data
     * @param opponentScore score of the opponent player, for showing
     *                      after the multiplayer level is finished
     */

    public void sendStats(int level, Player player, LongValueUpdate opponentScore) {
        this.player = player;
        if (level == 5){
            if (player.getScore() > opponentScore.value) {
                player.applyBonus();
            } else {
                opponentScore.value += Constants.WinnerBonus;
            }
        }
        updateHighScore();
        saveGame();
    }

    /**
     * Sends the user score to the backend
     * for checking whether it is the new highscore of the user or not.
     */
    private void updateHighScore() {
        restTemplate.exchange(
                apiAddress + "/user/highscore?username=" + player.getName() + "&hs=" + player.getScore(),
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<>() {
        });
    }

    /**
     * Adds new entry to the scoreboard using the player information
     */
    private void saveGame() {
        ResponseEntity<String> user = restTemplate
                .exchange(apiAddress + "/user/get/name/" + player.getName(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });
        String bodyRaw = user.getBody();
        assert bodyRaw != null;
        bodyRaw = bodyRaw.substring(1, bodyRaw.length() - 1);
        String [] elements = bodyRaw.split(",");
        int id = parseInt(elements[0].split(":")[1]);
        String pwd = elements[1].split(":")[1];
        String password = (pwd).substring(1, pwd.length() - 1);
        int highScore = parseInt(elements[2].split(":")[1]);
        String uname = elements[4].split(":")[1];
        String userName = uname.substring(1, uname.length() - 1);
        try {
            JSONObject usr = new JSONObject()
                    .put("id", id)
                    .put("password", password)
                    .put("highScore", highScore)
                    .put("userName", userName);
            String jsonString = new JSONObject()
                    .put("date", new Date(Calendar.getInstance().getTime().getTime()))
                    .put("score", player.getScore())
                    .put("user", usr).toString();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);
            restTemplate.exchange(
                            apiAddress + "/scoreboard/add",
                            HttpMethod.POST,
                            entity,
                            String.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

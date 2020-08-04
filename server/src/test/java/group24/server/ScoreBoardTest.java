package group24.server;

import group24.server.models.ScoreBoard;
import group24.server.models.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.Assert.assertEquals;


@SpringBootTest
public class ScoreBoardTest {
    /**
     * Tests whether user id in the game belongs to the user.
     * */
    @Test
    public void uidTest() {
        User user1 = new User("Halit_" + Math.random() * 10000, "1564", 58);
        User user2 = new User("Halil_" + Math.random() * 10000, "6256", 170);

        Date date1 = Date.valueOf("2020-03-12");
        Date date2 = Date.valueOf("2020-03-02");

        ScoreBoard game1 = new ScoreBoard(user1, date1, 150);
        ScoreBoard game2 = new ScoreBoard(user2, date2, 55);

        assertEquals(user1.getId(), game1.getUserID());
        assertEquals(user2.getId(), game2.getUserID());
    }
}

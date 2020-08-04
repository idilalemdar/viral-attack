package group24.server;

import group24.server.models.ScoreBoard;
import group24.server.models.User;
import group24.server.repository.ScoreBoardRepository;
import group24.server.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.sql.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreBoardRepositoryTest {
    @Autowired
    private ScoreBoardRepository scoreBoardRepository;

    @Autowired
    private UserRepository userRepository;


    @Before
    @Transactional
    public void setUp() {
        User user1 = new User("user" + Math.random() * 10000, "1564", 58);
        User user2 = new User("user" + Math.random() * 10000, "6256", 170);
        User user3 = new User("user" + + Math.random() * 10000, "1555", 196);
        User user4 = new User("user" + Math.random() * 10000, "8552", 256);
        this.userRepository.save(user1);
        this.userRepository.save(user2);
        this.userRepository.save(user3);
        this.userRepository.save(user4);
        Date date = new Date(System.currentTimeMillis());
        Date date1 = Date.valueOf("2020-03-22");
        Date date2 = Date.valueOf("2020-03-19");
        ScoreBoard game1 = new ScoreBoard(user1, date, 240);
        ScoreBoard game2 = new ScoreBoard(user2, date, 750);
        ScoreBoard game3 = new ScoreBoard(user3, date1, 150);
        ScoreBoard game4 = new ScoreBoard(user4, date2, 250);
        this.scoreBoardRepository.save(game1);
        this.scoreBoardRepository.save(game2);
        this.scoreBoardRepository.save(game3);
        this.scoreBoardRepository.save(game4);
    }

    /**
     * Tests whether score in game with id=1 is as expected.
     * */
    @Test
    public void testFetchData(){

        ScoreBoard game = scoreBoardRepository.findById(1);
        assertNotNull(game);
        assertEquals(240, game.getScore());

    }


}
package group24.server;

import group24.server.models.User;

import group24.server.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.sql.SQLException;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User user1;

    /**
     * Adds two new users to the database.
     * */

    @Before
    @Transactional
    public void setUp() throws Exception, SQLException {
        user1 = new User("user" + + Math.random() * 10000, "1234", 0);
        User user2 = new User("user" + + Math.random() * 10000, "1258", 0);
        this.userRepository.save(user1);
        this.userRepository.save(user2);
    }

    /**
     * Tests whether the password of the given username is as expected.
     * */

    @Test
    public void testFetchData(){

        User userA = userRepository.findByUsername(user1.getUserName());
        assertNotNull(userA);
        assertEquals(user1.getPassword(), userA.getPassword());
    }
}

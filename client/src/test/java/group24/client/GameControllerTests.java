package group24.client;

import group24.client.controller.GameController;
import group24.client.models.entities.aliens.Alien;
import group24.client.models.entities.missiles.EnemyMissile;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;

import static org.junit.Assert.*;

@SpringBootTest
public class GameControllerTests extends ApplicationTest {
    @Value("${spring.application.apiAddress}") private String apiAddress;
    @Value("${spring.application.cellWidth}") private int cellWidth;
    @Value("${spring.application.cellHeight}") private int cellHeight;

    @Before
    public void setup () throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(GameApplication.class);
    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void Level1() {
        GameController gameController = new GameController(apiAddress);
        ArrayList<Alien> alienList = new ArrayList<>();
        gameController.initializeLvl(1, alienList, cellWidth, cellHeight);
        assertEquals(36, alienList.size());
        for (Alien alien: alienList) {
            assertTrue(alien.getName().equalsIgnoreCase("H5N1"));
        }
    }

    @Test
    public void Level2() {
        GameController gameController = new GameController(apiAddress);
        ArrayList<Alien> alienList = new ArrayList<>();
        gameController.initializeLvl(2, alienList, cellWidth, cellHeight);
        assertEquals(37, alienList.size());
        for (int i = 0; i < alienList.size() - 1; i++) {
            assertTrue(alienList.get(i).getName().equalsIgnoreCase("H5N1"));
        }
        assertTrue(alienList.get(36).getName().equalsIgnoreCase("SARS"));
    }

    @Test
    public void Level3() {
        GameController gameController = new GameController(apiAddress);
        ArrayList<Alien> alienList = new ArrayList<>();
        gameController.initializeLvl(3, alienList, cellWidth, cellHeight);
        assertEquals(38, alienList.size());
        for (int i = 0; i < alienList.size() - 2; i++) {
            assertTrue(alienList.get(i).getName().equalsIgnoreCase("H5N1"));
        }
        assertTrue(alienList.get(36).getName().equalsIgnoreCase("SARS"));
        assertTrue(alienList.get(37).getName().equalsIgnoreCase("MERS"));
    }

    @Test
    public void Level4() {
        GameController gameController = new GameController(apiAddress);
        ArrayList<Alien> alienList = new ArrayList<>();
        gameController.initializeLvl(4, alienList, cellWidth, cellHeight);
        assertEquals(6, alienList.size());
        for (int i = 0; i < alienList.size(); i++) {
            if (i % 2 == 1) {
                assertTrue(alienList.get(i).getName().equalsIgnoreCase("SARS"));
            } else {
                assertTrue(alienList.get(i).getName().equalsIgnoreCase("MERS"));
            }
        }
    }

    @Test
    public void Level5() {
        GameController gameController = new GameController(apiAddress);
        ArrayList<Alien> alienList = new ArrayList<>();
        gameController.initializeLvl(5, alienList, cellWidth, cellHeight);
        assertEquals(1, alienList.size());
        assertTrue(alienList.get(0).getName().equalsIgnoreCase("COVID19"));
    }

    @Test
    public void alienShoot(){
        GameController gameController = new GameController(apiAddress);
        ArrayList<Alien> alienList = new ArrayList<>();
        gameController.initializeLvl(4, alienList, cellWidth, cellHeight);
        for (Alien alien: alienList) {
            EnemyMissile missile = alien.shoot();
            assertNotNull(missile);
        }
    }
}

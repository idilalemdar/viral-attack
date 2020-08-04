package group24.client;

import group24.client.models.entities.Player;
import group24.client.models.entities.aliens.H5N1;
import group24.client.models.entities.aliens.MERS;
import group24.client.models.entities.aliens.SARS;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;


public class EntityTests {

    private final int maxgrid = 800;

    /**
     * Tests whether the move method has no effect on the coordinates of H5N1, as expected.
     * */
    @Test
    public void moveH5N1() {
        H5N1 h5n1 = new H5N1(40, 80);
        h5n1.move(maxgrid);
        assertEquals(40, ((int) h5n1.getPosX()));
        assertEquals(80, ((int) h5n1.getPosY()));
    }

    /**
     * Tests whether moving SARS once gives the correct coordinates.
     * */
    @Test
    public void moveSARSonce(){
        SARS sars = new SARS(400, 200);
        assertEquals(400, ((int) sars.getPosX()));
        assertEquals(200, ((int) sars.getPosY()));

        sars.move(maxgrid);

        assertEquals(350, ((int) sars.getPosX()));
        assertEquals(200, ((int) sars.getPosY()));
    }

    /**
     * Tests whether moving MERS once gives the correct coordinates.
     * */
    @Test
    public void moveMERSonce(){
        MERS mers = new MERS(400, 250);
        assertEquals(400, ((int) mers.getPosX()));
        assertEquals(250, ((int) mers.getPosY()));

        mers.move(maxgrid);

        assertEquals(450, ((int) mers.getPosX()));
        assertEquals(250, ((int) mers.getPosY()));
    }

    /**
     * Tests whether moving SARS changes direction when moved from the corner.
     * */
    @Test
    public void moveSARSFarLeftCorner(){
        SARS sars = new SARS(0,200);
        sars.move(maxgrid);
        assertEquals(50, ((int) sars.getPosX()));
        assertEquals(200, ((int) sars.getPosY()));
    }

    /**
     * Tests whether moving SARS changes direction when moved from the corner.
     * */
    @Test
    public void moveMERSFarRightCorner(){
        MERS mers = new MERS(maxgrid - 50,250);
        mers.move(maxgrid);
        assertEquals(700, ((int) mers.getPosX()));
        assertEquals(250, ((int) mers.getPosY()));
    }

    @Test
    public void playerConstructor() {
        Player player = new Player();
        assertEquals(9, player.getHP());
        assertEquals(0, player.getScore());
    }

    /**
     * Tests player's score according to the last level they completed.
     * */
    @Test
    public void score() {
        Player player = new Player();
        assertEquals(0, player.getScore());
        player.calculateScore(1);
        assertEquals(240, player.getScore());
        player.calculateScore(2);
        assertEquals(440, player.getScore());
    }
}

package group24.client.models.entities;

import group24.client.Constants;
import group24.client.models.entities.missiles.DefenseMissile;
import group24.client.models.entities.missiles.Missile;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Player class holding the information related to the player
 * and providing functions related to score calculations.
 */
public class Player implements Entity{
    private int HP; /** hit points of the player*/
    private String user; /** user that is playing*/
    private int score; /** score of the player*/
    private long HPtakenFromCovid; /** hit points taken from the final boss*/
    private ImageView image; /** image of the player*/

    public Player() {
        this.HP = Constants.PlayerHP;
        score = 0;
        HPtakenFromCovid = 0;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return user;
    }

    public int getHP() {
        return this.HP;
    }

    public void decreaseHP() {
        this.HP--;
    }

    /**
     * Shooting method for the player.
     * @return new DefenseMissile right above the current position of the player.
     */
    public DefenseMissile shoot() {
        return new DefenseMissile(image.getX(), image.getY() - 40);
    }

    @Override
    public void setImage(String filename, double w, double h) {
        Image i = new Image(filename, w, h, true, false);
        image = new ImageView(i);
    }

    /**
     * Calculates the score of the player according to level information.
     * @param level Level information for calculating the score.
     */
    public void calculateScore(int level) {
        if (level == 5) { // first calculate the score gained at 4th level
            int prevLevel = level - 1;
            int quotient = prevLevel - 1;
            score = prevLevel * 150 + quotient * quotient * 50;
            score += HPtakenFromCovid;
        } else {
            int quotient = level - 1;
            score = level * 150 + quotient * quotient * 50;
            score += 10 * HP;
        }
    }

    public void increaseHPTaken(){
        HPtakenFromCovid++;
    }

    public void applyBonus(){
            score += Constants.WinnerBonus;
    }

    public ImageView getImage(){
        return image;
    }

    @Override
    public void render(GraphicsContext gc) { }

    @Override
    public double getPosX() {
        return 0;
    }

    @Override
    public double getPosY() {
        return 0;
    }

    /**
     * @return bounding box of the player's image
     */
    public Rectangle2D getBoundary() {
        return new Rectangle2D(image.getX(), image.getY(), image.getFitWidth(), image.getFitHeight());
    }

    /**
     * Checks whether or not here is a collision between the player and the missile
     * @param missile missile which we control whether or not a collision occurs
     * @return whether or not there is a collision with the given missile
     */
    public boolean intersects(Missile missile) {
        return missile.getBoundary().intersects(this.getBoundary());
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }

    public void resetHP() {
        HP = Constants.PlayerHP;
    }

    public long getHPTaken() { return HPtakenFromCovid;}
}

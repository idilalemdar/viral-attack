package group24.client.models.entities.aliens;

import group24.client.Constants;
import group24.client.models.entities.Entity;
import group24.client.models.entities.missiles.EnemyMissile;
import group24.client.models.entities.missiles.Missile;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract base class for all aliens. getName() and move() methods
 * are to be overwritten by the inheriting classes.
 */
public abstract class Alien implements Entity {
    private ImageView image; /** image of the alien*/
    private double positionX; /** x coordinate of the alien*/
    private double positionY; /** y coordinate of the alien*/
    protected boolean shoots; /** whether or not the alien shoots*/
    protected int HP; /** Hit points of the alien*/
    protected boolean isLeft; /** initial orientation of a moving alien*/

    public Alien(double x, double y) {
        this.positionX = x;
        this.positionY = y;
    }

    public String getName() {
        return null;
    }

    /**
     * @return remaining hit points (HP) of the entity.
     */
    public int getHP() {
        return this.HP;
    }

    public void decreaseHP() {
        this.HP--;
    }

    public void move(int maxgrid) {

    }

    /**
     * Shooting method for any alien.
     * @return new EnemyMissile right below the current position of the alien.
     */
    public EnemyMissile shoot() {
        return new EnemyMissile(this.getPosX(), this.getPosY() + 40);
    }

    @Override
    public void setImage(String filename, double w, double h) {
        Image i = new Image(filename, w, h, true, false);
        image = new ImageView(i);
        image.setId(getName());
        image.setFitWidth(w);
        image.setFitHeight(h);
    }

    @Override
    public ImageView getImage(){
        return image;
    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    /**
     * Draws the alien on the canvas using the image and coordinates of it.
     * @param gc GraphicsContext to draw image on.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(image.getImage(), positionX, positionY);
    }

    /**
     * @return bounding box of the alien's image
     */
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, image.getFitWidth(), image.getFitHeight());
    }

    /**
     * Checks whether or not here is a collision between the alien and the missile
     * @param missile missile which we control whether or not a collision occurs
     * @return whether or not there is a collision with the given missile
     */
    public boolean intersects(Missile missile) {
        return missile.getBoundary().intersects(this.getBoundary());
    }

    public double getPosX(){return positionX;}
    public double getPosY(){return positionY;}

    /**
     * Checks direction for the moving aliens
     * so that aliens move from one edge of the game screen to the other.
     * @param maxgrid used for boundary check at the right edge of the game screen.
     */
    protected void checkDirection(int maxgrid) {
        if (this.getPosX() == 0) {
            this.isLeft = false;
        }
        else if (this.getPosX() == maxgrid - Constants.SmallAlienGridCheck) {
            this.isLeft = true;
        }
    }
}

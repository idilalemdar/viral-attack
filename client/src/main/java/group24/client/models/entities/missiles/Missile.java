package group24.client.models.entities.missiles;

import group24.client.models.entities.Entity;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract base class for all missiles. move() method
 * is to be overwritten by the inheriting classes.
 */
public abstract class Missile implements Entity {
    protected double positionX; /** x coordinate of the missile*/
    protected double positionY; /** y coordinate of the missile*/
    protected ImageView image; /** image of the missile*/

    public Missile(double x, double y){
        this.positionX = x;
        this.positionY = y;
    }

    @Override
    public void setImage(String filename, double w, double h) {
        Image i = new Image(filename, w, h, true, false);
        image = new ImageView(i);
        image.setFitWidth(w);
        image.setFitHeight(h);
    }

    @Override
    public ImageView getImage(){
        return image;
    }

    public void move() {

    }

    @Override
    public double getPosX(){return positionX;}
    @Override
    public double getPosY(){return positionY;}

    /**
     * Draws the missile on the canvas using the image and coordinates of it.
     * @param gc GraphicsContext to draw image on.
     */
    @Override
    public void render(GraphicsContext gc)
    {
        gc.drawImage(image.getImage(), positionX, positionY);
    }

    /**
     * @return bounding box of the missile's image
     */
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, image.getFitWidth(), image.getFitHeight());
    }
}

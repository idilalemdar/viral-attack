package group24.client.models.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

/**
 *Interface for entities (aliens, player and the missiles)
 * on the game screen.
 */
public interface Entity {
    void setImage(String filename, double w, double h);
    ImageView getImage();
    void render(GraphicsContext gc);
    double getPosX();
    double getPosY();
}

package group24.client.models.entities.aliens;

import group24.client.Constants;

/**
 * SARS implementation. It is a shooting and moving type alien.
 */
public class SARS extends Alien {
    public SARS(double x, double y){
        super(x,y);
        this.HP = Constants.SARSHP;
        this.shoots = true;
        this.isLeft = true;
    }

    /**
     * Moves the alien by the AlienStep amount on the y-axis. It performs boundary check at the both edge
     * of the game screen and reverts the alien's direction.
     * @param maxgrid used for boundary check at the right edge of the game screen.
     */
    @Override
    public void move(int maxgrid) {
        this.checkDirection(maxgrid);
        if (this.isLeft){
            this.setPosition(this.getPosX() - Constants.AlienStep, this.getPosY());
        } else {
            this.setPosition(this.getPosX() + Constants.AlienStep, this.getPosY());
        }
    }
    @Override
    public String getName() {
        return "SARS";
    }
}

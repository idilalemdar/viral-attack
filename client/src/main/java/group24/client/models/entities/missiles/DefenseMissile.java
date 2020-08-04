package group24.client.models.entities.missiles;

import group24.client.Constants;

/**
 * Defense missile implementation.
 * Those are the missiles thrown by the player.
 * Overrides move() method.
 */
public class DefenseMissile extends Missile {
    public DefenseMissile(double x, double y) {
        super(x, y);
    }
    @Override
    public void move(){
        this.positionY = this.positionY - Constants.MissileStep;
    }
}

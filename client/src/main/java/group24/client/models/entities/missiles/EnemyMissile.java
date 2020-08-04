package group24.client.models.entities.missiles;

import group24.client.Constants;

/**
 * Enemy missile implementation.
 * Those are the missiles thrown by the aliens.
 * Overrides move() method.
 */
public class EnemyMissile extends Missile{
    public EnemyMissile(double x, double y) {
        super(x, y);
    }
    @Override
    public void move(){
        this.positionY = this.positionY + Constants.MissileStep;
    }
}

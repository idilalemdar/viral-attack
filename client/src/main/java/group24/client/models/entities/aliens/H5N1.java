package group24.client.models.entities.aliens;

import group24.client.Constants;

/**
 * H5N1 implementation. It is the most basic, non-shooting and non-moving alien.
 */
public class H5N1 extends Alien {
    public H5N1(double x, double y){
        super(x,y);
        this.HP = Constants.H5N1HP;
        this.shoots = false;
    }
    @Override
    public void move(int maxgrid) {
    }
    @Override
    public String getName() {
        return "H5N1";
    }
}

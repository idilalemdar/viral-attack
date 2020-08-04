package group24.client.models.entities.aliens;

import group24.client.Constants;

/**
 * COVID19 implementations. It is the final boss.
 * It is significantly bigger and has more HP
 * compared the other aliens. It shoots but it does not move.
 */
public class COVID19 extends Alien {
    public COVID19(double x, double y){
        super(x,y);
        this.HP = Constants.CovidHP;
        this.shoots = true;
    }

    @Override
    public void move(int maxgrid) {

    }

    @Override
    public String getName() {
        return "COVID19";
    }

}

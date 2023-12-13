package entities;

import main.Game;
import utilz.Constants;

import static utilz.Constants.EnemyConstants.*;

public class FireSpirit extends Enemy {

    public FireSpirit(float x, float y) {
        super(x, y, FIRE_SPIRIT_DIMENSIONS, FIRE_SPIRIT_DIMENSIONS, FIRE_SPIRIT);
        initHitbox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
    }

    public int flipX(){
        if (getWalkDir() == Constants.Directions.LEFT)
            return width;
        else
            return 0;
    }

    public int flipW(){
        if (getWalkDir() == Constants.Directions.LEFT)
            return -1;
        else
            return 1;
    }

}

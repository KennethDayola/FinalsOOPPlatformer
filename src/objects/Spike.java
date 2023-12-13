package objects;

import main.Game;

import static utilz.Constants.ObjectConstants.SPIKE_BOTTOM;

public class Spike extends GameObject{

    public Spike(int x, int y, int objType) {
        super(x, y, objType);

        initHitbox(32, 16);
        xDrawOffset = 0;
        if (getObjType() == SPIKE_BOTTOM)
            yDrawOffset = (int)(Game.SCALE * 16);
        hitbox.y += yDrawOffset;

    }

}
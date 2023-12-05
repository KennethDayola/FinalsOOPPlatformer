package objects;

import main.Game;

public class Portal extends GameObject{

    public Portal(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(32,32);
    }
    public void update() {
        updateAnimationTick();
    }

}
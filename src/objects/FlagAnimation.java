package objects;

public class FlagAnimation extends GameObject {
    public FlagAnimation(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(32,32);
        doAnimation = false;
    }
    public void update() {
        updateAnimationTick();
    }
}

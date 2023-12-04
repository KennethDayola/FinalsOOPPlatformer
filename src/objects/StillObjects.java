package objects;

public class StillObjects extends GameObject{
    public StillObjects(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(32,32);
    }
    public void update() {
        updateAnimationTick();
    }
}

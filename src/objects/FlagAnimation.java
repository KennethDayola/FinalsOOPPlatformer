package objects;

public class FlagAnimation extends GameObject {
    public boolean soundPlayedCheckpoint1 = false, soundPlayedCheckpoint2 = false, soundPlayedCheckpoint3 = false;

    public FlagAnimation(int x, int y, int objType) {
        super(x, y, objType);
        initHitbox(32,32);
        doAnimation = false;
    }
    public void update() {
        updateAnimationTick();
    }
}

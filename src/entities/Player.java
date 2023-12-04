package entities;

import static utilz.Constants.PlayerConstants.*;

import static utilz.HelpMethods.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import utilz.LoadSave;
import main.Game;
public class Player extends Entity {

    private Playing playing;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 1.25f * Game.SCALE;
    private int[][] lvlData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 15 * Game.SCALE;

    // Jumping / Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    private boolean isDead = false;
    private long deathTimestamp;
    private long deathDelay = 2000;
    private boolean deathAniPlayed = false;

    private int flipX = 0;
    private int flipW = 1;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (47 * Game.SCALE));
        this.playing = playing;
    }

    public void setCheckpoint(float xCheckpoint, float yCheckpoint) {
        this.x = xCheckpoint;
        this.y = yCheckpoint;
    }

    public void update() {
        updatePos();
        if (moving){
            checkWaterTouched();
            checkFlagTouched();
        }
        updateAnimationTick();
        setAnimation();
    }

    private void checkFlagTouched() {
        playing.checkFlagTouched(this);
    }

    public void render(Graphics g, int lvlOffset) {
        if (isDead && !deathAniPlayed) {
            g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
            if (aniIndex == 4) {
                deathAniPlayed = true;
            }
        } else if (deathAniPlayed){
            g.drawImage(animations[playerAction][5], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
        }else if (!isDead) {
            g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
        }
    }
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }

        }

    }

    private void setAnimation() {
        int startAni = playerAction;

        if (playerAction != DEATH && moving)
            playerAction = RUNNING;
        else if (playerAction != DEATH)
            playerAction = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }

        int ATTACK_1 = 1;
        if (attacking)
            playerAction = ATTACK_1;

        if (isDead) {
            long currentTime = System.currentTimeMillis();
            resetDirBooleans();
            if (currentTime - deathTimestamp >= deathDelay) {
                isDead = false;
                playing.resetAll();
            }
        }
        if (startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;


        if (jump)
            jump();

        if (!inAir)
            if ((!left && !right) || (right && left))
                return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir)
            if (!IsEntityOnFloor(hitbox, lvlData))
                inAir = true;

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;


		}
    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
            moving = true;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }

    }


    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
            animations = new BufferedImage[5][22];
            for (int j = 0; j < animations.length; j++)
                for (int i = 0; i < animations[j].length; i++)
                    animations[j][i] = img.getSubimage(i * 64, j * 64, 64, 64);


    }

    private void checkWaterTouched() {
        playing.checkWaterTouched(this);
    }


    public void kill() {
        playerAction = DEATH;
        isDead = true;
        deathTimestamp = System.currentTimeMillis();
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }
    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }
    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        deathAniPlayed = false;
        playerAction = IDLE;

        hitbox.x = x;
        hitbox.y = y;

        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }
    public boolean getIsDead(){
        return isDead;
    }
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
    public void setJump(boolean jump) {
        this.jump = jump;
    }

}

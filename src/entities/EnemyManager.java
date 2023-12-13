package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import utilz.LoadSave;
import utilz.MusicMethods;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[] fireSpiritArr;
    private ArrayList<FireSpirit> fireSpirits = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        fireSpirits = LoadSave.GetFireSpirit();
    }

    public void update(int[][] lvlData) {
        for (FireSpirit f : fireSpirits) {
            f.update(lvlData);
            if (f.hitbox.intersects(playing.getPlayer().hitbox) && !playing.getPlayer().getIsDead()) {
                playing.getPlayer().kill("normal");
                MusicMethods.fireDeathSound.play();
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawFireSpirit(g, xLvlOffset);
    }

    private void drawFireSpirit(Graphics g, int xLvlOffset) {
        for (FireSpirit f : fireSpirits) {
            g.drawImage(fireSpiritArr[f.getAniIndex()], (int) f.getHitbox().x - xLvlOffset - FIRE_SPIRIT_DRAWOFFSET_X + f.flipX(), (int) f.getHitbox().y - FIRE_SPIRIT_DRAWOFFSET_Y, FIRE_SPIRIT_DIMENSIONS * f.flipW(), FIRE_SPIRIT_DIMENSIONS, null);
        }

    }

    private void loadEnemyImgs() {
        fireSpiritArr = new BufferedImage[5];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.FIRE_SPIRIT_SPRITE);

        for (int i = 0; i < fireSpiritArr.length; i++) {
            fireSpiritArr[i] = temp.getSubimage(i * 32, 0, 32, 32);
        }
    }
}
package objects;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.ObjectConstants.*;

public class ObjectManager {
    private Playing playing;
    private ArrayList<WaterTop> waterTop;
    private BufferedImage[][]waterTopImg;
    private BufferedImage waterBase;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
        loadWaterAni(LoadSave.WATER_ANI);
    }
    private void loadImgs() {
        waterBase = LoadSave.GetSpriteAtlas(LoadSave.WATER_BASE);
    }
    private void loadWaterAni(String filePath){
        BufferedImage waterAni = LoadSave.GetSpriteAtlas(filePath);
        waterTopImg = new BufferedImage[3][4];

        for (int j = 0; j < waterTopImg.length; j++)
            for (int i = 0; i < waterTopImg[j].length; i++)
                waterTopImg[j][i] = waterAni.getSubimage(32 * i, 32 * j, 32, 32);
    }
    public void update(){
        for (WaterTop w: waterTop)
            w.update();
    }
    public void loadObjects(Level newLevel) {
        waterTop = newLevel.getWaterTop();
    }
    public void draw(Graphics g, int xLvlOffset) {
        drawWaterAni(g, xLvlOffset);
    }

    private void drawWaterAni(Graphics g, int xLvlOffset) {
        for (WaterTop w : waterTop) {
            int type = 0;
            if (w.getObjType() == WATER_TOP2)
                type = 1;
            if (w.getObjType() == WATER_TOP3)
                type = 2;
            g.drawImage(waterTopImg[type][w.getAniIndex()], (int) (w.getHitbox().x - w.getxDrawOffset() - xLvlOffset),
                    (int) (w.getHitbox().y - w.getyDrawOffset()), WATER_DIMENSIONS, WATER_DIMENSIONS, null);
        }
    }
    private void drawWaterHitbox(Graphics g, WaterTop waterTop, int xLvlOffset) {
        g.setColor(Color.BLUE);
        g.drawRect((int) (waterTop.getHitbox().x - xLvlOffset), (int) waterTop.getHitbox().y,
                (int) waterTop.getHitbox().width, (int) waterTop.getHitbox().height);
    }
}

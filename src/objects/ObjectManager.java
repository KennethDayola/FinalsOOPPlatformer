package objects;

import entities.Player;
import gamestates.Gamestate;
import gamestates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;
import utilz.MusicMethods;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.ObjectConstants.*;

public class ObjectManager {
    private Playing playing;
    private ArrayList<WaterTop> waterTop;
    private ArrayList<StillObjects> stillObjects;
    private ArrayList<FlagAnimation> flagAni;
    private ArrayList<Spike> spikes;
    private ArrayList<Portal> portal;

    private BufferedImage[][]waterTopImg;
    private BufferedImage[]flagTopImg;
    private BufferedImage [] stillObjectsImg;
    private BufferedImage [] spikeImg;
    private BufferedImage [] portalImg;

    private boolean inPortal = false, portalTouched = false;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadWaterAni();
        loadFLagAni();
        loadPortal();
        loadImg(LoadSave.STILL_OBJECTS);
        loadImg(LoadSave.SPIKE);
    }

    private void loadPortal() {
        BufferedImage portalAni = LoadSave.GetSpriteAtlas(LoadSave.PORTAL);
        portalImg = new BufferedImage[7];
        for (int i = 0; i < portalImg.length; i++)
            portalImg[i] = portalAni.getSubimage(32 * i, 0, 32, 48);
    }

    private void loadFLagAni() {
        BufferedImage flagAniTemp = LoadSave.GetSpriteAtlas(LoadSave.FLAG_TOP);
        flagTopImg = new BufferedImage[9];

        for (int i = 0; i < flagTopImg.length; i++)
            flagTopImg[i] = flagAniTemp.getSubimage(32 * i, 0, 32, 32);
    }

    private void loadWaterAni(){
        BufferedImage waterAni = LoadSave.GetSpriteAtlas(LoadSave.WATER_ANI);
        waterTopImg = new BufferedImage[3][4];

        for (int j = 0; j < waterTopImg.length; j++)
            for (int i = 0; i < waterTopImg[j].length; i++)
                waterTopImg[j][i] = waterAni.getSubimage(32 * i, 32 * j, 32, 32);
    }
    private void loadImg(String filePath){
        BufferedImage imgTemp = LoadSave.GetSpriteAtlas(filePath);
        if (filePath.equals(LoadSave.STILL_OBJECTS)) {
            stillObjectsImg = new BufferedImage[3];
            for (int i = 0; i < stillObjectsImg.length; i++)
                stillObjectsImg[i] = imgTemp.getSubimage(32 * i, 0, 32, 32);
        }
        else if (filePath.equals(LoadSave.SPIKE)){
            spikeImg = new BufferedImage[2];
            for (int i = 0; i < spikeImg.length; i++)
                spikeImg[i] = imgTemp.getSubimage(32 * i, 0, 32, 32);
        }
    }
    public void update(){
        for (WaterTop w: waterTop)
            w.update();
        for (FlagAnimation f: flagAni)
            f.update();
        for (Portal p: portal)
            p.update();
    }
    public void checkWaterTouched(Player p) {
        for (WaterTop w : waterTop)
            if (w.getHitbox().intersects(p.getHitbox())) {
                MusicMethods.waterDeathSound.play();
                p.kill("water");
            }
    }

    public void checkFlagTouched(Player p) {
        for (FlagAnimation f : flagAni) {
            if (f.getHitbox().intersects(p.getHitbox())) {
                f.doAnimation = true;
                if (f.getObjType() == FLAG_ANI) {
                    playing.setCheckpoint(2300 * Game.SCALE, 200 * Game.SCALE);
                    if(!f.soundPlayedCheckpoint1){
                        MusicMethods.checkpointSound.play();
                        f.soundPlayedCheckpoint1 = true;
                    }
                } else if (f.getObjType() == FLAG_ANI2) {
                    playing.setCheckpoint(4995 * Game.SCALE, 50 * Game.SCALE);
                    if(!f.soundPlayedCheckpoint2){
                        MusicMethods.checkpointSound.play();
                        f.soundPlayedCheckpoint2 = true;
                        applyStoryState(2);
                    }
                }else if (f.getObjType() == FLAG_ANI3) {
                    playing.setCheckpoint(8128 * Game.SCALE, 180 * Game.SCALE);
                    if(!f.soundPlayedCheckpoint2){
                        MusicMethods.checkpointSound.play();
                        f.soundPlayedCheckpoint2 = true;
                    }
                }
            }
        }
    }

    public void checkPortalTouched(Player player) {
        for (Portal p : portal)
            if (p.getHitbox().intersects(player.getHitbox())) {
                inPortal = true;
                if (!portalTouched) {
                    MusicMethods.portalSound.play();
                    applyStoryState(3);
                    portalTouched = true;
                }
            }
            else
                inPortal = false;
    }

    public void checkSpikesTouched(Player p) {
        for (Spike s : spikes)
            if (s.getHitbox().intersects(p.getHitbox())) {
                MusicMethods.spikeHit.play();
                p.kill("normal");
            }
    }

    public void loadObjects(Level newLevel) {
        waterTop = newLevel.getWaterTop();
        stillObjects = newLevel.getStillObjects();
        flagAni = newLevel.getFlagAni();
        spikes = newLevel.getSpikes();
        portal = newLevel.getPortal();
    }
    public void draw(Graphics g, int xLvlOffset) {
        drawObjects(g, xLvlOffset);
    }

    private void drawObjects(Graphics g, int xLvlOffset) {
        for (WaterTop w : waterTop) {
            int type = 0;
            if (w.getObjType() == WATER_TOP2)
                type = 1;
            if (w.getObjType() == WATER_TOP3)
                type = 2;
            g.drawImage(waterTopImg[type][w.getAniIndex()], (int) (w.getHitbox().x - w.getxDrawOffset() - xLvlOffset),
                    (int) (w.getHitbox().y - w.getyDrawOffset()), OBJECT_DIMENSIONS, OBJECT_DIMENSIONS, null);
        }
        for (StillObjects s: stillObjects){
            if (s.getObjType() == FLAG_BASE)
                g.drawImage(stillObjectsImg[0], (int) (s.getHitbox().x - s.getxDrawOffset() - xLvlOffset),
                        (int) (s.getHitbox().y - s.getyDrawOffset()), OBJECT_DIMENSIONS, OBJECT_DIMENSIONS, null);
            if (s.getObjType() == WATER_BASE)
                g.drawImage(stillObjectsImg[1], (int) (s.getHitbox().x - s.getxDrawOffset() - xLvlOffset),
                        (int) (s.getHitbox().y - s.getyDrawOffset()), OBJECT_DIMENSIONS, OBJECT_DIMENSIONS, null);
            if (s.getObjType() == E_KEY)
                if (inPortal)
                    g.drawImage(stillObjectsImg[2], (int) (s.getHitbox().x - s.getxDrawOffset() - xLvlOffset),
                        (int) (s.getHitbox().y - s.getyDrawOffset()), OBJECT_DIMENSIONS, OBJECT_DIMENSIONS, null);

        }
        for (FlagAnimation f: flagAni){
            if (!f.doAnimation) {
                // Draw the flag at the first index
                g.drawImage(flagTopImg[0], (int) (f.getHitbox().x - f.getxDrawOffset() - xLvlOffset),
                        (int) (f.getHitbox().y - f.getyDrawOffset()), OBJECT_DIMENSIONS, OBJECT_DIMENSIONS, null);
            } else {
                // Draw indices 1 to 5 during animation
                if (f.getAniIndex() >= 1 && f.getAniIndex() <= 5) {
                    g.drawImage(flagTopImg[f.getAniIndex()], (int) (f.getHitbox().x - f.getxDrawOffset() - xLvlOffset),
                            (int) (f.getHitbox().y - f.getyDrawOffset()), OBJECT_DIMENSIONS, OBJECT_DIMENSIONS, null);
                } else {
                    // Loop indices 6 to 8 when done with indices 1 to 5
                    int loopedIndex = (f.getAniIndex() - 1) % 3 + 6;
                    g.drawImage(flagTopImg[loopedIndex], (int) (f.getHitbox().x - f.getxDrawOffset() - xLvlOffset),
                            (int) (f.getHitbox().y - f.getyDrawOffset()), OBJECT_DIMENSIONS, OBJECT_DIMENSIONS, null);
                }
            }
        }
        for (Spike s : spikes) {
            int type = 0;
            if (s.getObjType() == SPIKE_TOP)
                type = 1;
            g.drawImage(spikeImg[type], (int) (s.getHitbox().x - s.getxDrawOffset() - xLvlOffset),
                    (int) (s.getHitbox().y - s.getyDrawOffset()), OBJECT_DIMENSIONS, OBJECT_DIMENSIONS, null);
        }
        for (Portal p: portal){
            g.drawImage(portalImg[p.getAniIndex()],(int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
                    (int) (p.getHitbox().y - p.getyDrawOffset()), OBJECT_DIMENSIONS, (int) (68*Game.SCALE), null);
        }
    }

    private void applyStoryState(int storyFlag){
        playing.getPlayer().resetDirBooleans();
        Gamestate.state = Gamestate.STORY;
        playing.getStory().setStoryFlag(storyFlag);
    }

    private void drawWaterHitbox(Graphics g, WaterTop waterTop, int xLvlOffset) {
        g.setColor(Color.BLUE);
        g.drawRect((int) (waterTop.getHitbox().x - xLvlOffset), (int) waterTop.getHitbox().y,
                (int) waterTop.getHitbox().width, (int) waterTop.getHitbox().height);
    }
    public boolean getInPortal(){
        return inPortal;
    }
    public void setPortalTouched(boolean portalTouched) {
        this.portalTouched = portalTouched;
    }
}

package levels;

import objects.FlagAnimation;
import objects.StillObjects;
import objects.WaterTop;
import utilz.HelpMethods;
import utilz.LoadSave;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level {
    private ArrayList<WaterTop> waterTop;
    private ArrayList<StillObjects> stillObjects;
    private ArrayList<FlagAnimation> flagAni;
    private int[][] lvlData;
    private BufferedImage img;

    public Level(int[][] lvlData) {
        img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ONE_DATA);
        createWaterTop();
        createStillObj();
        createFlagAni();
        this.lvlData = lvlData;
    }

    private void createFlagAni() {
        flagAni = HelpMethods.GetFlagAnimation(img);
    }
    private void createWaterTop() {
        waterTop = HelpMethods.GetWaterTop(img);
    }
    private void createStillObj() {
        stillObjects = HelpMethods.GetStillObjects(img);
    }
    public ArrayList<WaterTop> getWaterTop() {
        return waterTop;
    }
    public ArrayList<FlagAnimation> getFlagAni() {
        return flagAni;
    }
    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }
    public int[][] getLevelData() {
        return lvlData;
    }

    public ArrayList<StillObjects> getStillObjects() {
        return stillObjects;
    }
}
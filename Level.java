import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level {
    private ArrayList<WaterTop> waterTop;
    private int[][] lvlData;
    private BufferedImage img;

    private void createWaterTop() {
        waterTop = HelpMethods.GetWaterTop(img);
    }


    public Level(int[][] lvlData) {
        img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ONE_DATA);
        createWaterTop();
        this.lvlData = lvlData;
    }
    public ArrayList<WaterTop> getWaterTop() {
        return waterTop;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

}
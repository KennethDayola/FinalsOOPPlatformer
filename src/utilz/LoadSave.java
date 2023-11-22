package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;
public class LoadSave {

    public static final String PLAYER_ATLAS = "spritesheet3.png";
    public static final String LEVEL_ATLAS = "tilesgrass.png";
    public static final String LEVEL_ONE_DATA = "lvl_one_data.png";
    public static final String MENU_PLAYBTN = "playButtonSprite.png";
    public static final String MENU_EXITOPTIONSBTN = "settingsExitSprite.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static BufferedImage GetSpriteAtlas(String fileName){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/res/" + fileName);
        try {
             img = ImageIO.read(is);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }return img;
    }
    public static int[][] GetLevelData() {
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        return lvlData;
}}



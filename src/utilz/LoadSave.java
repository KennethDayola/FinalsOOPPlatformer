package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entities.FireSpirit;
import main.Game;

import static utilz.Constants.EnemyConstants.FIRE_SPIRIT;

import javax.imageio.ImageIO;

import main.Game;
public class LoadSave {

    public static final String PLAYER_ATLAS = "spritesheet5.png";
    public static final String LEVEL_ATLAS = "tilesgrass.png";
    public static final String LEVEL_ONE_DATA = "lvl_one_data.png";
    public static final String MENU_PLAYBTN = "playButtonSprite.png";
    public static final String MENU_EXITOPTIONSBTN = "settingsExitSprite.png";
    public static final String MENU_BACKGROUND = "menuBg.gif";
    public static final String PLAYING_BG_IMG = "playing_bg_img.png";
    public static final String BIG_CLOUDS = "big_clouds.png";
    public static final String SMALL_CLOUDS = "small_clouds.png";
    public static final String SMALL_TERRAIN = "small_terrain.png";
    public static final String WATER_ANI = "waterAni.png";
    public static final String STILL_OBJECTS = "stillObjectSprites.png";
    public static final String FLAG_TOP = "flagTop.png";
    public static final String SPIKE = "spike.png";
    public static final String PORTAL = "greenPortal.png";
    public static final String VN_SPRITE = "vnSprite.png";
    public static final String TEXT_BOX = "TextBox.png";
    public static final String COMPLETED_SCREEN = "completedScreen.png";
    public static final String FIRE_SPIRIT_SPRITE = "fireSpirit.png";

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

    public static ArrayList<FireSpirit> GetFireSpirit() {
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<FireSpirit> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == FIRE_SPIRIT)
                    list.add(new FireSpirit(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        return list;

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



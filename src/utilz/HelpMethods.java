package utilz;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.ObjectConstants.*;

import main.Game;
import objects.*;


public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    if (!IsSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    public static ArrayList<Portal> GetPortal(BufferedImage img) {
        ArrayList<Portal> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == PORTAL)
                    list.add(new Portal(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
            }

        return list;
    }

    public static ArrayList<WaterTop> GetWaterTop(BufferedImage img){
        ArrayList<WaterTop> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == WATER_TOP1 || value == WATER_TOP2 || value == WATER_TOP3 )
                    list.add(new WaterTop(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
            }
        return list;
    }

    public static ArrayList<Spike> GetSpikes(BufferedImage img) {
        ArrayList<Spike> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == SPIKE_TOP)
                    list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
                else if (value == SPIKE_BOTTOM)
                    list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
            }

        return list;
    }

    public static ArrayList<FlagAnimation> GetFlagAnimation(BufferedImage img){
        ArrayList<FlagAnimation> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == FLAG_ANI)
                    list.add(new FlagAnimation(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
                else if (value == FLAG_ANI2)
                    list.add(new FlagAnimation(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
                else if (value == FLAG_ANI3)
                    list.add(new FlagAnimation(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
            }
        return list;
    }

    public static ArrayList<StillObjects> GetStillObjects(BufferedImage img){
        ArrayList<StillObjects> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == WATER_BASE || value == FLAG_BASE )
                    list.add(new StillObjects(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
                if (value == E_KEY)
                    list.add(new StillObjects(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
            }
        return list;
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = lvlData[(int) yIndex][(int) xIndex];
        // INCREASE IF NA INCREASE ANG TILES INDEX, ADJUST LATER
        if (value >= 48 || value < 0 || value != 10)
            return true;
        return false;
    }
    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * Game.TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (57 +Game.TILES_SIZE - hitbox.height)+7;
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTile * Game.TILES_SIZE;

    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Check the pixel below bottomleft and bottomright
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;

        return true;

    }

}
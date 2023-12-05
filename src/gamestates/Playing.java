package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import entities.Player;
import objects.ObjectManager;
import levels.LevelManager;
import main.Game;

import utilz.LoadSave;
import utilz.MusicMethods;

import static utilz.Constants.Environment.*;


public class Playing extends State implements Statemethods {
    private Player player;
    private ObjectManager objectManager;
    private LevelManager levelManager;

    private int xLvlOffset;
    private int leftBorder = (int) (0.3 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.6 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
    private BufferedImage backgroundImg, bigCloud, smallCloud, bigRock, smallTerrain;
    private boolean paused = false, gameOver = false;

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallTerrain = LoadSave.GetSpriteAtlas(LoadSave.SMALL_TERRAIN);
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        objectManager = new ObjectManager(this);

        player = new Player(100 * Game.SCALE, 250 * Game.SCALE, (int) (64 * Game.SCALE), (int) (64 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());

    }

    @Override
    public void update() {
            levelManager.update();
            objectManager.update();
            player.update();
            checkCloseToBorder();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        drawClouds(g);
        
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);

        if (paused){
            g.setColor(new Color(0,0,0,100));
            g.fillRect(0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        }
    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 11; i++)
            g.drawImage(bigCloud, i * BG_ELEMENTS_WIDTH - (int) (xLvlOffset * 0.3), 0, BG_ELEMENTS_WIDTH, BG_ELEMENTS_HEIGHT, null);
        for (int i = 0; i < 11; i++)
            g.drawImage(smallCloud, i * BG_ELEMENTS_WIDTH - (int) (xLvlOffset * 0.7), 0, BG_ELEMENTS_WIDTH, BG_ELEMENTS_HEIGHT, null);
        for (int i = 0; i < 11; i++)
            g.drawImage(smallTerrain, i * BG_ELEMENTS_WIDTH - (int) (xLvlOffset * 0.45), Game.GAME_HEIGHT - BG_ELEMENTS_HEIGHT, BG_ELEMENTS_WIDTH, BG_ELEMENTS_HEIGHT , null);
    }


    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;

    }

    private void togglePause(){
        player.resetDirBooleans();
        paused = !paused;
    }

    private void toggleGameOver() {
        gameOver = !gameOver;
        if (gameOver){
            resetAll();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!paused && !player.getIsDead()) {
            if (e.getButton() == MouseEvent.BUTTON1)
                player.setAttacking(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!paused && !player.getIsDead()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    player.setLeft(true);
                    MusicMethods.runningSound.play();
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    player.setRight(true);
                    MusicMethods.runningSound.play();
                    break;
                case KeyEvent.VK_SPACE:
                    if (!player.getInAir()) {
                        player.setJump(true);
                        MusicMethods.jumpSound.play();
                    }
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    Gamestate.state = Gamestate.MENU;
                    break;
                case KeyEvent.VK_P:
                    resetAll();
                    break;
                case KeyEvent.VK_E:
                    if (objectManager.getInPortal())
                        Gamestate.state = Gamestate.MENU;
                    break;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            togglePause();
        }
    }

    public void resetAll() {
        paused = false;
        player.resetAll();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!paused && !player.getIsDead()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    player.setLeft(false);
                    MusicMethods.runningSound.stopLoop();
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    player.setRight(false);
                    MusicMethods.runningSound.stopLoop();
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
        }

    }


    public void checkSpikesTouched(Player player) {
        objectManager.checkSpikesTouched(player);
    }
    public void checkWaterTouched(Player player) {
        objectManager.checkWaterTouched(player);
    }
    public void checkPortalTouched(Player player) {
        objectManager.checkPortalTouched(player);
    }
    public void setCheckpoint(float x, float y) {
        player.setCheckpoint(x,y);
    }
    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }
    public ObjectManager getObjectManager() {
        return objectManager;
    }
    public void checkFlagTouched(Player player) {
        objectManager.checkFlagTouched(player);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
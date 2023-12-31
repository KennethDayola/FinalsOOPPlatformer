package main;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import OtherComponents.SaveFinishTimes;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import gamestates.Story;
import utilz.MusicMethods;

import javax.swing.*;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;
    private Story story;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 2f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    public final static int ORIG_WIDTH = 832;
    public final static int ORIG_HEIGHT = 448;

    public Game() {
        initClasses();
        initMusic();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        startBgmTimer();

        startGameLoop();
    }

    private void startBgmTimer() {
        Timer bgmTimer = new Timer(310, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicMethods.bgm.play();
            }
        });
        bgmTimer.setRepeats(false); // Set to non-repeating
        bgmTimer.start();
    }

    private void initMusic() {
        MusicMethods.initMusic();
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
        story = new Story(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (Gamestate.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case STORY:
                playing.update();
                story.update();
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }

    public void render(Graphics g) {
        switch (Gamestate.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case STORY:
                playing.draw(g);
                story.draw(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }

    }

    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING)
            playing.getPlayer().resetDirBooleans();
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Story getStory() {
        return story;
    }

    public void resetGame() {
        SaveFinishTimes.getFinishTimes().add(playing.getMinutes() + "m " + playing.getSeconds() + "s");
        SaveFinishTimes.saveFinishTimes();

        playing.setTime(0);
        playing.getPlayer().resetDirBooleans();
        playing.setCheckpoint(100 * SCALE, 250 * SCALE);
        playing.resetAll();
        playing.getPlayer().setPlayerFacingRight();
        playing.getObjectManager().setPortalTouched(false);
        story.setStoryFlag(1);
        story.setClickCounter(0);

        MusicMethods.bgm.getClip().setMicrosecondPosition(0);
        MusicMethods.bgm.getGameClip().setMicrosecondPosition(0);
        MusicMethods.bgm.play();
    }
}
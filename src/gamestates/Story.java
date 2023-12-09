package gamestates;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Story extends State implements Statemethods{

    private Timer initialDelayTimer;
    private boolean firstDrawIteration = true;
    private BufferedImage textBox;
    private BufferedImage charSprite;
    private int dialogueIndex = 0, clickCounter = 0, clickCondition;
    private Game game;
    private int storyFlag = 1;

    public Story(Game game) {
        super(game);
        this.game = game;
        initImg();
    }

    private void initImg() {
        textBox = LoadSave.GetSpriteAtlas(LoadSave.TEXT_BOX);
    }

    @Override
    public void update() {
        updateClickCondition();
        if (clickCounter == clickCondition){
            Gamestate.state = Gamestate.PLAYING;
            clickCounter = 0;
            if (storyFlag == 1)
                game.getPlaying().setSpawnPlayer(true);
        }
    }

    private void updateClickCondition() {
        switch (storyFlag){
            case 1:
                clickCondition = 5;
                break;
            case 2:
                clickCondition = 3;
                break;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0,0,0,100));
        g.fillRect(0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        if (firstDrawIteration)
            delayedDraw(g);
        else
            g.drawImage(textBox, 0, (int) (300 * Game.SCALE), (int) (Game.ORIG_WIDTH * Game.SCALE), (int) (140 * Game.SCALE), null);
    }

    private void delayedDraw(Graphics g) {
        initialDelayTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.drawImage(textBox, 0, (int) (300 * Game.SCALE), (int) (Game.ORIG_WIDTH * Game.SCALE), (int) (140 * Game.SCALE), null);
                firstDrawIteration = false;
                initialDelayTimer.stop();
            }
        });
        initialDelayTimer.setRepeats(false);
        initialDelayTimer.start();
    }

    public void setStoryFlag(int storyFlag){
        this.storyFlag = storyFlag;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            clickCounter++;

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_L:
                System.out.println("Switching to PLAYING state");

                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

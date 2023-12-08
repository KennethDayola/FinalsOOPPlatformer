package gamestates;

import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Story extends State implements Statemethods{

    private BufferedImage dialogueBubble;
    private BufferedImage charSprite;
    private int dialogueIndex = 0;

    public Story(Game game) {
        super(game);
        initImg();
    }

    private void initImg() {
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

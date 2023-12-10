package gamestates;

import main.Game;
import utilz.LoadSave;
import OtherComponents.Dialogue;
import utilz.MusicMethods;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

public class Story extends State implements Statemethods{

    private Timer textTimer;
    private boolean drawnIntro = false;
    private boolean startText = false;
    private boolean textDisplayComplete = true, firstTextIteration = true;
    private BufferedImage textBox;
    private BufferedImage charSprite;
    private int dialogueIndex = 0, clickCounter = 0, clickCondition;
    private int introOffset = (int) (Game.ORIG_HEIGHT * Game.SCALE);
    private Game game;
    private Dialogue dialogue = new Dialogue();
    private int storyFlag = 1;
    private String currentText = "", textToDisplay;

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
        if (!game.getPlaying().getCompleted()) {
            updateClickCondition();
            if (clickCounter == clickCondition) {
                Gamestate.state = Gamestate.PLAYING;
                clickCounter = 0;
                dialogueIndex = 0;
                firstTextIteration = true;
                startText = false;
                drawnIntro = false;
                if (storyFlag == 1)
                    game.getPlaying().setSpawnPlayer(true);
            }
            if (drawnIntro)
                if (introOffset >= (int) (300 * Game.SCALE)) {
                    introOffset -= 2;
                    startText = false;
                }
            if (introOffset <= (int) (300 * Game.SCALE))
                startText = true;
            if (firstTextIteration) {
                displayText();
            }
        }
    }

    private void updateClickCondition() {
        switch (storyFlag){
            case 1:
                clickCondition = 13;
                break;
            case 2:
                clickCondition = 6;
                break;
            case 3:
                clickCondition = 14;
                break;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (!game.getPlaying().getCompleted()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

            g.drawImage(textBox, 0, introOffset, (int) (Game.ORIG_WIDTH * Game.SCALE), (int) (140 * Game.SCALE), null);
            drawnIntro = true;

            if (startText) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Bradley Hand ITC", Font.BOLD, 40));
                g.drawString("• Criszel", 135, (int) (350 * Game.SCALE));

                g.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
                g.drawString(currentText, 110, (int) (370 * Game.SCALE));
            }
        }
    }

    private void displayText() {
        if (textTimer == null || !textTimer.isRunning()) {
            if (startText) {
                if (textDisplayComplete) {
                    clickCounter++;
                    textDisplayComplete = false;
                    currentText = "";
                    if (!firstTextIteration)
                        MusicMethods.vnClickSound.play();
                    else
                        firstTextIteration = false;
                }
                try {
                    textToDisplay = dialogue.scenes[storyFlag - 1][dialogueIndex];
                } catch (ArrayIndexOutOfBoundsException a) {
                    return;
                }
                startTypewriterEffect(textToDisplay);
                if (dialogueIndex <= dialogue.scenes[storyFlag - 1].length)
                    dialogueIndex++;
            }
        }
    }

    private void startTypewriterEffect(String text) {
        int delay = 5; // Adjust the delay between characters
        textTimer = new Timer(delay, new ActionListener() {
            int index = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < text.length()) {
                    currentText += text.charAt(index);
                    index++;
                } else {
                    textDisplayComplete = true;
                    textTimer.stop();
                }
            }
        });

        textTimer.start();
    }


    public void setStoryFlag(int storyFlag){
        this.storyFlag = storyFlag;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            displayText();
        }
    }

    public void setClickCounter(int clickCounter) {
        this.clickCounter = clickCounter;
    }

    public void setDialogueIndex(int dialogueIndex) {
        this.dialogueIndex = dialogueIndex;
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

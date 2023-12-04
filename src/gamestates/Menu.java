package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;
import utilz.MusicMethods;

import static utilz.Constants.UI.Buttons.*;

public class Menu extends State implements Statemethods {

    private MenuButton[] button = new MenuButton[3];
    private BufferedImage backgroundImg;


    public Menu(Game game) {
        super(game);
        loadBackground();
        loadButtons();


    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
    }

    private void loadButtons() {
        button[0] = new MenuButton((int) (Game.GAME_WIDTH / 2.4), (int) (300 * Game.SCALE), 0, Gamestate.PLAYING, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT, B_DESIREDWIDTH, B_DESIREDHEIGHT, LoadSave.MENU_PLAYBTN);
        button[1] = new MenuButton((int) (Game.GAME_WIDTH / 2.45), (int) (340 * Game.SCALE), 0, Gamestate.OPTIONS, B2_WIDTH_DEFAULT, B2_HEIGHT_DEFAULT, B2_DESIREDWIDTH, B2_DESIREDHEIGHT, LoadSave.MENU_EXITOPTIONSBTN);
        button[2] = new MenuButton((int) (Game.GAME_WIDTH / 2.18), (int) (340 * Game.SCALE), 1, Gamestate.OPTIONS, B2_WIDTH_DEFAULT, B2_HEIGHT_DEFAULT, B2_DESIREDWIDTH, B2_DESIREDHEIGHT, LoadSave.MENU_EXITOPTIONSBTN);
    }

    @Override
    public void update() {
        for (MenuButton mb : button)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0 ,0 , Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        for (MenuButton mb : button)
            mb.draw(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : button) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
                MusicMethods.clickSound.play();
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : button) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed()) {
                    mb.applyGamestate();
                    MusicMethods.bgm.stop();
                    MusicMethods.bgm.playGameMusic();
                }
                break;
            }
        }

        resetButtons();

    }

    private void resetButtons() {
        for (MenuButton mb : button)
            mb.resetBools();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        boolean anyButtonHovered = false;

        for (MenuButton mb : button) {
            boolean wasHovered = mb.isMouseOver(); // Remember the previous state

            mb.setMouseOver(isIn(e, mb));
            anyButtonHovered = anyButtonHovered || isIn(e, mb);

            if (mb.isMouseOver() && !wasHovered) {
                if (!MusicMethods.hoverSound.getMusicPlayed()) {
                    MusicMethods.hoverSound.play();
                }
            }
        }
        if (!anyButtonHovered) {
            MusicMethods.hoverSound.setMusicPlayed(false);
        }
    }



    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            Gamestate.state = Gamestate.PLAYING;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
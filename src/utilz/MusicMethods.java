package utilz;

import javax.sound.sampled.*;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

public class MusicMethods {

    private Clip clip;
    private Clip gameClip;
    private boolean musicPlayed = false;
    private String loadedFilePath;
    private boolean isLooping = false;

    public static MusicMethods bgm = new MusicMethods();
    public static MusicMethods hoverSound = new MusicMethods();
    public static MusicMethods clickSound = new MusicMethods();
    public static MusicMethods jumpSound = new MusicMethods();
    public static MusicMethods deathSound = new MusicMethods();
    public static MusicMethods checkpointSound = new MusicMethods();
    public static MusicMethods runningSound = new MusicMethods();

    public static final String MENU_MUSIC = "/res/InDreamlandbyChillpeach.wav";
    public static final String GAME_MUSIC = "/res/game_music.wav";
    public static final String HOVER_MUSIC = "/res/Retro12.wav";
    public static final String CLICK_MUSIC = "/res/Retro1.wav";
    public static final String JUMP_SOUND = "/res/jump.wav";
    public static final String WATER_DEATH_SOUND = "/res/waterSplash.wav";
    public static final String CHECKPOINT_SOUND = "/res/checkpointSound.wav";
    public static final String RUNNING_SOUND = "/res/runningSound.wav";

    public static void initMusic(){
        MusicMethods.clickSound.loadMusic(MusicMethods.CLICK_MUSIC);
        MusicMethods.hoverSound.loadMusic(MusicMethods.HOVER_MUSIC);
        MusicMethods.jumpSound.loadMusic(MusicMethods.JUMP_SOUND);
        MusicMethods.deathSound.loadMusic(MusicMethods.WATER_DEATH_SOUND);
        MusicMethods.checkpointSound.loadMusic(MusicMethods.CHECKPOINT_SOUND);
        MusicMethods.runningSound.loadMusic(MusicMethods.RUNNING_SOUND);
        MusicMethods.bgm.loadMusic(MusicMethods.MENU_MUSIC);
        MusicMethods.bgm.loadGameMusic();
    }

    public void loadMusic(String filePath) {
        try {
                AudioInputStream audioInputStream = getAudioInputStream(Objects.requireNonNull(MusicMethods.class.getResource(filePath)));
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                loadedFilePath = filePath;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (!musicPlayed){
            musicPlayed = true;
            if (clip.isRunning()){
                clip.setMicrosecondPosition(0);
            }
            if (loadedFilePath.equals(MENU_MUSIC)) {
                if (loadedFilePath.equals(RUNNING_SOUND))
                    setVolume(0.7f);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else if (loadedFilePath.equals(RUNNING_SOUND)){
                setVolume(0.7f);
            }
            else if (loadedFilePath.equals(HOVER_MUSIC)){
                clip.stop();
                clip.setMicrosecondPosition(0);
                clip.start();
            }
            else {
                if (loadedFilePath.equals(CHECKPOINT_SOUND))
                    setVolume(0.8f);
                clip.stop();
                clip.setMicrosecondPosition(0);
                clip.start();
                musicPlayed = false;
            }
        }
    }


    public void loadGameMusic(){
        try {
            AudioInputStream audioInputStream = getAudioInputStream(Objects.requireNonNull(MusicMethods.class.getResource(GAME_MUSIC)));
            gameClip = AudioSystem.getClip();
            gameClip.open(audioInputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playGameMusic() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameClip.loop(Clip.LOOP_CONTINUOUSLY);

                timer.cancel();
            }
        }, 1300);
    }

    public void stop() {
        clip.stop();
        clip.close();
        musicPlayed = false;
    }

    public void stopLoop() {
        clip.stop();
        musicPlayed = false;
    }

    public void stopGameMusic(){
        gameClip.stop();
        gameClip.close();
    }

    public void setMusicPlayed(boolean isLoaded) {
        this.musicPlayed = isLoaded;
    }

    private void setVolume(float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

    public boolean getMusicPlayed() {
        return musicPlayed;
    }
}
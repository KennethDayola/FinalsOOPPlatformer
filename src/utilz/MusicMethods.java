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
    public static MusicMethods spikeHit = new MusicMethods();
    public static MusicMethods clickSound = new MusicMethods();
    public static MusicMethods vnClickSound = new MusicMethods();
    public static MusicMethods jumpSound = new MusicMethods();
    public static MusicMethods waterDeathSound = new MusicMethods();
    public static MusicMethods checkpointSound = new MusicMethods();
    public static MusicMethods runningSound = new MusicMethods();
    public static MusicMethods successSound = new MusicMethods();
    public static MusicMethods portalSound = new MusicMethods();
    public static MusicMethods fireDeathSound = new MusicMethods();

    public static final String MENU_MUSIC = "/res/menuMusic.wav";
    public static final String GAME_MUSIC = "/res/game_music.wav";
    public static final String HOVER_MUSIC = "/res/Retro12.wav";
    public static final String CLICK_MUSIC = "/res/Retro1.wav";
    public static final String VN_CLICK_SOUND = "/res/clickMusic.wav";
    public static final String JUMP_SOUND = "/res/jump.wav";
    public static final String WATER_DEATH_SOUND = "/res/waterSplash.wav";
    public static final String CHECKPOINT_SOUND = "/res/checkpointSound.wav";
    public static final String RUNNING_SOUND = "/res/runningSound.wav";
    public static final String SPIKE_HIT = "/res/spikeHit.wav";
    public static final String SUCCESS_SOUND = "/res/successSound.wav";
    public static final String PORTAL_SOUND = "/res/portalSound.wav";
    public static final String FIRE_DEATH_SOUND = "/res/fireDeath.wav";

    public static void initMusic(){
        MusicMethods.clickSound.loadMusic(MusicMethods.CLICK_MUSIC);
        MusicMethods.vnClickSound.loadMusic(MusicMethods.VN_CLICK_SOUND);
        MusicMethods.hoverSound.loadMusic(MusicMethods.HOVER_MUSIC);
        MusicMethods.jumpSound.loadMusic(MusicMethods.JUMP_SOUND);
        MusicMethods.waterDeathSound.loadMusic(MusicMethods.WATER_DEATH_SOUND);
        MusicMethods.checkpointSound.loadMusic(MusicMethods.CHECKPOINT_SOUND);
        MusicMethods.runningSound.loadMusic(MusicMethods.RUNNING_SOUND);
        MusicMethods.spikeHit.loadMusic(MusicMethods.SPIKE_HIT);
        MusicMethods.successSound.loadMusic(MusicMethods.SUCCESS_SOUND);
        MusicMethods.portalSound.loadMusic(MusicMethods.PORTAL_SOUND);
        MusicMethods.fireDeathSound.loadMusic(MusicMethods.FIRE_DEATH_SOUND);
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
            clip.setMicrosecondPosition(0);
            switch (loadedFilePath) {
                case MENU_MUSIC -> {
                    setVolume(0.85f, clip);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                case RUNNING_SOUND -> clip.loop(Clip.LOOP_CONTINUOUSLY);
                case HOVER_MUSIC -> {
                    clip.stop();
                    clip.setMicrosecondPosition(0);
                    clip.start();
                }
                default -> {
                    if (loadedFilePath.equals(CHECKPOINT_SOUND))
                        setVolume(0.8f, clip);
                    clip.stop();
                    clip.setMicrosecondPosition(0);
                    clip.start();
                    musicPlayed = false;
                }
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
                setVolume(0.78f, gameClip);
                gameClip.loop(Clip.LOOP_CONTINUOUSLY);
                timer.cancel();
            }
        }, 1300);
    }

    public void stop() {
        clip.stop();
        musicPlayed = false;
    }

    public void stopLoop() {
        clip.stop();
        musicPlayed = false;
    }

    public void stopGameMusic(){
        gameClip.stop();
    }

    public void setMusicPlayed(boolean isLoaded) {
        this.musicPlayed = isLoaded;
    }

    private void setVolume(float volume, Clip clip) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

    public Clip getClip() {
        return clip;
    }

    public Clip getGameClip() {
        return gameClip;
    }

    public boolean getMusicPlayed() {
        return musicPlayed;
    }
}
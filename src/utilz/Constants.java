package utilz;
import main.Game;
public class Constants {

    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final int ANI_SPEED = 25;

    public static class ObjectConstants {

        public static final int WATER_BASE = 0;
        public static final int WATER_TOP1 = 1, WATER_TOP2 = 2, WATER_TOP3 = 3;

        public static final int WATER_DIMENSIONS_DEFAULT = 32;
        public static final int WATER_DIMENSIONS = (int) (WATER_DIMENSIONS_DEFAULT * Game.SCALE);

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case WATER_TOP1:
                case WATER_TOP2:
                case WATER_TOP3:
                    return 4;
                case WATER_BASE:
                    return 1;
            }
            return 1;
        }
    }
    public static class Environment {
        public static final int BG_ELEMENTS_WIDTH_DEFAULT = 576;
        public static final int BG_ELEMENTS_HEIGHT_DEFAULT = 324;

        public static final int BG_ELEMENTS_WIDTH = (int) (BG_ELEMENTS_WIDTH_DEFAULT * Game.SCALE);
        public static final int BG_ELEMENTS_HEIGHT = (int) (BG_ELEMENTS_HEIGHT_DEFAULT * Game.SCALE);

    }


    public static class UI {
        public static class Buttons {

            //B1 = PlayButton   B2 = Exit and Options Button
            public static final int B_WIDTH_DEFAULT = 240;
            public static final int B_HEIGHT_DEFAULT = 130;
            public static final int B_DESIREDWIDTH = (int) (64 * Game.SCALE);
            public static final int B_DESIREDHEIGHT = (int) (35 * Game.SCALE);

            public static final int B2_WIDTH_DEFAULT = 144;
            public static final int B2_HEIGHT_DEFAULT = 130;
            public static final int B2_DESIREDWIDTH = (int) (39 * Game.SCALE);
            public static final int B2_DESIREDHEIGHT = (int) (35 * Game.SCALE);
        }
    }
    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int DEATH = 4;

        public static int GetSpriteAmount (int player_action){
            switch (player_action) {
                case RUNNING:
                    return 7;
                case IDLE:
                    return 21;
                case JUMP:
                    return 3;
                case FALLING:
                    return 2;
                case DEATH:
                    return 5;
            }
            return player_action;
        }
    }


}

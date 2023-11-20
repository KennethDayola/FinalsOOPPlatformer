package utilz;
import main.Game;
public class Constants {

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
            }
            return player_action;
        }
    }


}

package utils;

public class Constants  {

    public static class Directions{
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
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK_ONE = 6;
        public static final int JUMP_ATTACK = 7;
        public static final int JUMP_ATTACK_TWO = 8;

        public static int getSpriteAmount(int player_action){
            switch (player_action){
                case IDLE:
                    return 0;
                case RUNNING:
                    return 1;

                case HIT:
                    return 4;
                case JUMP:
                case ATTACK_ONE:
                case JUMP_ATTACK:
                case JUMP_ATTACK_TWO:
                    return 3;
                case GROUND:
                    return 2;
                case FALLING:
                default:
                    return 1;
            }
        }

    }
}

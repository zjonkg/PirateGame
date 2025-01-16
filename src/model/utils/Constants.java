package model.utils;

import view.main.Game;

public class Constants {

    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final int ANI_SPEED = 25;

    public static class JFXConstants {

        public static final int TRAIL_WIDTH_DEFAULT = 14;
        public static final int TRAIL_HEIGHT_DEFAULT = 10;

        public static final int TRAIL_FALL_WIDTH_DEFAULT = 72;
        public static final int TRAIL_FALL_HEIGHT_DEFAULT = 9;

        public static final int TRAIL_JUMPING_WIDTH_DEFAULT = 40;
        public static final int TRAIL_JUMPING_HEIGHT_DEFAULT = 28;

        public static final int TRAIL_FALL_WIDTH = (int) (TRAIL_FALL_WIDTH_DEFAULT * Game.SCALE);
        public static final int TRAIL_FALL_HEIGHT = (int) (TRAIL_FALL_HEIGHT_DEFAULT * Game.SCALE);

        public static final int TRAIL_JUMPING_WIDTH = (int) (TRAIL_JUMPING_WIDTH_DEFAULT * Game.SCALE);
        public static final int TRAIL_JUMPING_HEIGHT = (int) (TRAIL_JUMPING_HEIGHT_DEFAULT * Game.SCALE);

        public static final int TRAIL_WIDTH = (int) (TRAIL_WIDTH_DEFAULT * Game.SCALE);
        public static final int TRAIL_HEIGHT = (int) (TRAIL_HEIGHT_DEFAULT * Game.SCALE);
    }

    public static class ObjectConstants {

        public static final int RED_POTION = 0;
        public static final int BLUE_POTION = 1;
        public static final int BARREL = 2;
        public static final int BOX = 3;
        public static final int SPIKE = 4;
        public static final int SMALL_CHAIN = 5;
        public static final int LARGE_CHAIN = 6;
        public static final int CANDLE = 7;
        public static final int CANDLE_LIGHT = 8;

        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;

        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
        public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
        public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

        public static final int SMALL_CHAIN_WIDTH_DEFAULT = 9;
        public static final int SMALL_CHAIN_HEIGHT_DEFAULT = 52;

        public static final int LARGE_CHAIN_WIDTH_DEFAULT = 9;
        public static final int LARGE_CHAIN_HEIGHT_DEFAULT = 100;

        public static final int CANDLE_WIDTH_DEFAULT = 10;
        public static final int CANDLE_HEIGHT_DEFAULT = 22;
        public static final int CANDLE_LIGHT_WIDTH_DEFAULT = 35;
        public static final int CANDLE_LIGHT_HEIGHT_DEFAULT = 30;

        public static final int SMALL_CHAIN_WIDTH = (int) (Game.SCALE * SMALL_CHAIN_WIDTH_DEFAULT);
        public static final int SMALL_CHAIN_HEIGHT = (int) (Game.SCALE * SMALL_CHAIN_HEIGHT_DEFAULT);

        public static final int LARGE_CHAIN_WIDTH = (int) (Game.SCALE * LARGE_CHAIN_WIDTH_DEFAULT);
        public static final int LARGE_CHAIN_HEIGHT = (int) (Game.SCALE * LARGE_CHAIN_HEIGHT_DEFAULT);

        public static final int CANDLE_WIDTH = (int) (Game.SCALE * CANDLE_WIDTH_DEFAULT);
        public static final int CANDLE_HEIGHT = (int) (Game.SCALE * CANDLE_HEIGHT_DEFAULT);
        public static final int CANDLE_LIGHT_WIDTH = (int) (Game.SCALE * CANDLE_LIGHT_WIDTH_DEFAULT);
        public static final int CANDLE_LIGHT_HEIGHT = (int) (Game.SCALE * CANDLE_LIGHT_HEIGHT_DEFAULT);

        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int) (Game.SCALE * SPIKE_WIDTH_DEFAULT);
        public static final int SPIKE_HEIGHT = (int) (Game.SCALE * SPIKE_HEIGHT_DEFAULT);

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case CANDLE_LIGHT:
                    return 5;
                case RED_POTION, BLUE_POTION:
                    return 7;
                case BARREL, BOX, CANDLE:
                    return 8;
                case SMALL_CHAIN, LARGE_CHAIN:
                    return 10;
            }
            return 1;
        }
    }

    public static class EnemyConstants {

        public static final int CRABBY = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public static final int CRABBY_HEIGHT_DEFAULT = 32;

        public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
        public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);

        public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {

            switch (enemy_type) {
                case CRABBY:
                    switch (enemy_state) {
                        case IDLE:
                            return 9;
                        case RUNNING:
                            return 6;
                        case ATTACK:
                            return 7;
                        case HIT:
                            return 4;
                        case DEAD:
                            return 5;
                    }
            }

            return 0;

        }

        public static int GetMaxHealth(int enemy_type) {
            switch (enemy_type) {
                case CRABBY:
                    return 10;
                default:
                    return 1;
            }
        }

        public static int GetEnemyDmg(int enemy_type) {
            switch (enemy_type) {
                case CRABBY:
                    return 15;
                default:
                    return 0;
            }

        }

    }

    public static class UI {

        public static class Buttons {

            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class PauseButtons {

            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class URMButtons {

            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

        }

        public static class VolumeButtons {

            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
        }
    }

    public static class Directions {

        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int ATTACK = 5;
        public static final int HIT = 6;
        public static final int DEAD = 7;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case DEAD, ATTACK:
                    return 8;
                case RUNNING:
                    return 14;
                case IDLE:
                    return 24;
                case HIT:
                    return 4;
                case JUMP, GROUND:
                    return 3;
                case FALLING:
                default:
                    return 1;
            }
        }
    }

    public static class BombConstants {

        public static final int NORMAL = 0;
        public static final int PREPARING = 1;
        public static final int EXPLODING = 2;

        public static int GetSpriteAmount(int bomb_action) {
            switch (bomb_action) {
                case NORMAL:
                    return 1;
                case EXPLODING:
                    return 9;
                case PREPARING:
                    return 10;
                default:
                    return 1;
            }
        }
    }

}

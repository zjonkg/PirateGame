package model.entities;

import static model.utils.Constants.PlayerConstants.*;
import static model.utils.HelpMethods.*;
import static model.utils.Constants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import controller.gamestates.Playing;
import java.util.ArrayList;
import model.jfx.Trail;
import model.jfx.TrailFall;
import model.jfx.TrailJump;
import view.main.Game;
import model.utils.LoadSave;

/**
 *
 * @author Usuario
 */
public class Player extends Entity {

    private float distanceTraveled = 0f;
    private static final float TRAIL_DISTANCE_THRESHOLD = 125f;
    private float lastX = x;

    private ArrayList<Bomb> bombs = new ArrayList<>();
    private final int bombCooldown = 120; // Tiempo de espera entre lanzamientos (en ticks).
    private int bombCooldownTick = 0;


    private boolean lastDirectionRight = true;
    private BufferedImage[][] animations;
    private boolean moving = false, attacking = false;
    private boolean left, right, jump;
    private int[][] lvlData;
    private final float xDrawOffset = 12 * Game.SCALE;
    private final float yDrawOffset = 11 * Game.SCALE;

    // Jumping / Gravity
    private final float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.1f * Game.SCALE;

    // StatusBarUI
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);
    private ArrayList<Trail> trails = new ArrayList<>();
    private ArrayList<TrailFall> trailFall = new ArrayList<>();
    private ArrayList<TrailJump> trailJumping = new ArrayList<>();

    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);
    private int healthWidth = healthBarWidth;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;
    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = 35;
        this.walkSpeed = Game.SCALE * 0.8f;
        loadAnimations();
        initHitbox(20, 27);
        initAttackBox();
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
    }

    public void throwBomb(float xSpeed, float ySpeed) {
        if (bombCooldownTick >= bombCooldown) {

            Bomb bomb = new Bomb(
                    hitbox.x, 
                    hitbox.y, 
                    xSpeed, ySpeed, 
                    98, 108, 
                    playing.getLvlData(), 
                    playing // 
            );

            bombs.add(bomb); 
            bombCooldownTick = 0; 
        }
    }

    public void update() {
        updateHealthBar();

        if (currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }

        updateAttackBox();
        updatePos();
        if (moving) {
            checkPotionTouched();
            checkSpikesTouched();
        }

        if (bombCooldownTick < bombCooldown) {
            bombCooldownTick++;
        }

        // Actualizar todas las bombas activas.
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            bomb.update();

            
            if (bomb.isExploded()) {
                bombs.remove(i);
                i--; 
            }
        }

        updateAnimationTick();
        setAnimation();

        if (moving && state == RUNNING) {
            generateTrail();
        }

        for (int i = 0; i < trails.size(); i++) {
            Trail trail = trails.get(i);
            trail.update();
            if (!trail.isActive()) {
                trails.remove(i);
                i--;
            }
        }

        for (int i = 0; i < trailFall.size(); i++) {
            TrailFall trailsFall = trailFall.get(i);
            trailsFall.update();
            if (!trailsFall.isActive()) {
                trailFall.remove(i);
                i--;
            }
        }

        for (int i = 0; i < trailJumping.size(); i++) {
            TrailJump trailsJump = trailJumping.get(i);
            trailsJump.update();
            if (!trailsJump.isActive()) {
                trailJumping.remove(i);
                i--;
            }
        }

    }

    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);

    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitbox);
    }


    private void updateAttackBox() {
        if (right) {
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
        }

        attackBox.y = hitbox.y + (Game.SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    public void render(Graphics g, int lvlOffset) {
        g.drawImage(animations[state][aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
		drawHitbox(g, lvlOffset);
//		drawAttackBox(g, lvlOffset);

        drawUI(g);

        for (Trail trail : trails) {
            trail.render(g);
        }

        for (TrailFall trail : trailFall) {
            trail.render(g);
        }

        for (TrailJump trail : trailJumping) {
            trail.render(g);
        }

        for (Bomb bomb : bombs) {
            bomb.render(g);
        }
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(state)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = state;

        if (state == GROUND) {
            if (aniIndex >= GetSpriteAmount(GROUND) - 1) {

                state = IDLE;
            }
            return;
        }

        if (moving) {
            state = RUNNING;
        } else {
            state = IDLE;
        }

        if (inAir) {
            if (airSpeed < 0) {
                state = JUMP;
            } else {
                state = FALLING;
            }
        }

        if (attacking) {
            state = ATTACK;
            if (startAni != ATTACK) {
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }

        if (startAni != state) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;

        if (jump) {
            jump();
        }

        if (!inAir) {
            if ((!left && !right) || (right && left)) {
                return;
            }
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= walkSpeed;
            flipX = width;
            flipW = -1;

            
            if (lastDirectionRight) {
                generateTrail();
                lastDirectionRight = false;
            }
        }

        if (right) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;

            if (!lastDirectionRight) {
                generateTrail();
                lastDirectionRight = true;
            }
        }

        if (!inAir) {
            if (!IsEntityOnFloor(hitbox, lvlData)) {
                inAir = true;

            }
        }

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
                updateXPos(xSpeed);

            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                    generateFallTrail();

                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }

        } else {
            updateXPos(xSpeed);
        }
        moving = true;
    }

    private void generateFallTrail() {
        trailFall.add(new TrailFall(getX() - xDrawOffset, getY() - yDrawOffset, flipW));
    }

    private void generateJumpTrail() {
        trailJumping.add(new TrailJump(getX() - xDrawOffset, getY() - yDrawOffset, flipW));
    }

    private void generateTrail() {

        float currentX = getX();

        distanceTraveled += Math.abs(currentX - lastX);

        if (distanceTraveled >= TRAIL_DISTANCE_THRESHOLD) {
            trails.add(new Trail(getX() - xDrawOffset, getY() - yDrawOffset, flipW));
            distanceTraveled = 0f;
        }

        lastX = currentX;
    }

    private void jump() {
        if (inAir) {
            return;

        }

        inAir = true;
        generateJumpTrail();
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
        state = GROUND;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    public void changeHealth(int value) {
        currentHealth += value;

        if (currentHealth <= 0) {
            currentHealth = 0;
        } else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public void kill() {
        currentHealth = 0;
    }

    public void changePower(int value) {
        System.out.println("Added power!");
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[6][26];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 58, j * 58, 58, 58);
            }
        }

        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
    }


    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        state = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        if (!IsEntityOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
    }
    
    public boolean isLastDirectionRight() {
    return lastDirectionRight;
    }


}

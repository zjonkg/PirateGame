package model.entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import controller.gamestates.Playing;
import java.awt.image.BufferedImage;
import view.main.Game;
import model.utils.Constants.BombConstants;
import model.utils.HelpMethods;
import static model.utils.HelpMethods.CanMoveHere;
import model.utils.LoadSave;

public class Bomb extends Entity {

    private float xSpeed, ySpeed;
    private int preparingTime = 500; 
    private int preparingTick = 0;

    private boolean inAir = true;
    private static final float GRAVITY = 0.05f * Game.SCALE;  
    private static final float EXPLOSION_RADIUS = 50 * Game.SCALE;
    private static final int EXPLOSION_DAMAGE = 50;

    private float xDrawOffset = 30 * Game.SCALE;
    private float yDrawOffset = 22 * Game.SCALE;

    private int state = BombConstants.NORMAL;
    private int aniTick = 0;
    private int aniIndex = 0;
    private int aniSpeed = 10;


    private BufferedImage[][] animations;

    private int[][] lvlData;
    private boolean exploded = false;

    private Playing playing;

    public Bomb(float x, float y, float xSpeed, float ySpeed, int width, int height, int[][] lvlData, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.lvlData = lvlData;
        this.inAir = true; 
        initHitbox(20, 20);
        loadAnimations();
    }

    private void updateAnimation() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;

            if (aniIndex >= BombConstants.GetSpriteAmount(state)) {
                aniIndex = 0;
                if (state == BombConstants.EXPLODING) {
                    exploded = true;
                }
            }
        }
    }

    public void update() {
        if (exploded) {
            return;  
        }

        updatePos();  

        if (state != BombConstants.NORMAL) {
            updateAnimation(); 
        }

      
        if (isTouchingFloor() && state != BombConstants.EXPLODING) {
            if (state != BombConstants.PREPARING) {
                state = BombConstants.PREPARING;  
                aniIndex = 0; 
                preparingTick = 0;
            }
        }

        
        if (state == BombConstants.PREPARING) {
            preparingTick++;  
            if (preparingTick >= preparingTime) {
                state = BombConstants.EXPLODING;
                aniIndex = 0;  
                applyDamageToEntities();
            }
        }
    }

    public void updatePos() {

        ySpeed += GRAVITY;

        if (isTouchingFloor()) {
            ySpeed = 0;
            resetInAir();
            xSpeed = 0;

        }

       
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;  // Mover horizontalmente si no hay bloque sólido
        } else {
          
            xSpeed = 0;
            if (inAir) {
                state = BombConstants.PREPARING;
            }
        }

        
        if (CanMoveHere(hitbox.x, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += ySpeed;  
        } else {
            
            ySpeed = 0;
            if (inAir) {
                resetInAir();  
            }
        }
    }

// Verifica si la bomba está tocando el suelo
    private boolean isTouchingFloor() {
        return HelpMethods.IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, playing.getLvlData())
                || HelpMethods.IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, playing.getLvlData());
    }

    private void resetInAir() {
        inAir = false;   
        ySpeed = 0;       
        if (state != BombConstants.PREPARING) {
            preparingTick = 0; 
        }
    }


    private void applyDamageToEntities() {
        Rectangle2D.Float explosionArea = new Rectangle2D.Float(
                hitbox.x - EXPLOSION_RADIUS / 2,
                hitbox.y - EXPLOSION_RADIUS / 2,
                EXPLOSION_RADIUS,
                EXPLOSION_RADIUS
        );
        playing.checkObjectHit(explosionArea);
        playing.getEnemyManager().checkExplosionDamage(explosionArea);
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.BOMB_ATLAS);
        animations = new BufferedImage[3][10]; 
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 96, j * 108, 96, 108);
            }
        }
    }

    public void render(Graphics g) {
        if (exploded) {
            return; 
        }

        BufferedImage currentFrame = animations[state][aniIndex];
        g.drawImage(currentFrame, (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);

        drawHitbox(g, 30);
    }

    public boolean isExploded() {
        return exploded;
    }
}

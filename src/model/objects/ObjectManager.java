package model.objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.entities.Player;
import controller.gamestates.Playing;
import controller.levels.Level;
import model.utils.LoadSave;
import static model.utils.Constants.ObjectConstants.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] potionImgs, containerImgs;
    private BufferedImage[] smallChainedImgs, largeChainedImgs, candleImgs, candleLightImgs;
    private BufferedImage spikeImg;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<SmallChain> smallChain;
    private ArrayList<LargeChain> largeChain;
    private ArrayList<Candle> candle;
    private ArrayList<CandleLight> candleLight;
    

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }

    public void checkSpikesTouched(Player p) {
        for (Spike s : spikes) {
            if (s.getHitbox().intersects(p.getHitbox())) {
                p.kill();
            }
        }
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Potion p : potions) {
            if (p.isActive()) {
                if (hitbox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
        }
    }

    public void applyEffectToPlayer(Potion p) {
        if (p.getObjType() == RED_POTION) {
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
        } else {
            playing.getPlayer().changePower(BLUE_POTION_VALUE);
        }
    }

    public void checkObjectHit(Rectangle2D.Float explosionArea) {
        for (GameContainer gc : containers) {
            if (gc.isActive() && !gc.doAnimation) {
                if (gc.getHitbox().intersects(explosionArea)) {
                    gc.setAnimation(true);
                    int type = 0;
                    if (gc.getObjType() == BARREL) {
                        type = 1;
                    }
                    potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
                    return;
                }
            }
        }
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpikes();
        smallChain = newLevel.getSmallChain();
        largeChain = newLevel.getLargeChain();
        candle = newLevel.getCandle();
        candleLight = newLevel.getCandleLight();
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImgs = new BufferedImage[2][7];

        for (int j = 0; j < potionImgs.length; j++) {
            for (int i = 0; i < potionImgs[j].length; i++) {
                potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
            }
        }

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for (int j = 0; j < containerImgs.length; j++) {
            for (int i = 0; i < containerImgs[j].length; i++) {
                containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
            }
        }

        spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);

        BufferedImage smallChainSprite = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CHAIN);
        smallChainedImgs = new BufferedImage[10];

        for (int j = 0; j < smallChainedImgs.length; j++) {
                smallChainedImgs[j] = smallChainSprite.getSubimage(9 * j, 0, 9, 52);
            }
        BufferedImage largeChainSprite = LoadSave.GetSpriteAtlas(LoadSave.LARGE_CHAIN);
        largeChainedImgs = new BufferedImage[10];

        for (int j = 0; j < largeChainedImgs.length; j++) {
                largeChainedImgs[j] = largeChainSprite.getSubimage(9 * j, 0, 9, 100);
            }
        
        BufferedImage candleSprite = LoadSave.GetSpriteAtlas(LoadSave.CANDLE);
        candleImgs = new BufferedImage[8];

        for (int j = 0; j < candleImgs.length; j++) {
                candleImgs[j] = candleSprite.getSubimage(14 * j, 0, 14, 32);
            }
        
        BufferedImage candleLightSprite = LoadSave.GetSpriteAtlas(LoadSave.CANDLE_LIGHT);
        candleLightImgs = new BufferedImage[5];

        for (int j = 0; j < candleLightImgs.length; j++) {
                candleLightImgs[j] = candleLightSprite.getSubimage(60 * j, 0, 60, 58);
            }

    }

    public void update() {
        for (Potion p : potions) {
            if (p.isActive()) {
                p.update();
            }
        }

        for (GameContainer gc : containers) {
            if (gc.isActive()) {
                gc.update();
            }
        }
        
        for (SmallChain t : smallChain) {
            if (t.isActive()) {
                t.update();
            }
        }
        
        for (LargeChain l : largeChain) {
            if (l.isActive()) {
                l.update();
            }
        }
        for (Candle z : candle) {
            if (z.isActive()) {
                z.update();
            }
        }
        for (CandleLight c : candleLight) {
            if (c.isActive()) {
                c.update();
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
        drawSmallChain(g, xLvlOffset);
        drawLargeChain(g, xLvlOffset);
        drawCandle(g, xLvlOffset);
        drawCandleLight(g, xLvlOffset);
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for (Spike s : spikes) {
            g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }

    }

    private void drawContainers(Graphics g, int xLvlOffset) {
        for (GameContainer gc : containers) {
            if (gc.isActive()) {
                int type = 0;
                if (gc.getObjType() == BARREL) {
                    type = 1;
                }
                g.drawImage(containerImgs[type][gc.getAniIndex()], (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset), (int) (gc.getHitbox().y - gc.getyDrawOffset()), CONTAINER_WIDTH,
                        CONTAINER_HEIGHT, null);
            }
        }
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for (Potion p : potions) {
            if (p.isActive()) {
                int type = 0;
                if (p.getObjType() == RED_POTION) {
                    type = 1;
                }
                g.drawImage(potionImgs[type][p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT,
                        null);
            }
        }
    }

    private void drawSmallChain(Graphics g, int xLvlOffset) {
        for (SmallChain p : smallChain) {
            g.drawImage(smallChainedImgs[p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), SMALL_CHAIN_WIDTH, SMALL_CHAIN_HEIGHT,
                    null);
        }

    }

    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Potion p : potions) {
            p.reset();
        }
        for (GameContainer gc : containers) {
            gc.reset();
        }
    }

    private void drawLargeChain(Graphics g, int xLvlOffset) {
        for (LargeChain p : largeChain) {
            g.drawImage(largeChainedImgs[p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), LARGE_CHAIN_WIDTH, LARGE_CHAIN_HEIGHT,
                    null);
        }
    }
    
    private void drawCandle(Graphics g, int xLvlOffset) {
        for (Candle p : candle ) {
            g.drawImage(candleImgs[p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), CANDLE_WIDTH, CANDLE_HEIGHT,
                    null);
        }
    }
    
    private void drawCandleLight(Graphics g, int xLvlOffset) {
        for (CandleLight p : candleLight ) {
            g.drawImage(candleLightImgs[p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), CANDLE_LIGHT_WIDTH, CANDLE_LIGHT_HEIGHT,
                    null);
        }
    }
}

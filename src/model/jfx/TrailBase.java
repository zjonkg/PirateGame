/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.jfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author jongonhur
 */
public abstract class TrailBase {
    protected float x, y;
    protected BufferedImage[] trailFrames;
    protected int frameIndex = 0;
    protected int frameTick = 0;
    protected int maxFrameTick = 6;
    protected boolean active = true;

    protected int width, height;

    public TrailBase(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        loadAnimations();
    }

    protected abstract void loadAnimations(); 

    public void update() {
        frameTick++;
        if (frameTick >= maxFrameTick) {
            frameTick = 0;
            frameIndex++;
            if (frameIndex >= trailFrames.length) {
                active = false;
            }
        }
    }

    public void render(Graphics g) {
        if (active) {
            g.drawImage(trailFrames[frameIndex], 
                        (int) x, 
                        (int) y, 
                        width, height, 
                        null);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}




package model.jfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import model.utils.LoadSave;
import model.utils.Constants;
import static model.utils.Constants.JFXConstants.TRAIL_HEIGHT;
import static model.utils.Constants.JFXConstants.TRAIL_WIDTH;

public class Trail extends TrailBase {

    private int flipW;

    public Trail(float x, float y, int flipW) {
        super(x, y + 60, TRAIL_WIDTH, TRAIL_HEIGHT);
        this.flipW = flipW;
    }

    @Override
    protected void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.RUNNING_PARTICLE);
        trailFrames = new BufferedImage[6];
        for (int j = 0; j < trailFrames.length; j++) {
            trailFrames[j] = img.getSubimage(j * 12, 0, 12, 10);
        }
    }

    @Override
    public void render(Graphics g) {
        if (active) {
            int xOffset = (flipW == 1) ? 10 : (flipW == -1) ? -80 : 0;

            g.drawImage(trailFrames[frameIndex], 
                        (int) (x - xOffset), 
                        (int) y, 
                        width * flipW, height, 
                        null);
        }
    }
}

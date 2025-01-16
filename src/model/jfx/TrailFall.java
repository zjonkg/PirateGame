package model.jfx;

import java.awt.image.BufferedImage;
import model.utils.LoadSave;
import static model.utils.Constants.JFXConstants.TRAIL_FALL_HEIGHT;
import static model.utils.Constants.JFXConstants.TRAIL_FALL_WIDTH;

public class TrailFall extends TrailBase {

    public TrailFall(float x, float y, int flipW) {
        super(x - 30, y + 58, TRAIL_FALL_WIDTH, TRAIL_FALL_HEIGHT);
    }

    @Override
    protected void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.FALLING_PARTICLE);
        trailFrames = new BufferedImage[6];
        for (int j = 0; j < trailFrames.length; j++) {
            trailFrames[j] = img.getSubimage(j * 80, 0, 80, 10);
        }
    }
}

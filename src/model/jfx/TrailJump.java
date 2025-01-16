package model.jfx;

import java.awt.image.BufferedImage;
import model.utils.LoadSave;
import static model.utils.Constants.JFXConstants.TRAIL_JUMPING_HEIGHT;
import static model.utils.Constants.JFXConstants.TRAIL_JUMPING_WIDTH;

public class TrailJump extends TrailBase {

    public TrailJump(float x, float y, int flipW) {
        super(x, y, TRAIL_JUMPING_WIDTH, TRAIL_JUMPING_HEIGHT);
    }

    @Override
    protected void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.JUMPING_PARTICLE);
        trailFrames = new BufferedImage[6];
        for (int j = 0; j < trailFrames.length; j++) {
            trailFrames[j] = img.getSubimage(j * 40, 0, 40, 28);
        }
    }
}

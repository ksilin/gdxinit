package info.silin.gdxinit.renderer.texture;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Avatar.State;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AvatarTexturePack {

	private static final float RUNNING_FRAME_DURATION = 0.06f;

	private TextureRegion bobIdleLeft;
	private TextureRegion bobIdleRight;
	private TextureRegion bobJumpLeft;
	private TextureRegion bobFallLeft;
	private TextureRegion bobJumpRight;
	private TextureRegion bobFallRight;

	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;

	public AvatarTexturePack(TextureAtlas atlas) {
		loadTextures(atlas);
	}

	private void loadTextures(TextureAtlas atlas) {

		bobIdleLeft = atlas.findRegion("bob-01");
		bobIdleRight = new TextureRegion(bobIdleLeft);
		bobIdleRight.flip(true, false);

		TextureRegion[] walkLeftFrames = createWalkLeftFrames(atlas);
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION,
				walkLeftFrames);

		TextureRegion[] walkRightFrames = createWalkRightFrames(walkLeftFrames);
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION,
				walkRightFrames);

		bobJumpLeft = atlas.findRegion("bob-up");
		bobJumpRight = new TextureRegion(bobJumpLeft);
		bobJumpRight.flip(true, false);

		bobFallLeft = atlas.findRegion("bob-down");
		bobFallRight = new TextureRegion(bobFallLeft);
		bobFallRight.flip(true, false);
	}

	private TextureRegion[] createWalkRightFrames(TextureRegion[] walkLeftFrames) {
		TextureRegion[] walkRightFrames = new TextureRegion[5];

		for (int i = 0; i < 5; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		return walkRightFrames;
	}

	private TextureRegion[] createWalkLeftFrames(TextureAtlas atlas) {
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			walkLeftFrames[i] = atlas.findRegion("bob-0" + (i + 2));
		}
		return walkLeftFrames;
	}

	public TextureRegion getAvatarFrame(Avatar avatar) {
		TextureRegion frame = avatar.isFacingLeft() ? bobIdleLeft
				: bobIdleRight;
		if (avatar.getState().equals(State.WALKING)) {
			frame =

			avatar.isFacingLeft() ? walkLeftAnimation.getKeyFrame(
					avatar.getStateTime(), true) : walkRightAnimation
					.getKeyFrame(avatar.getStateTime(), true);
		}
		return frame;
	}

}

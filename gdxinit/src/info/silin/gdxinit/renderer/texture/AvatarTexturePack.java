package info.silin.gdxinit.renderer.texture;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.entity.state.Walking;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AvatarTexturePack {

	private static final float RUNNING_FRAME_DURATION = 0.06f;

	private TextureRegion idleLeft;
	private TextureRegion idleRight;
	private TextureRegion jumpLeft;
	private TextureRegion fallLeft;
	private TextureRegion jumpRight;
	private TextureRegion fallRight;

	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;

	public AvatarTexturePack(TextureAtlas atlas) {
		createTextures(atlas);
	}

	private void createTextures(TextureAtlas atlas) {

		createIdleTextures(atlas);
		createWalkAnimations(atlas);
		createJumpTextures(atlas);
		createFallTextures(atlas);
	}

	private void createIdleTextures(TextureAtlas atlas) {
		idleLeft = atlas.findRegion("bob-01");
		idleRight = new TextureRegion(idleLeft);
		idleRight.flip(true, false);
	}

	private void createWalkAnimations(TextureAtlas atlas) {
		TextureRegion[] walkLeftFrames = createWalkLeftFrames(atlas);
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION,
				walkLeftFrames);

		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION,
				createWalkRightFrames(walkLeftFrames));
	}

	private void createJumpTextures(TextureAtlas atlas) {
		jumpLeft = atlas.findRegion("bob-up");
		jumpRight = new TextureRegion(jumpLeft);
		jumpRight.flip(true, false);
	}

	private void createFallTextures(TextureAtlas atlas) {
		fallLeft = atlas.findRegion("bob-down");
		fallRight = new TextureRegion(fallLeft);
		fallRight.flip(true, false);
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
		TextureRegion frame = avatar.isFacingLeft() ? idleLeft : idleRight;
		State state = avatar.getState();
		if (state.equals(Walking.getINSTANCE())) {
			frame =

			avatar.isFacingLeft() ? walkLeftAnimation.getKeyFrame(
					state.getStateTime(), true) : walkRightAnimation
					.getKeyFrame(state.getStateTime(), true);
		}
		return frame;
	}
}

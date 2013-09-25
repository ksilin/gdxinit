package info.silin.gdxinit.renderer.texture;

import info.silin.gdxinit.entity.Vehicle;
import info.silin.gdxinit.entity.state.State;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AvatarTexturePack {

	private static final float RUNNING_FRAME_DURATION = 0.06f;

	private TextureRegion idleLeft;
	private TextureRegion idleRight;
	private TextureRegion jumpLeft;
	private TextureRegion fallLeft;
	private TextureRegion jumpRight;
	private TextureRegion fallRight;

	private TextureRegion bloodStain;

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
		bloodStain = atlas.findRegion("blood_stain32");
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

	public TextureRegion getWalkFrame(Vehicle vehicle) {

		TextureRegion frame = vehicle.getVelocity().x < 0 ? idleLeft
				: idleRight;
		Vector2 v = vehicle.getVelocity();
		if (v.len2() > 1) {
			State state = vehicle.getState();
			float stateTime = state.getStateTime();
			frame = v.x < 0 ? walkLeftAnimation.getKeyFrame(stateTime, true)
					: walkRightAnimation.getKeyFrame(stateTime, true);
		}
		return frame;
	}

	public TextureRegion getBloodStain() {
		return bloodStain;
	}
}

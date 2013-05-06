package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Bob;
import info.silin.gdxinit.entity.Bob.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DefaultRenderer {

	private int height;
	private int width;
	private World world;

	private static final float RUNNING_FRAME_DURATION = 0.06f;

	private SpriteBatch spriteBatch = new SpriteBatch();

	private TextureRegion bobIdleLeft;
	private TextureRegion bobIdleRight;
	private TextureRegion blockTexture;
	private TextureRegion bobFrame;
	private TextureRegion bobJumpLeft;
	private TextureRegion bobFallLeft;
	private TextureRegion bobJumpRight;
	private TextureRegion bobFallRight;

	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;

	public DefaultRenderer(World world) {
		this.world = world;
		loadTextures();
	}

	private void loadTextures() {

		Gdx.app.log("WorldRenderer", "loading the textures");

		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/textures.atlas"));

		blockTexture = atlas.findRegion("crate");

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

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public void render(Camera cam) {
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		drawBlocks();
		drawBob();
		spriteBatch.end();
	}

	private void drawBlocks() {
		for (Block block : world.getDrawableBlocks(width, height)) {
			drawBlock(block);
		}
	}

	private void drawBlock(Block block) {
		Rectangle rect = block.getBounds();
		Vector2 position = block.getPosition();

		// TODO: why are we shifting here?
		float x1 = position.x + rect.x;
		float y1 = position.y + rect.y;

		spriteBatch.draw(blockTexture, x1, y1, Block.SIZE, Block.SIZE);
	}

	private void drawBob() {
		Bob bob = world.getBob();
		bobFrame = bob.isFacingLeft() ? bobIdleLeft : bobIdleRight;
		if (bob.getState().equals(State.WALKING)) {
			bobFrame = bob.isFacingLeft() ? walkLeftAnimation.getKeyFrame(
					bob.getStateTime(), true) : walkRightAnimation.getKeyFrame(
					bob.getStateTime(), true);
		}
		spriteBatch.draw(bobFrame, bob.getPosition().x, bob.getPosition().y,
				Bob.SIZE, Bob.SIZE);
	}

}

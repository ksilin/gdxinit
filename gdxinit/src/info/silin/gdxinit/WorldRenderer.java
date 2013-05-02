package info.silin.gdxinit;

import info.silin.gdxinit.Bob.State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {

	private World world;

	private OrthographicCamera cam;

	private static final float CAMERA_WIDTH = 16f;
	private static final float CAMERA_HEIGHT = 9f;

	DebugRenderer debugRenderer;

	private SpriteBatch spriteBatch;
	private boolean debug = false;

	private static final float RUNNING_FRAME_DURATION = 0.06f;

	SpriteBatch fontBatch;
	BitmapFont font;
	CharSequence str = "Hello World!";

	/** Textures **/
	private TextureRegion bobIdleLeft;
	private TextureRegion bobIdleRight;
	private TextureRegion blockTexture;
	private TextureRegion bobFrame;
	private TextureRegion bobJumpLeft;
	private TextureRegion bobFallLeft;
	private TextureRegion bobJumpRight;
	private TextureRegion bobFallRight;

	/** Animations **/
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;

	public void setSize(int w, int h) {
	}

	public WorldRenderer(World world, boolean debug) {

		this.world = world;
		this.debug = debug;

		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH * 0.5f, CAMERA_HEIGHT * 0.5f, 0);
		this.cam.update();

		debugRenderer = new DebugRenderer(world);

		spriteBatch = new SpriteBatch();
		loadTextures();
	}

	private void loadTextures() {

		fontBatch = new SpriteBatch();
		font = new BitmapFont(
				Gdx.files.internal("data/DejaVuSansCondensed.fnt"),
				Gdx.files.internal("data/DejaVuSansCondensed.png"), false);

		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/textures.atlas"));
		for (AtlasRegion reg : atlas.getRegions()) {
			System.out.println("index: " + reg.index);
			System.out.println("name: " + reg.name);
			System.out.println("region: " + reg.toString());
		}
		bobIdleLeft = atlas.findRegion("bob-01");
		bobIdleRight = new TextureRegion(bobIdleLeft);
		bobIdleRight.flip(true, false);
		blockTexture = atlas.findRegion("crate");
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			walkLeftFrames[i] = atlas.findRegion("bob-0" + (i + 2));
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION,
				walkLeftFrames);

		TextureRegion[] walkRightFrames = new TextureRegion[5];

		for (int i = 0; i < 5; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION,
				walkRightFrames);

		bobJumpLeft = atlas.findRegion("bob-up");
		bobJumpRight = new TextureRegion(bobJumpLeft);
		bobJumpRight.flip(true, false);
		bobFallLeft = atlas.findRegion("bob-down");
		bobFallRight = new TextureRegion(bobFallLeft);
		bobFallRight.flip(true, false);
	}

	public void render() {

		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		drawBlocks();
		drawBob();
		spriteBatch.end();

		if (debug) {
			debugRenderer.render(cam);
		}

		fontBatch.begin();
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(fontBatch, str, 25, 160);
		fontBatch.end();
	}

	private void drawBlocks() {
		for (Block block : world.getBlocks()) {
			Rectangle rect = block.getBounds();

			// TODO: why are we shifting here?
			float x1 = block.getPosition().x + rect.x;
			float y1 = block.getPosition().y + rect.y;

			spriteBatch.draw(blockTexture, x1, y1, Block.SIZE, Block.SIZE);
		}
	}

	private void drawBob() {
		Bob bob = world.getBob();
		bobFrame = bob.isFacingLeft() ? bobIdleLeft : bobIdleRight;
		if (bob.getState().equals(State.WALKING)) {
			bobFrame = bob.isFacingLeft() ? walkLeftAnimation.getKeyFrame(
					bob.getStateTime(), true) : walkRightAnimation.getKeyFrame(
					bob.getStateTime(), true);
		} else if (bob.getState().equals(State.JUMPING)) {
			if (bob.getVelocity().y > 0) {
				bobFrame = bob.isFacingLeft() ? bobJumpLeft : bobJumpRight;
			} else {
				bobFrame = bob.isFacingLeft() ? bobFallLeft : bobFallRight;
			}
		}
		spriteBatch.draw(bobFrame, bob.getPosition().x, bob.getPosition().y,
				Bob.SIZE, Bob.SIZE);
	}
}

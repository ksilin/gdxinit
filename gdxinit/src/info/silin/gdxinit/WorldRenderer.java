package info.silin.gdxinit;

import info.silin.gdxinit.Bob.State;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Region;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {

	private World world;

	private OrthographicCamera cam;

	private static final float CAMERA_WIDTH = 16f;
	private static final float CAMERA_HEIGHT = 9f;

	ShapeRenderer debugRenderer = new ShapeRenderer();

	private SpriteBatch spriteBatch;
	private boolean debug = false;

	private static final float RUNNING_FRAME_DURATION = 0.06f;

	/** Textures **/
	private TextureRegion bobIdleLeft;
	private TextureRegion bobIdleRight;
	private TextureRegion blockTexture;
	private TextureRegion bobFrame;

	/** Animations **/
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;

	public void setSize(int w, int h) {
	}

	public WorldRenderer(World world, boolean debug) {

		this.world = world;
		this.debug = debug;

		// float w = Gdx.graphics.getWidth();
		// float h = Gdx.graphics.getHeight();

		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH * 0.5f, CAMERA_HEIGHT * 0.5f, 0);
		this.cam.update();

		spriteBatch = new SpriteBatch();
		loadTextures();
	}

	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/textures.atlas"));
		for (AtlasRegion reg : atlas.getRegions()) {
			System.out.println("index: " + reg.index);
			System.out.println("name: " + reg.name);
			System.out.println("region: " + reg.toString());
		}
		bobIdleLeft = atlas.findRegion("images/bob-01");
		bobIdleRight = new TextureRegion(bobIdleLeft);
		bobIdleRight.flip(true, false);
		blockTexture = atlas.findRegion("images/crate");
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			walkLeftFrames[i] = atlas.findRegion("images/bob-0" + (i + 2));
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
	}

	public void render() {

		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		drawBlocks();
		drawBob();
		spriteBatch.end();

		if (debug) {
			debugRender();
		}
	}

	private void debugRender() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Rectangle);
		for (Block block : world.getBlocks()) {
			Rectangle rect = block.getBounds();

			// TODO: why are we shifting here?
			float x1 = block.getPosition().x + rect.x;
			float y1 = block.getPosition().y + rect.y;

			debugRenderer.setColor(new Color(1, 0, 0, 1));
			// debugRenderer.end();
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
		// render Bob
		Bob bob = world.getBob();
		Rectangle rect = bob.getBounds();
		float x1 = bob.getPosition().x + rect.x;
		float y1 = bob.getPosition().y + rect.y;
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		debugRenderer.end();
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
		}
		spriteBatch.draw(bobFrame, bob.getPosition().x, bob.getPosition().y,
				Bob.SIZE, Bob.SIZE);
	}
}

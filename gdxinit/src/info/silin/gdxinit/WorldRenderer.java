package info.silin.gdxinit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class WorldRenderer {

	private World world;

	private OrthographicCamera cam;

	private static final float CAMERA_WIDTH = 16f;
	private static final float CAMERA_HEIGHT = 9f;

	ShapeRenderer debugRenderer = new ShapeRenderer();

	private Texture bobTexture;
	private Texture blockTexture;

	private SpriteBatch spriteBatch;
	private boolean debug = false;

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
		bobTexture = new Texture(Gdx.files.internal("images/bob_01.png"));
		blockTexture = new Texture(Gdx.files.internal("images/crate.png"));
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
		spriteBatch.draw(bobTexture, bob.getPosition().x, bob.getPosition().y,
				Bob.SIZE, Bob.SIZE);
	}
}

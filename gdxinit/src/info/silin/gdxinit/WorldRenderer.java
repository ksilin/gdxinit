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

	private static final int CAMERA_WIDTH = 16;
	// is a derivative of the cam width and the viewport size ratio
	private float cameraHeight;

	ShapeRenderer debugRenderer = new ShapeRenderer();

	private Texture bobTexture;
	private Texture blockTexture;

	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float) width / CAMERA_WIDTH;
		ppuY = (float) height / cameraHeight;

		System.out.println("resizing: ");
		System.out.println("width: " + width);
		System.out.println("height: " + height);
		System.out.println("ppuX: " + ppuX);
		System.out.println("ppuY: " + ppuY);
	}

	public WorldRenderer(World world) {

		this.world = world;

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		cameraHeight = CAMERA_WIDTH * (h / w);
		this.cam = new OrthographicCamera(CAMERA_WIDTH, cameraHeight);
		this.cam.position.set(CAMERA_WIDTH * 0.5f, cameraHeight * 0.5f, 0);
		this.cam.update();

		spriteBatch = new SpriteBatch();
		loadTextures();
	}

	private void loadTextures() {
		bobTexture = new Texture(Gdx.files.internal("images/bob_01.png"));
		blockTexture = new Texture(Gdx.files.internal("images/crate.png"));
	}

	public void render() {

		// render blocks
		if (debug) {
			debugRender();
		}

		spriteBatch.begin();
		drawBlocks();
		drawBob();
		spriteBatch.end();
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
			spriteBatch.draw(blockTexture, block.getPosition().x * ppuX,
					block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE
							* ppuY);
		}
	}

	private void drawBob() {
		Bob bob = world.getBob();
		spriteBatch.draw(bobTexture, bob.getPosition().x * ppuX,
				bob.getPosition().y * ppuY, Bob.SIZE * ppuX, Bob.SIZE * ppuY);
	}
}

package info.silin.gdxinit.renderer;

import info.silin.gdxinit.Level;
import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class RendererController {

	private World world;

	private OrthographicCamera cam;

	private static final float CAMERA_WIDTH = 16f;
	private static final float CAMERA_HEIGHT = 9f;

	private DebugRenderer debugRenderer;

	private DefaultRenderer defaultRenderer;
	private CollisionRenderer collisionRenderer;

	// TODO - extract to UIRenderer?
	private Stage stage;
	private Skin skin;

	private boolean debug = false;

	private int height;

	private int width;

	public RendererController(World world, boolean debug) {

		this.world = world;
		this.debug = debug;

		skin = new Skin(Gdx.files.internal("data/myskin.json"));
		stage = new Stage(width, height, false);

		defaultRenderer = new DefaultRenderer(world, this);
		debugRenderer = new DebugRenderer(world, this);
		collisionRenderer = new CollisionRenderer(world, this);

		setupCam();
	}

	private void setupCam() {
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH * 0.5f, CAMERA_HEIGHT * 0.5f, 0);
		this.cam.update();
	}

	public void render(float delta) {

		// defaultRenderer.render(cam);
		collisionRenderer.render(cam, delta);
		if (debug) {
			debugRenderer.render(cam);

			stage.act(delta);

			stage.draw();
		}
	}

	// TODO: this does not belong here - extract
	public List<Block> getDrawableBlocks(int width, int height) {

		Avatar avatar = world.getAvatar();
		Level level = world.getLevel();
		int levelWidth = level.getWidth();
		int levelHeight = level.getHeight();

		int left = Math.max((int) avatar.getPosition().x - width, 0);
		int bottom = Math.max((int) avatar.getPosition().y - height, 0);

		int right = Math.min(left + 2 * width, levelWidth - 1);
		int top = Math.min(bottom + 2 * height, levelHeight - 1);

		List<Block> blocks = new ArrayList<Block>();
		Block block;
		for (int column = left; column <= right; column++) {
			for (int row = bottom; row <= top; row++) {
				block = level.getBlocks()[column][row];
				if (block != null) {
					blocks.add(block);
				}
			}
		}
		return blocks;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		debugRenderer.setSize(w, h);
		defaultRenderer.setSize(w, h);
		collisionRenderer.setSize(w, h);
		stage.setViewport(width, height, false);
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}
}

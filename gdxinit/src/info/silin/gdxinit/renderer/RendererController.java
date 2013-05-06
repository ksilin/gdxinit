package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class RendererController {

	private World world;

	private OrthographicCamera cam;

	private static final float CAMERA_WIDTH = 16f;
	private static final float CAMERA_HEIGHT = 9f;

	DebugRenderer debugRenderer;
	TextRenderer textRenderer = new TextRenderer();
	DefaultRenderer defaultRenderer;
	CollisionRenderer collisionRenderer;

	private boolean debug = false;

	public void setSize(int w, int h) {
		debugRenderer.setSize(w, h);
		defaultRenderer.setSize(w, h);
		collisionRenderer.setSize(w, h);
	}

	public RendererController(World world, boolean debug) {

		this.world = world;
		this.debug = debug;

		defaultRenderer = new DefaultRenderer(world);
		debugRenderer = new DebugRenderer(world);
		collisionRenderer = new CollisionRenderer(world);
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
		}
		// textRenderer.render();
	}
}

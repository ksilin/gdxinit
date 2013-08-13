package info.silin.gdxinit.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class RendererController {

	private static final double ZOOM_OUT_FACTOR = 1.02;
	private static final double ZOOM_IN_FACTOR = 0.98;
	private static final float CAM_ANGLE_INCREMENT = 1;
	private static final float CAM_POS_INCREMENT = 0.1f;

	public static OrthographicCamera cam;

	private static final float CAMERA_WIDTH = 16f;
	private static final float CAMERA_HEIGHT = 10f;

	private boolean debug = false;
	private DebugRenderer debugRenderer;
	private DefaultRenderer defaultRenderer;

	// TODO - extract to UIRenderer, how to distribute globally, for all
	// renderers?
	private Stage stage;
	private Skin skin;

	private int height;
	private int width;

	public RendererController(boolean debug) {
		this.debug = debug;

		skin = new Skin(Gdx.files.internal("data/myskin.json"));
		stage = new Stage(CAMERA_WIDTH, CAMERA_HEIGHT, false);

		defaultRenderer = new DefaultRenderer();
		debugRenderer = new DebugRenderer(skin, stage);
		setupCam();
	}

	private void setupCam() {
		cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		cam.position.set(CAMERA_WIDTH * 0.5f, CAMERA_HEIGHT * 0.5f, 0);
		cam.update();
	}

	public void draw(float delta) {
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		clear();
		if (debug) {
			debugRenderer.draw(cam);
			stage.act(delta);
			stage.draw();
		} else {
			defaultRenderer.draw(cam, delta);
		}
	}

	private void clear() {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 0.1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClear(GL10.GL_ALPHA_BITS);
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

	public void zoomIn() {
		cam.viewportWidth *= ZOOM_IN_FACTOR;
		cam.viewportHeight *= ZOOM_IN_FACTOR;
		cam.update();
	}

	public void zoomOut() {
		cam.viewportWidth *= ZOOM_OUT_FACTOR;
		cam.viewportHeight *= ZOOM_OUT_FACTOR;
		cam.update();
	}

	public void rotateCamCW() {
		cam.rotate(CAM_ANGLE_INCREMENT);
		cam.update();
	}

	public void rotateCamCCW() {
		cam.rotate(-CAM_ANGLE_INCREMENT);
		cam.update();
	}

	public void moveCamUp() {
		cam.position.y -= CAM_POS_INCREMENT;
		cam.update();
	}

	public void moveCamDown() {
		cam.position.y += CAM_POS_INCREMENT;
		cam.update();
	}

	public void moveCamLeft() {
		cam.position.x -= CAM_POS_INCREMENT;
		cam.update();
	}

	public void moveCamRight() {
		cam.position.x += CAM_POS_INCREMENT;
		cam.update();
	}
}

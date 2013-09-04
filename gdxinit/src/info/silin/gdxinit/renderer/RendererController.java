package info.silin.gdxinit.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class RendererController {

	private static final double ZOOM_OUT_FACTOR = 1.02;
	private static final double ZOOM_IN_FACTOR = 0.98;
	private static final float CAM_ANGLE_INCREMENT = 1;
	private static final float CAM_POS_INCREMENT = 0.1f;

	private static OrthographicCamera CAM;

	public static final float CAMERA_WIDTH = 20f;
	public static final float CAMERA_HEIGHT = 12f;

	private boolean debug = false;
	private DebugRenderer debugRenderer;
	private DefaultRenderer defaultRenderer;
	public static UIRenderer uiRenderer;

	public RendererController(boolean debug) {
		this.debug = debug;

		uiRenderer = new UIRenderer();
		defaultRenderer = new DefaultRenderer();
		debugRenderer = new DebugRenderer();
		setupCam();
	}

	private void setupCam() {
		CAM = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		CAM.position.set(CAMERA_WIDTH * 0.5f, CAMERA_HEIGHT * 0.5f, 0);
		CAM.update();
	}

	public void draw(float delta) {
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		clear();
		if (debug) {
			debugRenderer.draw(CAM);
		} else {
			defaultRenderer.draw(CAM, delta);
		}
		uiRenderer.draw(delta);
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
		if (debug)
			uiRenderer.showDebugUI();
		else
			uiRenderer.hideDebugUI();

	}

	public void zoomIn() {
		CAM.viewportWidth *= ZOOM_IN_FACTOR;
		CAM.viewportHeight *= ZOOM_IN_FACTOR;
		CAM.update();
	}

	public void zoomOut() {
		CAM.viewportWidth *= ZOOM_OUT_FACTOR;
		CAM.viewportHeight *= ZOOM_OUT_FACTOR;
		CAM.update();
	}

	public void rotateCamCW() {
		CAM.rotate(CAM_ANGLE_INCREMENT);
		CAM.update();
	}

	public void rotateCamCCW() {
		CAM.rotate(-CAM_ANGLE_INCREMENT);
		CAM.update();
	}

	public void moveCamUp() {
		CAM.position.y -= CAM_POS_INCREMENT;
		CAM.update();
	}

	public void moveCamDown() {
		CAM.position.y += CAM_POS_INCREMENT;
		CAM.update();
	}

	public void moveCamLeft() {
		CAM.position.x -= CAM_POS_INCREMENT;
		CAM.update();
	}

	public void moveCamRight() {
		CAM.position.x += CAM_POS_INCREMENT;
		CAM.update();
	}

	public static Vector2 getUnprojectedMousePosition() {
		return getUnprojectedTouchpointPosition(0);
	}

	public static Vector2 getUnprojectedTouchpointPosition(int index) {
		Vector3 unprojected = new Vector3(Gdx.input.getX(index),
				Gdx.input.getY(index), 1);
		CAM.unproject(unprojected);
		return new Vector2(unprojected.x, unprojected.y);
	}

	public void setSize(int width, int height) {
		uiRenderer.setSize(width, height);

	}
}

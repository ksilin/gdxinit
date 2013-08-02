package info.silin.gdxinit.screens;

import info.silin.gdxinit.GameMain;
import info.silin.gdxinit.WorldController;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class ParticleScreen implements Screen {

	private SpriteBatch spriteBatch = new SpriteBatch();
	private ParticleInputHandler inputHandler = new ParticleInputHandler();

	private OrthographicCamera cam;
	private static final float CAMERA_WIDTH = 16f;
	private static final float CAMERA_HEIGHT = 9f;
	private static final float SPEED = 0.1f;

	ParticleEffect hit;
	int emitterIndex;
	Array<ParticleEmitter> emitters;
	int particleCount = 10;

	// position of the particle effect
	private boolean debug = false;
	private float transX = 0;
	private float transY = 0;
	private float angle = 0;
	private float scale = 0.01f;

	public ParticleScreen() {
		setupCam();
	}

	private void setupCam() {
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(0, 0, 0);
		// cam.zoom = 50f;
		cam.update();
	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(inputHandler);

		hit = new ParticleEffect();
		hit.load(Gdx.files.internal("data/hit.p"), Gdx.files.internal("data"));
		hit.setPosition(0, CAMERA_HEIGHT * 0.5f);
	}

	@Override
	public void render(float delta) {

		if (hit.isComplete()) {
			hit.reset();
		}

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.setProjectionMatrix(cam.projection);
		spriteBatch.setTransformMatrix(cam.view);

		spriteBatch.getTransformMatrix().translate(transX, transY, 0);
		spriteBatch.getTransformMatrix().rotate(new Vector3(0, 0, 1), angle);
		spriteBatch.getTransformMatrix().scale(scale, scale, 1f);
		spriteBatch.begin();

		hit.draw(spriteBatch, delta);
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		inputHandler.setSize(width, height);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

	public class ParticleInputHandler extends InputMultiplexer {

		private Vector2 factor = new Vector2(1, 1);

		@Override
		public boolean keyDown(int keycode) {
			if (keycode == Keys.Q) {
				setDebug(!isDebug());
			}
			if (keycode == Keys.R) {
				hit.reset();
			}
			if (keycode == Keys.ESCAPE) {
				backToMenu();
			}

			if (keycode == Keys.W) {
				transY += SPEED;
				Gdx.app.log("ParticleScreen", "transY: " + transY);
			}
			if (keycode == Keys.S) {
				transY -= SPEED;
				Gdx.app.log("ParticleScreen", "transY: " + transY);
			}
			if (keycode == Keys.A) {
				transX -= SPEED;
				Gdx.app.log("ParticleScreen", "transX: " + transX);
			}
			if (keycode == Keys.D) {
				transX += SPEED;
				Gdx.app.log("ParticleScreen", "transX: " + transX);
			}

			if (keycode == Keys.UP) {
				scale *= 1.01f;
				Gdx.app.log("ParticleScreen", "scale: " + scale);
			}
			if (keycode == Keys.DOWN) {
				scale *= .99f;
				Gdx.app.log("ParticleScreen", "scale: " + scale);
			}

			if (keycode == Keys.LEFT) {
				angle += 1;
				Gdx.app.log("ParticleScreen", "angle: " + angle);
			}
			if (keycode == Keys.RIGHT) {
				angle -= 1;
				Gdx.app.log("ParticleScreen", "angle: " + angle);
			}
			return super.keyDown(keycode);
		}

		@Override
		public boolean keyUp(int keycode) {
			return super.keyUp(keycode);
		}

		@Override
		public boolean touchDown(int x, int y, int pointer, int button) {

			if (!Gdx.app.getType().equals(ApplicationType.Android)) {
				if (button == Buttons.LEFT) {
					// controller.updateMousePos(toWorldX(x), toWorldY(y));
				}
			}
			return super.touchDown(x, y, pointer, button);
		}

		@Override
		public boolean touchUp(int x, int y, int pointer, int button) {

			if (!Gdx.app.getType().equals(ApplicationType.Android)) {
				if (button == Buttons.LEFT) {
					// controller.updateMousePos(toWorldX(x), toWorldY(y));
				}
			}
			return super.touchUp(x, y, pointer, button);
		}

		@Override
		public boolean touchDragged(int x, int y, int pointer) {

			// controller.updateMousePos(toWorldX(x), toWorldY(y));
			return super.touchDragged(x, y, pointer);
		}

		public void setSize(int width, int height) {

			this.factor = new Vector2(WorldController.WIDTH / width,
					WorldController.HEIGHT / height);

			Gdx.app.log("InputHandler", "factor: x: " + factor.x + ", y: "
					+ factor.y);
		}

		public float toWorldX(float x) {
			return x * factor.x;
		}

		public float toWorldY(float y) {
			return WorldController.HEIGHT - y * factor.y;
		}

		public void backToMenu() {
			GameMain.instance.setScreen(new MenuScreen());
		}
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}

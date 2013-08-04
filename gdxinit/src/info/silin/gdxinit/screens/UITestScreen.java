package info.silin.gdxinit.screens;

import info.silin.gdxinit.GameMain;
import info.silin.gdxinit.World;

import java.text.DecimalFormat;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class UITestScreen implements Screen {

	private Stage stage;
	private Skin skin;

	private int width;
	private int height;

	private static float TOUCHPAD_RAD = 0.17f;
	private UIInputHandler inputHandler = new UIInputHandler();

	private Touchpad padLeft;
	private Touchpad padRight;
	private Label debugInfo;

	private float deltaTotal;

	private DecimalFormat numberFormat = new DecimalFormat("#.##");

	@Override
	public void show() {

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		Gdx.input.setInputProcessor(inputHandler);
		skin = new Skin(Gdx.files.internal("data/myskin.json"));

		padLeft = new Touchpad(5, skin);
		padLeft.setSize(width * TOUCHPAD_RAD, width * TOUCHPAD_RAD);
		padLeft.setPosition(width / 5 - (width * TOUCHPAD_RAD) / 2, height / 5
				- (height * TOUCHPAD_RAD) / 2);
		padRight = new Touchpad(5, skin);
		padRight.setSize(width * TOUCHPAD_RAD, width * TOUCHPAD_RAD);
		padRight.setPosition(width - width / 5 - (width * TOUCHPAD_RAD) / 2,
				height / 5 - (height * TOUCHPAD_RAD) / 2);

		debugInfo = new Label("debug label", skin);
		debugInfo.setPosition(width / 2, height / 2);
		debugInfo.setColor(0.8f, 0.8f, 0.2f, 1f);
		debugInfo.setSize(100, 100);

		stage = new Stage(width, height, false);
		Gdx.input.setInputProcessor(stage);
		stage.addActor(padLeft);
		stage.addActor(padRight);
		stage.addActor(debugInfo);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		deltaTotal += delta;

		stage.act(delta);

		StringBuilder debugText = new StringBuilder("debug info: \n");
		debugText.append("left pad: x: "
				+ numberFormat.format(padLeft.getKnobPercentX()) + ", y: "
				+ numberFormat.format(padLeft.getKnobPercentY()) + "\n");
		debugText.append("right pad: x: "
				+ numberFormat.format(padRight.getKnobPercentX()) + ", y: "
				+ numberFormat.format(padRight.getKnobPercentY()) + "\n");

		debugInfo.setText(debugText);

		debugInfo.setY(height * (float) Math.abs(Math.sin(deltaTotal)));

		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		stage.setViewport(width, height, false);
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

	public class UIInputHandler extends InputMultiplexer {

		private Vector2 factor = new Vector2(1, 1);

		@Override
		public boolean keyDown(int keycode) {

			if (keycode == Keys.ESCAPE) {
				backToMenu();
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

			this.factor = new Vector2(World.WIDTH / width, World.HEIGHT
					/ height);

			Gdx.app.log("InputHandler", "factor: x: " + factor.x + ", y: "
					+ factor.y);
		}

		public float toWorldX(float x) {
			return x * factor.x;
		}

		public float toWorldY(float y) {
			return World.HEIGHT - y * factor.y;
		}

		public void backToMenu() {
			GameMain.instance.setScreen(new MenuScreen());
		}
	}
}

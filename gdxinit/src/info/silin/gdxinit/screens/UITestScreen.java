package info.silin.gdxinit.screens;

import java.text.DecimalFormat;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class UITestScreen implements Screen {

	private Stage stage;
	private Skin skin;
	private Game game;

	private int width;
	private int height;

	// the sizes are screen percentages
	private static float BUTTON_WIDTH = 0.15f;
	private static float BUTTON_HEIGHT = 0.1f;

	private static float TOUCHPAD_RAD = 0.17f;

	private Touchpad padLeft;
	private Touchpad padRight;
	private Label debugInfo;

	private DecimalFormat numberFormat = new DecimalFormat("#.##");

	public UITestScreen(Game game) {
		this.game = game;
	}

	@Override
	public void show() {

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

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
		debugInfo.setPosition(0, height/2);
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

		stage.act(delta);

		StringBuilder debugText = new StringBuilder("debug info: \n");
		debugText.append("left pad: x: "
				+ numberFormat.format(padLeft.getKnobPercentX()) + ", y: "
				+ numberFormat.format(padLeft.getKnobPercentY()) + "\n");
		debugText.append("right pad: x: "
				+ numberFormat.format(padRight.getKnobPercentX()) + ", y: "
				+ numberFormat.format(padRight.getKnobPercentY()) + "\n");

		debugInfo.setText(debugText);

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

	private void goToMenu() {
		game.setScreen(new MenuScreen(game));
	}
}

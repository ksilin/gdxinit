package info.silin.gdxinit.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
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

	private static float TOUCHPAD_RAD = 0.2f;

	public UITestScreen(Game game) {
		this.game = game;
	}

	@Override
	public void show() {

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		skin = new Skin(Gdx.files.internal("data/uiskin.json"));

		Touchpad pad = new Touchpad(10, skin);
		pad.setSize(width * TOUCHPAD_RAD, width * TOUCHPAD_RAD);
		pad.setPosition(width / 2 - (width * TOUCHPAD_RAD) / 2, height / 2
				- (height * TOUCHPAD_RAD) / 2);

		Slider slider = new Slider(0f, 100f, 0.1f, false, skin);

		stage = new Stage(width, height, false);
		Gdx.input.setInputProcessor(stage);
		stage.addActor(pad);
		stage.addActor(slider);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
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

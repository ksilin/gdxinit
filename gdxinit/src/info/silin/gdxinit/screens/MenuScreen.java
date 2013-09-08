package info.silin.gdxinit.screens;

import info.silin.gdxinit.GameMain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {

	private Stage stage;
	private Skin skin;

	private int width;
	private int height;

	// the sizes are screen percentages
	private static float BUTTON_WIDTH = 0.15f;
	private static float BUTTON_HEIGHT = 0.1f;

	@Override
	public void show() {

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		skin = new Skin(Gdx.files.internal("data/myskin.json"));

		Button startGameButton = new TextButton("Start", skin, "default");

		startGameButton.setPosition(width / 2 - width * BUTTON_WIDTH / 2f,
				height - height * BUTTON_HEIGHT * 2);
		startGameButton.setSize(width * BUTTON_WIDTH, height * BUTTON_HEIGHT);
		startGameButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				startGame();
			}
		});

		Button particleButton = new TextButton("Particles", skin, "default");

		particleButton.setPosition(width / 2 - width * BUTTON_WIDTH / 2f,
				height - height * BUTTON_HEIGHT * 4);
		particleButton.setSize(width * BUTTON_WIDTH, height * BUTTON_HEIGHT);
		particleButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				startParticles();
			}
		});

		Button uiTestButton = new TextButton("UI test", skin, "toggle");

		uiTestButton.setPosition(width / 2 - width * BUTTON_WIDTH / 2f, height
				- height * BUTTON_HEIGHT * 6);
		uiTestButton.setSize(width * BUTTON_WIDTH, height * BUTTON_HEIGHT);
		uiTestButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				goToUITestScreen();
			}
		});

		stage = new Stage(width, height, false);
		Gdx.input.setInputProcessor(stage);

		stage.addActor(startGameButton);
		stage.addActor(particleButton);
		stage.addActor(uiTestButton);
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
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		Gdx.input.setInputProcessor(null);
	}

	private void startGame() {
		GameMain.instance.setScreen(new GameScreen());
	}

	private void startParticles() {
		GameMain.instance.setScreen(new ParticleScreen());

	}

	private void goToUITestScreen() {
		GameMain.instance.setScreen(new UITestScreen());
	}
}

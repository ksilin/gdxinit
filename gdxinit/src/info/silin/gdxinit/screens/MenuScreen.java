package info.silin.gdxinit.screens;

import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.events.ScreenChangeEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {

	private Stage stage;
	private Skin skin;

	private int width;
	private int height;

	// the sizes are screen percentages
	private static float BUTTON_WIDTH = 0.25f;
	private static float BUTTON_HEIGHT = 0.2f;

	@Override
	public void show() {

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		skin = new Skin(Gdx.files.internal("data/myskin.json"));

		Button startGameButton = new TextButton("Start", skin, "default");
		startGameButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Events.post(new ScreenChangeEvent(new GameScreen()));
			}
		});

		Button particleButton = new TextButton("Particles", skin, "default");
		particleButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Events.post(new ScreenChangeEvent(
						new ParticleEffectsPlayground()));
			}
		});

		Button uiTestButton = new TextButton("UI test", skin, "toggle");
		uiTestButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Events.post(new ScreenChangeEvent(new UITestScreen()));
			}
		});

		Table table = new Table();
		table.setSize(width, height);
		table.row();
		addToTable(table, startGameButton);
		addToTable(table, particleButton);
		addToTable(table, uiTestButton);

		stage = new Stage(width, height, false);
		Gdx.input.setInputProcessor(stage);

		stage.addActor(table);
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

	private void addToTable(Table table, Button actor) {
		table.add(actor).expand().prefWidth(width * BUTTON_WIDTH)
				.prefHeight(height * BUTTON_HEIGHT);
	}
}

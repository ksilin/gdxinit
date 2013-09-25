package info.silin.gdxinit.screens;

import info.silin.gdxinit.GameMain;
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

		skin = new Skin(Gdx.files.internal("data/uiskin32.json"));

		Button startGameButton = new TextButton("Start", skin, "default");
		startGameButton
				.addListener(createPostingListener(new ScreenChangeEvent(
						GameMain.GAME_SCREEN)));

		Button particleButton = new TextButton("Particles", skin, "default");
		particleButton.addListener(createPostingListener(new ScreenChangeEvent(
				GameMain.PARTICLE_SCREEN)));

		Button uiTestButton = new TextButton("UI test", skin, "toggle");
		uiTestButton.addListener(createPostingListener(new ScreenChangeEvent(
				GameMain.UITEST_SCREEN)));

		Table table = new Table();
		table.setSize(width, height);
		table.row();
		addToTable(table, startGameButton);
		addToTable(table, particleButton);
		addToTable(table, uiTestButton);

		stage = new Stage(width, height, false);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	private <T> ClickListener createPostingListener(final T eventToPost) {
		return new ClickListener() {

			T event = eventToPost;

			@Override
			public void clicked(InputEvent ie, float x, float y) {
				super.clicked(ie, x, y);
				Events.post(event);
			}
		};
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

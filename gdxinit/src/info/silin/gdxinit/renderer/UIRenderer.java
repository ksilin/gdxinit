package info.silin.gdxinit.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UIRenderer {

	public Stage stage;
	public Skin skin;

	public static float BUTTON_WIDTH = 0.25f;
	public static float BUTTON_HEIGHT = 0.1f;

	public UIRenderer() {
		skin = new Skin(Gdx.files.internal("data/myskin.json"));
		stage = new Stage(1, 1, false);
	}

	public void draw(float delta) {
		stage.act(delta);
		stage.draw();
	}

	public void setSize(int width, int height) {
		stage.setViewport(width, height, false);
	}
}

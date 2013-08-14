package info.silin.gdxinit.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UIRenderer {

	public Stage stage;
	public Skin skin;

	public UIRenderer() {
		skin = new Skin(Gdx.files.internal("data/myskin.json"));
		stage = new Stage(RendererController.CAMERA_WIDTH,
				RendererController.CAMERA_HEIGHT, false);
	}

	public void draw(float delta) {
		stage.act(delta);
		stage.draw();
	}

	public void setSize(int w, int h) {
		stage.setViewport(w, h, false);
	}
}

package info.silin.gdxinit.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextRenderer {

	SpriteBatch fontBatch = new SpriteBatch();
	BitmapFont font;

	public TextRenderer() {
		loadTextures();
	}

	private void loadTextures() {

		font = new BitmapFont(
				Gdx.files.internal("data/DejaVuSansCondensed12.fnt"),
				Gdx.files.internal("data/DejaVuSansCondensed12.png"), false);
	}

	public void draw(String text, float x, float y) {
		fontBatch.begin();
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(fontBatch, text, x, y);
		fontBatch.end();
	}

}

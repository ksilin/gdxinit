package info.silin.gdxinit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextRenderer {

	SpriteBatch fontBatch = new SpriteBatch();
	BitmapFont font;
	CharSequence str = "Hello World!";

	public TextRenderer() {
		loadTextures();
	}

	private void loadTextures() {

		font = new BitmapFont(
				Gdx.files.internal("data/DejaVuSansCondensed12.fnt"),
				Gdx.files.internal("data/DejaVuSansCondensed12.png"), false);
	}

	public void render() {

		fontBatch.begin();
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(fontBatch, str, 25, 160);
		fontBatch.end();
	}

}

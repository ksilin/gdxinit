package info.silin.gdxinit.screens;

import info.silin.gdxinit.GameMain;
import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.events.ScreenChangeEvent;
import info.silin.gdxinit.renderer.GridRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class ParticleEffectsPlayground implements Screen {

	private SpriteBatch batch;
	private ParticleEffect prototype;
	private ParticleEffectPool pool;
	private Array<PooledEffect> effects;
	private GridRenderer gridRenderer = new GridRenderer();

	private Camera cam = new OrthographicCamera(20, 12);

	private float transX = 0;
	private float transY = 0;
	private float scale = 0.03f;
	private float angle = 0;

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gridRenderer.draw(cam);

		batch.setProjectionMatrix(cam.combined);
		batch.getTransformMatrix().idt();

		batch.getTransformMatrix().translate(transX, transY, 0);
		batch.getTransformMatrix().scale(scale, scale, 1f);
		batch.getTransformMatrix().rotate(new Vector3(0, 0, 1f), angle);

		batch.begin();
		for (PooledEffect effect : effects) {
			effect.draw(batch, delta);
			if (effect.isComplete()) {
				effect.start();
				// effects.removeValue(effect, true);
				// effect.free();
			}
		}
		batch.end();
		// Gdx.app.log("pool stats", "active: " + effects.size + " | free: "
		// + pool.getFree() + "/" + pool.max + " | record: " + pool.peak);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {

		cam.position.set(new Vector3(cam.viewportWidth / 2,
				cam.viewportHeight / 2, 0));
		cam.update();

		batch = new SpriteBatch();

		prototype = new ParticleEffect();
		prototype.load(Gdx.files.internal("data/hit.p"),
				Gdx.files.internal("data"));
		prototype.setPosition(0, 0);
		prototype.start();

		pool = new ParticleEffectPool(prototype, 0, 70);
		effects = new Array<PooledEffect>();

		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				PooledEffect effect = pool.obtain();
				Vector3 v = new Vector3(screenX, screenY, 0);
				cam.unproject(v);
				// compensate scaling
				v.mul(1f / scale);
				effect.setPosition(v.x, v.y);
				effects.add(effect);
				return true;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.ESCAPE) {
					backToMenu();
				}

				if (keycode == Keys.LEFT) {
					angle += 1;
					Gdx.app.log("ParticleScreen", "angle: " + angle);
				}
				if (keycode == Keys.RIGHT) {
					angle -= 1;
					Gdx.app.log("ParticleScreen", "angle: " + angle);
				}
				if (keycode == Keys.UP) {
					scale *= 1.05;
					Gdx.app.log("ParticleScreen", "scale: " + scale);
				}
				if (keycode == Keys.DOWN) {
					scale *= 0.99;
					Gdx.app.log("ParticleScreen", "scale: " + scale);
				}
				return false;
			}

			public void backToMenu() {
				Events.post(new ScreenChangeEvent(GameMain.MENU_SCREEN));
			}
		});
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		batch.dispose();
		prototype.dispose();
	}
}

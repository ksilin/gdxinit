package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Avatar.State;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Projectile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class DefaultRenderer {

	private World world;
	private RendererController rendererController;

	ShapeRenderer shapeRenderer = new ShapeRenderer();
	private static final Color PROJECTILE_COLOR = new Color(0.8f, 0.8f, 0, 1);

	private static final float RUNNING_FRAME_DURATION = 0.06f;

	private SpriteBatch spriteBatch = new SpriteBatch();

	private TextureRegion bobIdleLeft;
	private TextureRegion bobIdleRight;
	private TextureRegion blockTexture;
	private TextureRegion bobJumpLeft;
	private TextureRegion bobFallLeft;
	private TextureRegion bobJumpRight;
	private TextureRegion bobFallRight;

	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;

	ParticleEffect hit;
	int emitterIndex;
	Array<ParticleEmitter> emitters;
	int particleCount = 10;

	private Map<Projectile, ParticleEffect> explosions = new HashMap<Projectile, ParticleEffect>();

	public DefaultRenderer(World world, RendererController rendererController) {
		this.world = world;
		this.rendererController = rendererController;
		loadTextures();
		prepareParticles();
	}

	private void prepareParticles() {
		hit = new ParticleEffect();
		hit.load(Gdx.files.internal("data/hit.p"), Gdx.files.internal("data"));
		hit.setPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
	}

	private void loadTextures() {

		Gdx.app.log("WorldRenderer", "loading the textures");

		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/textures.atlas"));

		blockTexture = atlas.findRegion("crate");

		bobIdleLeft = atlas.findRegion("bob-01");
		bobIdleRight = new TextureRegion(bobIdleLeft);
		bobIdleRight.flip(true, false);

		TextureRegion[] walkLeftFrames = createWalkLeftFrames(atlas);
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION,
				walkLeftFrames);

		TextureRegion[] walkRightFrames = createWalkRightFrames(walkLeftFrames);
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION,
				walkRightFrames);

		bobJumpLeft = atlas.findRegion("bob-up");
		bobJumpRight = new TextureRegion(bobJumpLeft);
		bobJumpRight.flip(true, false);

		bobFallLeft = atlas.findRegion("bob-down");
		bobFallRight = new TextureRegion(bobFallLeft);
		bobFallRight.flip(true, false);
	}

	private TextureRegion[] createWalkRightFrames(TextureRegion[] walkLeftFrames) {
		TextureRegion[] walkRightFrames = new TextureRegion[5];

		for (int i = 0; i < 5; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		return walkRightFrames;
	}

	private TextureRegion[] createWalkLeftFrames(TextureAtlas atlas) {
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			walkLeftFrames[i] = atlas.findRegion("bob-0" + (i + 2));
		}
		return walkLeftFrames;
	}

	public void render(Camera cam) {
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		drawBlocks();
		drawAvatar();
		spriteBatch.end();

		shapeRenderer.setProjectionMatrix(cam.combined);
		renderProjectiles();
		renderExplosions();
		filterFinishedExplosions();
	}

	private void filterFinishedExplosions() {

		Set<Entry<Projectile, ParticleEffect>> entries = explosions.entrySet();

		for (Iterator<Entry<Projectile, ParticleEffect>> iterator = entries
				.iterator(); iterator.hasNext();) {

			Entry<Projectile, ParticleEffect> entry = (Entry<Projectile, ParticleEffect>) iterator
					.next();

			if (entry.getValue().isComplete()) {
				 iterator.remove();
			}
		}
	}

	private void drawBlocks() {
		for (Entity block : rendererController.getDrawableBlocks(2, 2)) {
			drawBlock(block);
		}
	}

	private void drawBlock(Entity block) {
		Rectangle rect = block.getBoundingBox();
		spriteBatch.draw(blockTexture, rect.x, rect.y, Block.SIZE, Block.SIZE);
	}

	private void drawAvatar() {
		Avatar avatar = world.getAvatar();
		TextureRegion frame = getAvatarFrame(avatar);
		spriteBatch.draw(frame, avatar.getPosition().x, avatar.getPosition().y,
				Avatar.SIZE, Avatar.SIZE);
	}

	private TextureRegion getAvatarFrame(Avatar avatar) {
		TextureRegion frame = avatar.isFacingLeft() ? bobIdleLeft
				: bobIdleRight;
		if (avatar.getState().equals(State.WALKING)) {
			frame = avatar.isFacingLeft() ? walkLeftAnimation.getKeyFrame(
					avatar.getStateTime(), true) : walkRightAnimation
					.getKeyFrame(avatar.getStateTime(), true);
		}
		return frame;
	}

	private void renderProjectiles() {
		List<Projectile> projectiles = world.getProjectiles();

		shapeRenderer.begin(ShapeType.Rectangle);
		shapeRenderer.setColor(PROJECTILE_COLOR);
		for (Projectile p : projectiles) {
			if (Projectile.State.FLYING == p.state) {
				Rectangle boundingBox = p.getBoundingBox();
				shapeRenderer.rect(boundingBox.x, boundingBox.y,
						boundingBox.width, boundingBox.height);
			}
		}
		shapeRenderer.end();

		// get new explosions, set according projectiles to idle
		for (Projectile p : projectiles) {
			if (Projectile.State.EXPLODING == p.state) {

				p.state = Projectile.State.IDLE;

				ParticleEffect value = new ParticleEffect(hit);
				value.reset();
				explosions.put(p, value);
			}
		}

	}

	private void renderExplosions() {
		// render explosions
		shapeRenderer.begin(ShapeType.Rectangle);
		shapeRenderer.setColor(PROJECTILE_COLOR);
		for (Entry<Projectile, ParticleEffect> e : explosions.entrySet()) {

			Rectangle boundingBox = e.getKey().getBoundingBox();
			shapeRenderer.rect(boundingBox.x, boundingBox.y,
					boundingBox.width * 3, boundingBox.height * 3);
			ParticleEffect effect = e.getValue();
			effect.draw(spriteBatch);
		}
		shapeRenderer.end();
	}
}

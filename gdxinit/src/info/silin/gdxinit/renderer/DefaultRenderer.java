package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.renderer.texture.AvatarTexturePack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class DefaultRenderer {

	private static final float EXPLOSION_SCALE = 0.01f;
	private static final Vector3 AXIS = new Vector3(0, 0, 1);
	private World world;
	private RendererController rendererController;

	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private static final Color PROJECTILE_COLOR = new Color(0.8f, 0.8f, 0, 1);

	private TextureRegion blockTexture;
	private AvatarTexturePack avatarTextures;

	private SpriteBatch spriteBatch = new SpriteBatch();

	private ParticleEffect explosionPrototype;

	private Map<Projectile, ParticleEffect> explosions = new HashMap<Projectile, ParticleEffect>();

	public DefaultRenderer(World world, RendererController rendererController) {
		this.world = world;
		this.rendererController = rendererController;

		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/textures.atlas"));
		avatarTextures = new AvatarTexturePack(atlas);
		blockTexture = atlas.findRegion("crate");
		prepareParticles();
	}

	private void prepareParticles() {
		explosionPrototype = new ParticleEffect();
		explosionPrototype.load(Gdx.files.internal("data/hit.p"),
				Gdx.files.internal("data"));
	}

	public void render(Camera cam, float delta) {

		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.getTransformMatrix().idt();

		spriteBatch.begin();
		drawBlocks();
		drawAvatar();
		spriteBatch.end();

		shapeRenderer.setProjectionMatrix(cam.combined);
		drawProjectiles();
		checkForNewExplosions();
		drawExplosions(cam, delta);
		filterFinishedExplosions();
	}

	private void filterFinishedExplosions() {

		Set<Entry<Projectile, ParticleEffect>> entries = explosions.entrySet();

		for (Iterator<Entry<Projectile, ParticleEffect>> iterator = entries
				.iterator(); iterator.hasNext();) {

			Entry<Projectile, ParticleEffect> explosion = iterator.next();

			if (explosion.getValue().isComplete()) {
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
		TextureRegion frame = avatarTextures.getAvatarFrame(avatar);
		spriteBatch.draw(frame, avatar.getPosition().x, avatar.getPosition().y,
				Avatar.SIZE, Avatar.SIZE);
	}

	private void drawProjectiles() {

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
	}

	private void checkForNewExplosions() {

		List<Projectile> projectiles = world.getProjectiles();

		// get new explosions, set according projectiles to idle
		for (Projectile p : projectiles) {
			if (Projectile.State.EXPLODING == p.state) {

				p.state = Projectile.State.IDLE;

				ParticleEffect explosion = new ParticleEffect(
						explosionPrototype);
				explosion.reset();
				Vector2 position = p.getPosition();
				explosion.setPosition(position.x, position.y);
				Gdx.app.log("DefaultRenderer#checkForNewExplosions",
						"creating an explosion at " + position.x + ", "
								+ position.y);
				// TODO - setup the explosion with angle of the projectile

				explosions.put(p, explosion);
			}
		}
	}

	// TODO - confused by the transformations - recall
	private void drawExplosions(Camera cam, float delta) {

		spriteBatch.setProjectionMatrix(cam.projection);
		spriteBatch.setTransformMatrix(cam.view);

		for (Entry<Projectile, ParticleEffect> e : explosions.entrySet()) {

			Projectile projectile = e.getKey();
			ParticleEffect effect = e.getValue();

			Vector2 position = projectile.getPosition();
			Vector2 velocity = projectile.getVelocity();

			spriteBatch.getTransformMatrix().translate(position.x, position.y,
					0);
			spriteBatch.getTransformMatrix()
					.rotate(AXIS, velocity.angle() + 90);
			spriteBatch.getTransformMatrix().scale(EXPLOSION_SCALE,
					EXPLOSION_SCALE, 1f);
			spriteBatch.begin();

			effect.draw(spriteBatch, delta);
			spriteBatch.end();
		}
	}
}

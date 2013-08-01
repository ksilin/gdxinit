package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.renderer.texture.AvatarTexturePack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	private World world;
	private RendererController rendererController;

	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private static final Color PROJECTILE_COLOR = new Color(0.8f, 0.8f, 0, 1);

	private TextureRegion blockTexture;
	private AvatarTexturePack avatarTextures;

	private SpriteBatch spriteBatch = new SpriteBatch();

	private ParticleEffect explosionPrototype;

	private List<Explosion> explosions = new ArrayList<Explosion>();

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

		for (Iterator<Explosion> iterator = explosions.iterator(); iterator
				.hasNext();) {

			Explosion explosion = iterator.next();
			if (explosion.getEffect().isComplete()) {
				iterator.remove();
			}
		}
	}

	private void drawBlocks() {
		// setting the radios quite large to see more blocks
		List<Entity> drawableBlocks = rendererController.getDrawableBlocks(10,
				10);
		for (Entity block : drawableBlocks) {
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
				Vector2 velocity = p.getVelocity();
				Explosion ex = new Explosion(explosion, position,
						velocity.angle() + 90);
				explosions.add(ex);
			}
		}
	}

	private void drawExplosions(Camera cam, float delta) {

		spriteBatch.setProjectionMatrix(cam.projection);
		spriteBatch.setTransformMatrix(cam.view);

		for (Explosion ex : explosions) {

			Vector2 position = ex.getPosition();
			spriteBatch.getTransformMatrix().translate(position.x, position.y,
					0);
			spriteBatch.getTransformMatrix().rotate(Vector3.Z, ex.getAngle());
			spriteBatch.getTransformMatrix().scale(ex.getSize(), ex.getSize(),
					1f);
			spriteBatch.begin();

			ex.getEffect().draw(spriteBatch, delta);
			spriteBatch.end();
		}
	}
}

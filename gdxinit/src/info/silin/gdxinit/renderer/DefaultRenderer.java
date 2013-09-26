package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.entity.state.Dead;
import info.silin.gdxinit.renderer.texture.AvatarTexturePack;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class DefaultRenderer {

	private static final Color PROJECTILE_COLOR = new Color(0.8f, 0.8f, 0, 1);
	private static final Color ENEMY_TINT = new Color(0.2f, 0.8f, 0.5f, 0.9f);
	private static final Color TARGET_TINT = new Color(1f, 0.2f, 0.3f, 0.9f);
	private MyShapeRenderer shapeRenderer = new MyShapeRenderer();

	private TextureRegion blockTexture;
	private AvatarTexturePack avatarTextures;

	private SpriteBatch spriteBatch = new SpriteBatch();

	public DefaultRenderer() {
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/textures.atlas"));
		avatarTextures = new AvatarTexturePack(atlas);
		blockTexture = atlas.findRegion("crate");
	}

	public void draw(Camera cam, float delta) {

		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.begin(ShapeType.Rectangle);
		drawProjectiles();
		shapeRenderer.end();

		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.enableBlending();
		spriteBatch.begin();
		drawBlocks();
		drawEnemies();
		drawTarget();
		drawAvatar();
		spriteBatch.end();

		drawExplosions(cam);
	}

	private void drawBlocks() {
		// setting the radius quite large to see more blocks
		List<Entity> drawableBlocks = World.INSTANCE.getAllBlocks();
		for (Entity block : drawableBlocks) {
			drawBlock(block);
		}
	}

	private void drawBlock(Entity block) {
		Rectangle rect = block.getBoundingBox();
		spriteBatch.draw(blockTexture, rect.x, rect.y, block.getSize(),
				block.getSize());
	}

	private void drawAvatar() {
		Avatar avatar = World.INSTANCE.getAvatar();
		TextureRegion frame = avatarTextures.getWalkFrame(avatar);
		spriteBatch.draw(frame, avatar.getPosition().x, avatar.getPosition().y,
				avatar.getSize(), avatar.getSize());
	}

	private void drawEnemies() {
		List<Enemy> enemies = World.INSTANCE.getEnemies();

		Color prevColor = spriteBatch.getColor();
		for (Enemy e : enemies) {
			TextureRegion frame = avatarTextures.getWalkFrame(e);
			Vector2 pos = e.getPosition();
			float size = e.getSize();

			if (Dead.getInstance() == e.getState()) {
				TextureRegion f = avatarTextures.getBloodStain();
				spriteBatch.draw(f, pos.x, pos.y, size, size);
			} else {
				spriteBatch.setColor(ENEMY_TINT);
				spriteBatch.draw(frame, pos.x, pos.y, size, size);
			}
			spriteBatch.setColor(prevColor);
		}
	}

	private void drawTarget() {
		Enemy target = World.INSTANCE.getLevel().getTarget();
		TextureRegion frame = avatarTextures.getWalkFrame(target);
		Vector2 pos = target.getPosition();
		float size = target.getSize();

		Color prevColor = spriteBatch.getColor();
		if (Dead.getInstance() == target.getState()) {
			TextureRegion f = avatarTextures.getBloodStain();
			spriteBatch.draw(f, pos.x, pos.y, size, size);
		} else {
			spriteBatch.setColor(TARGET_TINT);
			spriteBatch.draw(frame, pos.x, pos.y, size, size);
		}
		spriteBatch.setColor(prevColor);
	}

	private void drawProjectiles() {
		for (Projectile p : World.INSTANCE.getProjectiles()) {
			Rectangle boundingBox = p.getBoundingBox();
			shapeRenderer.drawRect(boundingBox, PROJECTILE_COLOR);
		}
	}

	private void drawExplosions(Camera cam) {

		spriteBatch.setProjectionMatrix(cam.projection);
		spriteBatch.setTransformMatrix(cam.view);

		for (Explosion ex : World.INSTANCE.getExplosions()) {

			Vector2 position = ex.getPosition();
			spriteBatch.getTransformMatrix().translate(position.x, position.y,
					0);
			spriteBatch.getTransformMatrix().rotate(Vector3.Z, ex.getAngle());
			spriteBatch.getTransformMatrix().scale(ex.getSize(), ex.getSize(),
					1f);
			spriteBatch.begin();

			ex.getEffect().draw(spriteBatch);
			spriteBatch.end();
		}
		spriteBatch.getTransformMatrix().idt();
	}
}

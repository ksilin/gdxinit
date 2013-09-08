package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Projectile;
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

	private MyShapeRenderer shapeRenderer = new MyShapeRenderer();
	private static final Color PROJECTILE_COLOR = new Color(0.8f, 0.8f, 0, 1);

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
		drawAvatar();
		drawEnemies();
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
		TextureRegion frame = avatarTextures.getAvatarFrame(avatar);
		spriteBatch.draw(frame, avatar.getPosition().x, avatar.getPosition().y,
				avatar.getSize(), avatar.getSize());
	}

	private void drawEnemies() {
		List<Enemy> enemies = World.INSTANCE.getEnemies();
		Avatar avatar = World.INSTANCE.getAvatar();
		TextureRegion frame = avatarTextures.getAvatarFrame(avatar);
		for (Enemy e : enemies) {
			Vector2 pos = e.getPosition();
			float size = avatar.getSize();
			spriteBatch.draw(frame, pos.x, pos.y, size, size);
		}
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

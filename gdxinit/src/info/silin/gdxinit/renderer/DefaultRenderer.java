package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;
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

	public DefaultRenderer(World world, RendererController rendererController) {
		this.world = world;
		this.rendererController = rendererController;

		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/textures.atlas"));
		avatarTextures = new AvatarTexturePack(atlas);
		blockTexture = atlas.findRegion("crate");
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
		drawExplosions(cam);
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

	private void drawExplosions(Camera cam) {

		spriteBatch.setProjectionMatrix(cam.projection);
		spriteBatch.setTransformMatrix(cam.view);

		for (Explosion ex : world.getExplosions()) {

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
	}
}

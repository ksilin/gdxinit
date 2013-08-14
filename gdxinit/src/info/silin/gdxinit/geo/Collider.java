package info.silin.gdxinit.geo;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Collider {

	public List<Block> selectCollidableBlocks(List<Block> blocks, Rectangle rect) {
		return blocks;
	}

	public Rectangle predictBoundingBox(Entity entity, float delta) {
		Rectangle result = new Rectangle(entity.getBoundingBox());
		Vector2 velocity = entity.getVelocity().cpy().mul(delta);
		result.x += velocity.x;
		result.y += velocity.y;
		return result;
	}

	public List<Collision> predictCollisions(List<Entity> blocks,
			Entity entity, float delta) {
		List<Collision> result = new ArrayList<Collision>();

		Rectangle predictedBoundingBox = predictBoundingBox(entity, delta);
		result = getCollisions(blocks, predictedBoundingBox,
				entity.getVelocity());

		return result;
	}

	public void resolveCollisions(List<Entity> blocks, Avatar avatar,
			float delta) {

		float tempDelta = delta;
		for (Entity block : blocks) {

			Rectangle bounds = block.getBoundingBox();
			Rectangle predictedBoundingBox = predictBoundingBox(avatar, delta);
			if (predictedBoundingBox.overlaps(bounds)) {
				Gdx.app.log("Collider",
						"colliding with block at " + block.getPosition());
				tempDelta = pushBackEntity3(avatar, predictedBoundingBox,
						bounds, tempDelta);
				Gdx.app.log("Collider", "tempDelta: " + tempDelta);
			}
		}
		avatar.update(tempDelta);
	}

	private float pushBackEntity3(Entity entity,
			Rectangle predictedBoundingBox, Rectangle blockBoundingBox,
			float delta) {

		MinimumTranslationVector minimumTranslationVector = new MinimumTranslationVector();
		Intersector.overlapConvexPolygons(
				GeoFactory.fromRectangle(predictedBoundingBox),
				GeoFactory.fromRectangle(blockBoundingBox),
				minimumTranslationVector);

		Vector2 multiplied = minimumTranslationVector.normal.cpy().mul(
				minimumTranslationVector.depth);
		entity.getPosition().add(multiplied);

		return delta;
	}

	public List<Collision> getCollisions(List<Entity> blocks,
			Rectangle boundingBox, Vector2 velocity) {

		List<Collision> collisions = new ArrayList<Collision>();
		List<Entity> collidingBlocks = getCollidingBlocks(blocks, boundingBox);
		for (Entity block : collidingBlocks) {
			collisions.add(new Collision(boundingBox, velocity, block
					.getBoundingBox(), new Vector2(), calcTranslationVector(
					boundingBox, block.getBoundingBox())));
		}
		return collisions;
	}

	private MinimumTranslationVector calcTranslationVector(Rectangle r1,
			Rectangle r2) {
		MinimumTranslationVector minimumTranslationVector = new MinimumTranslationVector();
		Intersector.overlapConvexPolygons(GeoFactory.fromRectangle(r1),
				GeoFactory.fromRectangle(r2), minimumTranslationVector);

		// for some reason I have to flip the axes - the vector always wants to
		// push into the negative X and positive Y
		if (r1.x + r1.getWidth() / 2 > r2.x + r2.getWidth() / 2) {
			minimumTranslationVector.normal.x *= -1;
		}
		if (r1.y + r1.getHeight() / 2 < r2.y + r2.getHeight() / 2) {
			minimumTranslationVector.normal.y *= -1;
		}
		return minimumTranslationVector;
	}

	public List<Entity> getCollidingBlocks(List<Entity> blocks,
			Rectangle boundingBox) {
		List<Entity> result = new ArrayList<Entity>();

		for (Entity block : blocks) {

			if (boundingBox.overlaps(block.getBoundingBox())) {
				result.add(block);
			}
		}
		return result;
	}

}

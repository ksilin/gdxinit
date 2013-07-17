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
import com.badlogic.gdx.utils.Array;

public class Collider {

	Array<Block> collidableBlocks = new Array<Block>();

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

	public List<Collision> predictCollisions(List<Block> blocks, Entity entity,
			float delta) {
		List<Collision> result = new ArrayList<Collision>();

		Rectangle predictedBoundingBox = predictBoundingBox(entity, delta);
		result = getCollisions(blocks, predictedBoundingBox,
				entity.getVelocity());

		return result;
	}

	public void resolveCollisions(List<Block> blocks, Avatar avatar, float delta) {

		float tempDelta = delta;
		for (Block block : blocks) {

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

	private float pushBackEntity(Entity entity, Rectangle predictedBoundingBox,
			Rectangle blockBoundingBox, float delta) {
		float tempDelta = delta;
		if (!predictBoundingBox(entity, -tempDelta).overlaps(blockBoundingBox)) {
			return tempDelta * -1.1f;
		}
		while (predictedBoundingBox.overlaps(blockBoundingBox)) {
			tempDelta -= 0.0001;
			predictedBoundingBox = predictBoundingBox(entity, tempDelta);
		}
		tempDelta -= 0.0005;
		return tempDelta;
	}

	private float pushBackEntity2(Entity entity,
			Rectangle predictedBoundingBox, Rectangle blockBoundingBox,
			float delta) {

		Vector2 pushDirection = new Vector2(predictedBoundingBox.x
				- blockBoundingBox.x, predictedBoundingBox.y
				- blockBoundingBox.y);

		Vector2 velocity = entity.getVelocity();
		if (Math.abs(velocity.x) > 0.01) {
			velocity.x += pushDirection.x;
		}
		if (Math.abs(velocity.y) > 0.01) {
			velocity.y += pushDirection.y;
		}

		return delta;
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

	public List<Collision> getCollisions(List<Block> blocks,
			Rectangle boundingBox, Vector2 velocity) {

		List<Collision> collisions = new ArrayList<Collision>();
		List<Block> collidingBlocks = getCollidingBlocks(blocks, boundingBox);
		for (Block block : collidingBlocks) {
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

	public List<Block> getCollidingBlocks(List<Block> blocks,
			Rectangle boundingBox) {
		List<Block> result = new ArrayList<Block>();

		for (Block block : blocks) {

			if (boundingBox.overlaps(block.getBoundingBox())) {
				result.add(block);
			}
		}
		return result;
	}

}

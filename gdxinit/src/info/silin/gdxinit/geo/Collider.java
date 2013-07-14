package info.silin.gdxinit.geo;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;

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

	public Rectangle predictBoundingBox(Avatar avatar, float delta) {
		Rectangle result = new Rectangle(avatar.getBoundingBox());
		Vector2 velocity = avatar.getVelocity().cpy().mul(delta);
		result.x += velocity.x;
		result.y += velocity.y;
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
				tempDelta = pushBackAvatar3(avatar, predictedBoundingBox,
						bounds, tempDelta);
				Gdx.app.log("Collider", "tempDelta: " + tempDelta);
			}
		}
		avatar.update(tempDelta);
	}

	private float pushBackAvatar(Avatar avatar, Rectangle predictedBoundingBox,
			Rectangle blockBoundingBox, float delta) {
		float tempDelta = delta;
		if (!predictBoundingBox(avatar, -tempDelta).overlaps(blockBoundingBox)) {
			return tempDelta * -1.1f;
		}
		while (predictedBoundingBox.overlaps(blockBoundingBox)) {
			tempDelta -= 0.0001;
			predictedBoundingBox = predictBoundingBox(avatar, tempDelta);
		}
		tempDelta -= 0.0005;
		return tempDelta;
	}

	private float pushBackAvatar2(Avatar avatar,
			Rectangle predictedBoundingBox, Rectangle blockBoundingBox,
			float delta) {

		Vector2 pushDirection = new Vector2(predictedBoundingBox.x
				- blockBoundingBox.x, predictedBoundingBox.y
				- blockBoundingBox.y);

		Vector2 velocity = avatar.getVelocity();
		if (Math.abs(velocity.x) > 0.01) {
			velocity.x += pushDirection.x;
		}
		if (Math.abs(velocity.y) > 0.01) {
			velocity.y += pushDirection.y;
		}

		return delta;
	}

	private float pushBackAvatar3(Avatar avatar,
			Rectangle predictedBoundingBox, Rectangle blockBoundingBox,
			float delta) {

		MinimumTranslationVector minimumTranslationVector = new MinimumTranslationVector();
		Intersector.overlapConvexPolygons(
				GeoFactory.fromRectangle(predictedBoundingBox),
				GeoFactory.fromRectangle(blockBoundingBox),
				minimumTranslationVector);

		Vector2 multiplied = minimumTranslationVector.normal.cpy().mul(
				minimumTranslationVector.depth);
		avatar.getPosition().add(multiplied);

		return delta;
	}

	public List<Collision> getCollisions(List<Block> blocks, Avatar avatar,
			float delta) {

		List<Collision> collisions = new ArrayList<Collision>();

		List<Block> collidingBlocks = getCollidingBlocks(blocks, avatar, delta);
		for (Block block : collidingBlocks) {
			collisions.add(new Collision(avatar.getBoundingBox(), avatar
					.getVelocity(), block.getBoundingBox(), new Vector2()));
		}
		return collisions;
	}

	public List<Block> getCollidingBlocks(List<Block> blocks, Avatar avatar,
			float delta) {
		List<Block> result = new ArrayList<Block>();
		Rectangle nextFrameBB = predictBoundingBox(avatar, delta);
		Rectangle currentBoundingBox = avatar.getBoundingBox();

		for (Block block : blocks) {

			Rectangle bounds = block.getBoundingBox();
			if (nextFrameBB.overlaps(bounds)) {
				result.add(block);
			} else {
				if (currentBoundingBox.overlaps(bounds)) {
					result.add(block);
				}
			}
		}
		return result;
	}

}

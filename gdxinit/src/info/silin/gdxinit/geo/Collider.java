package info.silin.gdxinit.geo;

import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Avatar;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
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
				Gdx.app.log("Collider", "Collider colliding with block at "
						+ block.getPosition());
				tempDelta = pushBackAvatar(avatar, predictedBoundingBox, block,
						tempDelta);
				Gdx.app.log("Collider", "tempDelta: " + tempDelta);
			}
		}
		avatar.update(tempDelta);
	}

	private float pushBackAvatar(Avatar avatar, Rectangle predictedBoundingBox,
			Block block, float delta) {
		float tempDelta = delta;
		while (predictedBoundingBox.overlaps(block.getBoundingBox())) {
			tempDelta -= 0.0001;
			predictedBoundingBox = predictBoundingBox(avatar, tempDelta);
		}
		tempDelta -= 0.0005;
		return tempDelta;
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

package info.silin.gdxinit.geo;

import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Avatar;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Collider {

	Array<Block> collidableBlocks = new Array<Block>();

	public List<Block> selectCollidableBlocks(List<Block> blocks, Rectangle rect) {
		return blocks;
	}

	public Rectangle predictBoundingBox(Avatar bob, float delta) {
		Rectangle result = new Rectangle(bob.getBoundingBox());
		Vector2 velocity = bob.getVelocity().cpy().mul(delta);
		result.x += velocity.x;
		result.y += velocity.y;
		return result;
	}

	public List<Collision> getCollisions(List<Block> blocks, Avatar bob,
			float delta) {

		List<Collision> collisions = new ArrayList<Collision>();

		List<Block> collidingBlocks = getCollidingBlocks(blocks, bob, delta);
		for (Block block : collidingBlocks) {
			collisions.add(new Collision(bob.getBoundingBox(), bob
					.getVelocity(), block.getBoundingBox(), new Vector2()));
		}
		return collisions;
	}

	public List<Block> getCollidingBlocks(List<Block> blocks, Avatar bob,
			float delta) {
		List<Block> result = new ArrayList<Block>();
		Rectangle nextFrameBB = predictBoundingBox(bob, delta);
		Rectangle currentBoundingBox = bob.getBoundingBox();
		
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

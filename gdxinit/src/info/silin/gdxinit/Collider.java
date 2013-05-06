package info.silin.gdxinit;

import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Bob;

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

	public Rectangle createNextFrameBB(Bob bob, float delta) {
		Rectangle result = new Rectangle(bob.getBounds());
		Vector2 pos = bob.getPosition();
		Vector2 velocity = bob.getVelocity().cpy().mul(delta*10);
		result.x += pos.x + velocity.x;
		result.y += pos.y + velocity.y;
		return result;
	}

	public List<Block> getCollidingBlocks(List<Block> blocks, Bob bob,
			float delta) {
		List<Block> result = new ArrayList<Block>();

		Rectangle nextFrameBB = createNextFrameBB(bob, delta);
		for (Block block : blocks) {
			if (nextFrameBB.overlaps(block.getBounds())) {
				result.add(block);
			}
		}
		return result;
	}

}

package info.silin.gdxinit.geo;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Vehicle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Collider {

	public List<Block> selectCollidableBlocks(List<Block> blocks, Rectangle rect) {
		return blocks;
	}

	public static Rectangle predictBoundingBox(Vehicle entity, float delta) {
		Rectangle result = new Rectangle(entity.getBoundingBox());
		Vector2 velocity = entity.getVelocity().cpy().mul(delta);
		result.x += velocity.x;
		result.y += velocity.y;
		return result;
	}

	public static List<Collision> predictCollisions(List<Entity> obstacles,
			Vehicle entity, float delta) {
		List<Collision> result = new ArrayList<Collision>();

		Rectangle predictedBoundingBox = predictBoundingBox(entity, delta);
		result = getCollisions(obstacles, entity, predictedBoundingBox,
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

	// passing the entity and the bounding box because the bounding box may be a
	// prediction
	public static List<Collision> getCollisions(List<Entity> obstacles,
			Entity entity, Rectangle boundingBox, Vector2 velocity) {

		List<Collision> collisions = new ArrayList<Collision>();
		List<Entity> collidingObstacles = getCollidingEntities(obstacles,
				boundingBox);
		for (Entity obstacle : collidingObstacles) {

			Collision c = new Collision(obstacle, entity, boundingBox,
					velocity, obstacle.getBoundingBox(), new Vector2(),
					calcTranslationVector(boundingBox,
							obstacle.getBoundingBox()));
			collisions.add(c);
		}
		return collisions;
	}

	private static MinimumTranslationVector calcTranslationVector(Rectangle r1,
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

	public static List<Entity> getCollidingEntities(List<Entity> obstacles,
			Rectangle boundingBox) {

		List<Entity> result = new ArrayList<Entity>();
		for (Entity e : obstacles) {
			if (boundingBox.overlaps(e.getBoundingBox())) {
				result.add(e);
			}
		}
		return result;
	}

	public static List<Entity> getCollidingEntities(List<Entity> obstacles,
			Polygon poly) {

		List<Entity> result = new ArrayList<Entity>();
		for (Entity e : obstacles) {
			if (Intersector.overlapConvexPolygons(poly, e.getPolygon())) {
				result.add(e);
			}
		}
		return result;
	}

	public static Collision getCollision(Entity obstacle, Vehicle e, float delta) {

		Rectangle boundingBox = e.getBoundingBox();
		if (obstacle.getBoundingBox().overlaps(boundingBox)) {
			return new Collision(obstacle, e, boundingBox, e.getVelocity(),
					obstacle.getBoundingBox(), new Vector2(), null);
		}
		return null;
	}
}

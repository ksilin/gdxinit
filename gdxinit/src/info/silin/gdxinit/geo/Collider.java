package info.silin.gdxinit.geo;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Vehicle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Collider {

	public List<Block> selectCollidableBlocks(List<Block> blocks, Rectangle rect) {
		return blocks;
	}

	public static Rectangle predictBoundingBox(Vehicle v, float delta) {
		Rectangle result = new Rectangle(v.getBoundingBox());
		Vector2 velocity = v.getVelocity().cpy().mul(delta);
		result.x += velocity.x;
		result.y += velocity.y;
		return result;
	}

	public static List<Collision> predictCollisions(List<Entity> obstacles,
			Vehicle v, float delta) {
		List<Collision> result = new ArrayList<Collision>();

		Rectangle predictedBoundingBox = predictBoundingBox(v, delta);
		result = getCollisions(obstacles, v, predictedBoundingBox,
				v.getVelocity());

		return result;
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

	public static Collision getCollision(Entity obstacle, Vehicle v, float delta) {

		Rectangle boundingBox = v.getBoundingBox();
		if (obstacle.getBoundingBox().overlaps(boundingBox)) {
			return new Collision(obstacle, v, boundingBox, v.getVelocity(),
					obstacle.getBoundingBox(), new Vector2(), null);
		}
		return null;
	}

	public static void pushBack(Vehicle entity, float delta) {
		List<Collision> collisions = predictCollisions(World.INSTANCE
				.getLevel().getNonNullBlocks(), entity, delta);
		for (Collision c : collisions) {
			MinimumTranslationVector translation = c.getTranslation();
			entity.getPosition().add(translation.normal.x * translation.depth,
					translation.normal.y * translation.depth);
		}
	}

}

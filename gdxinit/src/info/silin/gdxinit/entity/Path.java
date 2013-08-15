package info.silin.gdxinit.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Path {

	private List<Vector2> waypoints = new ArrayList<Vector2>();

	public List<Vector2> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<Vector2> waypoints) {
		this.waypoints = waypoints;
	}
}

package info.silin.gdxinit.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Path {

	private List<Vector2> waypoints = new ArrayList<Vector2>();

	private int currentIndex = 0;

	public List<Vector2> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<Vector2> waypoints) {
		this.waypoints = waypoints;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public Vector2 getCurrentWaypoint() {
		return waypoints.get(currentIndex);
	}

	public void next() {
		if (getCurrentIndex() == getWaypoints().size() - 1) {
			setCurrentIndex(0);
		} else {
			setCurrentIndex(getCurrentIndex() + 1);
		}
	}
}

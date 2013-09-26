package info.silin.gdxinit;

import com.badlogic.gdx.Gdx;

public class Levels {

	public static final DemoLevel DEMO = new DemoLevel();
	public static final WeaponsLabLevel WEAPONS_LAB = new WeaponsLabLevel();

	private static Level CURRENT = null;

	public static Level getCurrent() {
		return CURRENT;
	}

	public static void setCurrent(Level newLevel) {
		CURRENT = newLevel;
	}

	public static void startLevel(Level newLevel) {
		setCurrent(newLevel);
		newLevel.init();
		Gdx.app.log("World", "starting level " + newLevel);
	}
}

package info.silin.gdxinit.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

	public static Sound steps;
	public static Sound reload;

	public static void load() {
		steps = Gdx.audio.newSound(Gdx.files.internal("sounds/footsteps.mp3"));
		reload = Gdx.audio.newSound(Gdx.files.internal("sounds/reload.ogg"));
	}

	public static void dispose() {
		steps.dispose();
		reload.dispose();
	}

}

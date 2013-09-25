package info.silin.gdxinit.events;

import com.badlogic.gdx.Screen;

public class ScreenChangeEvent {

	private Screen newScreen;

	public ScreenChangeEvent(Screen newScreen) {
		this.newScreen = newScreen;
	}

	public Screen getNewScreen() {
		return newScreen;
	}

}

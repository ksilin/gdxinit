package info.silin.gdxinit.events;

import com.badlogic.gdx.Gdx;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public enum Events {

	INSTANCE;

	private final EventBus bus = new EventBus("app");

	public EventBus getBus() {
		return bus;
	}

	@Subscribe
	public void listen(DeadEvent event) {
		Gdx.app.log("Events", "DeadEvent received");
	}

	public static void register(Object listener) {
		INSTANCE.bus.register(listener);
	}
	public static void post(Object event) {
		INSTANCE.bus.post(event);
	}
}

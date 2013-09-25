package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.events.PauseEvent;
import info.silin.gdxinit.renderer.RendererController;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;

public class InputEventHandler extends InputMultiplexer {

	private WorldController controller;
	private RendererController renderer;

	private static boolean touched = false;
	private static int touchingPointerIndex;

	// regardless of platform
	public static boolean enforcingAndroidInput = false;

	public InputEventHandler(WorldController controller,
			RendererController renderer) {
		this.controller = controller;
		this.renderer = renderer;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.D) {
			renderer.setDebug(!renderer.isDebug());
		}
		if (keycode == Keys.M) {
			controller.setManualStep(!controller.isManualStep());
		}
		if (keycode == Keys.N) {
			controller.step();
		}
		if (keycode == Keys.ESCAPE) {
			Events.post(new PauseEvent());
		}
		if (keycode == Keys.V) {
			setEnforcingAndroidInput(!enforcingAndroidInput);
			if (enforcingAndroidInput) {
				RendererController.uiRenderer.showAndroidUI();
			} else {
				RendererController.uiRenderer.hideAndroidUI();
			}
		}

		// cam movement
		if (keycode == Keys.I) {
			renderer.moveCamUp();
		}
		if (keycode == Keys.K) {
			renderer.moveCamDown();
		}
		if (keycode == Keys.J) {
			renderer.moveCamLeft();
		}
		if (keycode == Keys.L) {
			renderer.moveCamRight();
		}
		// cam rotation
		if (keycode == Keys.Z) {
			renderer.rotateCamCCW();
		}
		if (keycode == Keys.U) {
			renderer.rotateCamCW();
		}
		// cam zoom
		if (keycode == Keys.O) {
			renderer.zoomOut();
		}
		if (keycode == Keys.P) {
			renderer.zoomIn();
		}
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return super.keyUp(keycode);
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		touched = true;
		touchingPointerIndex = pointer;
		return super.touchDown(x, y, pointer, button);
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		touched = false;
		touchingPointerIndex = -1;
		return super.touchUp(x, y, pointer, button);
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return super.touchDragged(x, y, pointer);
	}

	public static void processAvatarInput() {
		Avatar avatar = World.INSTANCE.getAvatar();

		if (!isUsingAndroidInput()) {
			Vector2 force = new Vector2();
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				force.x -= avatar.getMaxForce();
			}
			if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				force.x += avatar.getMaxForce();

			}
			if (Gdx.input.isKeyPressed(Keys.UP)) {
				force.y += avatar.getMaxForce();

			}
			if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				force.y -= avatar.getMaxForce();
			}
			avatar.setForce(force);
		}
	}

	public static boolean isUsingAndroidInput() {
		if (ApplicationType.Android == Gdx.app.getType())
			return true;
		if (enforcingAndroidInput)
			return true;
		return false;
	}

	public static boolean isEnforcingAndroidInput() {
		return enforcingAndroidInput;
	}

	public static void setEnforcingAndroidInput(boolean usingAndroidInput) {
		InputEventHandler.enforcingAndroidInput = usingAndroidInput;
	}
}

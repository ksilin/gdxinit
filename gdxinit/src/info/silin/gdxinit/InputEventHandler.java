package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.renderer.RendererController;
import info.silin.gdxinit.screens.MenuScreen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class InputEventHandler extends InputMultiplexer {

	private WorldController controller;
	private RendererController renderer;
	private Vector2 factor = new Vector2(1, 1);
	private int height;

	private static boolean fireButtonWasPressed;

	// regardless of platform
	public static boolean enforcingAndroidInput = true;

	private static final float AVATAR_ACCELERATION = 20f;

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
			backToMenu();
		}

		if (keycode == Keys.B) {
			controller.togglePause();
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
		return super.touchDown(x, y, pointer, button);
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return super.touchUp(x, y, pointer, button);
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return super.touchDragged(x, y, pointer);
	}

	public void setSize(int width, int height) {
		this.height = height;
		this.factor = new Vector2(World.WIDTH / width, World.HEIGHT / height);
	}

	public float toWorldX(float x) {
		return x * factor.x;
	}

	public float toWorldY(float y) {
		return (height - y) * factor.y;
	}

	public void backToMenu() {
		GameMain.instance.setScreen(new MenuScreen());
	}

	public static void processInput() {
		Avatar avatar = World.INSTANCE.getAvatar();

		// desktop controls
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			avatar.setFacingLeft(true);
			avatar.setState(Avatar.State.WALKING);
			avatar.getAcceleration().x = -AVATAR_ACCELERATION;

		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			avatar.setFacingLeft(false);
			avatar.setState(Avatar.State.WALKING);
			avatar.getAcceleration().x = AVATAR_ACCELERATION;

		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			avatar.setFacingLeft(true);
			avatar.setState(Avatar.State.WALKING);
			avatar.getAcceleration().y = AVATAR_ACCELERATION;

		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			avatar.setFacingLeft(false);
			avatar.setState(Avatar.State.WALKING);
			avatar.getAcceleration().y = -AVATAR_ACCELERATION;
		} else {
			avatar.setState(Avatar.State.IDLE);
			avatar.getAcceleration().x = 0;
		}

		if (isUsingAndroidInput()) {
			processJoystick(avatar);
		}

		processFireControl(avatar);
	}

	private static void processFireControl(Avatar avatar) {

		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {

			if (isUsingAndroidInput()
					&& RendererController.uiRenderer.leftJoystick.isTouched())
				return;

			fireButtonWasPressed = true;
			avatar.shoot(RendererController.getUnprojectedMousePosition());
		} else if (fireButtonWasPressed) {
			avatar.shoot(RendererController.getUnprojectedMousePosition());
			fireButtonWasPressed = false;
		}
	}

	private static void processJoystick(Avatar avatar) {
		Touchpad leftJoystick = RendererController.uiRenderer.leftJoystick;
		if (leftJoystick.isTouched()) {

			avatar.getAcceleration().x = AVATAR_ACCELERATION
					* leftJoystick.getKnobPercentX();
			avatar.getAcceleration().y = AVATAR_ACCELERATION
					* leftJoystick.getKnobPercentY();
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

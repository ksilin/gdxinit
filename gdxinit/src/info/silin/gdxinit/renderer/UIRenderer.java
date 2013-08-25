package info.silin.gdxinit.renderer;

import info.silin.gdxinit.InputEventHandler;
import info.silin.gdxinit.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UIRenderer {

	public Stage stage;
	public Skin skin;
	public Touchpad leftJoystick;

	Button restartLevelButton;
	Button resumeButton;

	public static float BUTTON_WIDTH = 0.25f;
	public static float BUTTON_HEIGHT = 0.1f;
	public static float TOUCHPAD_RAD = 0.17f;

	public UIRenderer() {

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		skin = new Skin(Gdx.files.internal("data/myskin.json"));
		stage = new Stage(width, height, false); // setting the real size over
													// #setSize()
		createLeftJoystick(width, height);
		createEndLevelDialog(width, height);

		setPlatformDependentUIVisibility();
	}

	private void setPlatformDependentUIVisibility() {
		hideAndroidUI();
		if (InputEventHandler.isUsingAndroidInput())
			showAndroidUI();
	}

	private void createEndLevelDialog(int width, int height) {
		restartLevelButton = new TextButton("Restart level", skin, "default");
		float centerX = width / 2f - width * UIRenderer.BUTTON_WIDTH * 0.5f;
		restartLevelButton.setPosition(centerX, height / 2f);
		restartLevelButton.setSize(width * UIRenderer.BUTTON_WIDTH, height
				* UIRenderer.BUTTON_HEIGHT);
		ClickListener restartListener = new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				World.INSTANCE.restartCurrentLevel();
				World.INSTANCE.setState(World.State.RUNNING);
				hideEndLevelDialog();
				super.clicked(event, x, y);
			}
		};
		restartLevelButton.addListener(restartListener);
		restartLevelButton.setVisible(false);
		stage.addActor(restartLevelButton);

		resumeButton = new TextButton("Resume", skin, "default");
		resumeButton.setPosition(centerX, height * 0.7f);
		resumeButton.setSize(width * UIRenderer.BUTTON_WIDTH, height
				* UIRenderer.BUTTON_HEIGHT);
		ClickListener resumeListener = new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				World.INSTANCE.setState(World.State.RUNNING);
				hideEndLevelDialog();
				super.clicked(event, x, y);
			}
		};
		resumeButton.addListener(resumeListener);
		resumeButton.setVisible(false);
		stage.addActor(resumeButton);
	}

	private void createLeftJoystick(int width, int height) {
		leftJoystick = new Touchpad(5, skin);
		leftJoystick.setSize(width * TOUCHPAD_RAD, width * TOUCHPAD_RAD);
		leftJoystick.setPosition(width / 5 - (width * TOUCHPAD_RAD) / 2, height
				/ 5 - (height * TOUCHPAD_RAD) / 2);

		leftJoystick.addListener(new InputListener() {

			// @Override
			// public boolean touchDown(InputEvent event, float x, float y,
			// int pointer, int button) {
			// Gdx.app.log("joystick", "touchDown");
			// return super.touchDown(event, x, y, pointer, button);
			// }

			// TODO - why is touchUp never called, neither when releasing the
			// mouse inside or outside the actor, on desktop or device?
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.log("joystick", "touchUp");
				super.touchUp(event, x, y, pointer, button);
			}

			// TODO - why is touchDragged never called, neither on desktop or
			// device?
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				Gdx.app.log("joystick", "touchDragged");
				super.touchDragged(event, x, y, pointer);
			}

			// @Override
			// public boolean mouseMoved(InputEvent event, float x, float y) {
			// Gdx.app.log("joystick", "mouseMoved");
			// return super.mouseMoved(event, x, y);
			// }

			// always called immediately after toushDown -> get rid of toudhDown
			@Override
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				Gdx.app.log("joystick", "enter");
				super.enter(event, x, y, pointer, fromActor);
			}

			// is called instead of touchUp
			@Override
			public void exit(InputEvent event, float x, float y, int pointer,
					Actor toActor) {
				Gdx.app.log("joystick", "exit");
				super.exit(event, x, y, pointer, toActor);
			}

			// never called
			@Override
			public boolean scrolled(InputEvent event, float x, float y,
					int amount) {
				Gdx.app.log("joystick", "scrolled");
				return super.scrolled(event, x, y, amount);
			}
		});
		stage.addActor(leftJoystick);
	}

	public void draw(float delta) {
		stage.act(delta);
		stage.draw();
	}

	public void setSize(int width, int height) {
		stage.setViewport(width, height, false);
	}

	public void showEndLevelDialog() {
		restartLevelButton.setVisible(true);
		resumeButton.setVisible(true);
	}

	public void hideEndLevelDialog() {
		restartLevelButton.setVisible(false);
		resumeButton.setVisible(false);
	}

	public void hideAndroidUI() {
		leftJoystick.setVisible(false);
	}

	public void showAndroidUI() {
		leftJoystick.setVisible(true);
	}

}

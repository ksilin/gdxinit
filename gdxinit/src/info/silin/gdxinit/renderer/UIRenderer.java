package info.silin.gdxinit.renderer;

import info.silin.gdxinit.InputEventHandler;
import info.silin.gdxinit.World;
import info.silin.gdxinit.ui.AvatarJoystick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UIRenderer {

	public Stage stage;
	public Skin skin;
	public AvatarJoystick leftJoystick;

	private Label debugInfo;
	private Label fpsLabel;

	private Button restartLevelButton;
	private Button resumeButton;

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
		createDebugInfo();
		createFpsLabel();
		setPlatformDependentUIVisibility();
	}

	private Label createFpsLabel() {
		fpsLabel = new Label("fps label", skin);
		fpsLabel.setPosition(Gdx.graphics.getWidth() - 25, 25);
		fpsLabel.setColor(0.9f, 0.9f, 0.9f, 1f);
		fpsLabel.setSize(100, 100);
		stage.addActor(fpsLabel);
		return fpsLabel;
	}

	private Label createDebugInfo() {
		debugInfo = new Label("debug label", skin);
		debugInfo.setPosition(0, Gdx.graphics.getHeight() / 2);
		debugInfo.setColor(0.8f, 0.8f, 0.2f, 1f);
		debugInfo.setSize(100, 100);
		stage.addActor(debugInfo);
		return debugInfo;
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
		leftJoystick = new AvatarJoystick(5, skin);
		leftJoystick.setSize(width * TOUCHPAD_RAD, width * TOUCHPAD_RAD);
		leftJoystick.setPosition(width / 10 - (width * TOUCHPAD_RAD) / 2,
				height / 10 - (height * TOUCHPAD_RAD) / 2);
		stage.addActor(leftJoystick);
	}

	public void draw(float delta) {
		stage.act(delta);
		fpsLabel.setText(Integer.toString(Gdx.graphics.getFramesPerSecond()));
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

	public Label getDebugInfo() {
		return debugInfo;
	}

	public void setDebugInfo(Label debugInfo) {
		this.debugInfo = debugInfo;
	}

	public void showDebugUI() {
		debugInfo.setVisible(true);
	}

	public void hideDebugUI() {
		debugInfo.setVisible(false);
	}
}

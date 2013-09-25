package info.silin.gdxinit.renderer;

import info.silin.gdxinit.GameMain;
import info.silin.gdxinit.InputEventHandler;
import info.silin.gdxinit.World;
import info.silin.gdxinit.events.AvatarDeadEvent;
import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.events.LevelCompletedEvent;
import info.silin.gdxinit.events.PauseEvent;
import info.silin.gdxinit.events.ResumeEvent;
import info.silin.gdxinit.events.ScreenChangeEvent;
import info.silin.gdxinit.ui.AvatarJoystick;
import info.silin.gdxinit.ui.MyDialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.common.eventbus.Subscribe;

public class UIRenderer {

	private static final String UISKIN = "data/uiskin32.json";
	private static final String DEBUG_LABEL = "debug label";
	private static final String FPS_LABEL = "fps label";
	private static final String SUCCESS = "excellent!";
	private static final String KILLED = "the system has crushed you";
	private static final String PAUSED = "paused";
	private static final String MENU = "Main menu";
	private static final String RESTART = "Restart";
	private static final String RESUME = "Resume";
	public Stage stage;
	private Skin skin;
	private AvatarJoystick joystick;

	private Label debugInfo;
	private Label fpsLabel;

	MyDialog afterDeathDialog;
	MyDialog successDialog;
	MyDialog pauseDialog;

	private static float TOUCHPAD_RAD = 0.17f;

	private enum DialogResults {
		RESUME, RESTART_LEVEL, MENU;
	}

	public UIRenderer() {

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		skin = new Skin(Gdx.files.internal(UISKIN));
		stage = new Stage(width, height, false); // setting the real size over
													// #setSize()
		createJoystick(width, height);
		createDebugInfo();
		createFpsLabel();
		createDialogs();
		setPlatformDependentUIVisibility();
		Events.register(this);
	}

	private void createJoystick(int width, int height) {
		joystick = new AvatarJoystick(5, skin);
		joystick.setSize(width * TOUCHPAD_RAD, width * TOUCHPAD_RAD);
		joystick.setPosition(width / 10 - (width * TOUCHPAD_RAD) / 2, height
				/ 10 - (height * TOUCHPAD_RAD) / 2);
		stage.addActor(joystick);
	}

	private Label createDebugInfo() {
		debugInfo = new Label(DEBUG_LABEL, skin);
		debugInfo.setPosition(0, Gdx.graphics.getHeight() / 2);
		debugInfo.setColor(0.8f, 0.8f, 0.2f, 1f);
		debugInfo.setSize(100, 100);
		stage.addActor(debugInfo);
		return debugInfo;
	}

	private Label createFpsLabel() {
		fpsLabel = new Label(FPS_LABEL, skin);
		fpsLabel.setPosition(Gdx.graphics.getWidth() - 25, 25);
		fpsLabel.setColor(0.9f, 0.9f, 0.9f, 1f);
		fpsLabel.setSize(100, 100);
		stage.addActor(fpsLabel);
		return fpsLabel;
	}

	private void createDialogs() {
		createPauseDialog();
		createSuccessDialog();
		createAfterDeathDialog();
	}

	private void createSuccessDialog() {
		successDialog = createDialog();
		successDialog.text(SUCCESS)
				.button(RESTART, DialogResults.RESTART_LEVEL)
				.button(MENU, DialogResults.MENU)
				.key(Keys.ENTER, DialogResults.RESTART_LEVEL)
				.key(Keys.ESCAPE, DialogResults.MENU);
	}

	private void createPauseDialog() {
		pauseDialog = createDialog();
		pauseDialog.text(PAUSED).button(RESUME, DialogResults.RESUME)
				.button(RESTART, DialogResults.RESTART_LEVEL)
				.button(MENU, DialogResults.MENU)
				.key(Keys.ENTER, DialogResults.RESTART_LEVEL)
				.key(Keys.ESCAPE, DialogResults.RESUME);
	}

	private void createAfterDeathDialog() {
		afterDeathDialog = createDialog();
		afterDeathDialog.text(KILLED)
				.button(RESTART, DialogResults.RESTART_LEVEL)
				.button(MENU, DialogResults.MENU)
				.key(Keys.ENTER, DialogResults.RESTART_LEVEL)
				.key(Keys.ESCAPE, DialogResults.MENU);
	}

	private MyDialog createDialog() {
		return new MyDialog("", skin, "dialog") {
			@Override
			protected void result(Object result) {
				actOnDialogResult((DialogResults) result);
			}
		};
	}

	private void actOnDialogResult(DialogResults result) {

		switch (result) {
		// we dont need a special case for RESUME - the game is always unpaused
		case MENU:
			Events.post(new ScreenChangeEvent(GameMain.MENU_SCREEN));
			break;
		case RESTART_LEVEL:
			World.INSTANCE.resetCurrentLevel();
			break;
		default:
			break;
		}
		Events.post(new ResumeEvent());
	}

	private void setPlatformDependentUIVisibility() {
		hideAndroidUI();
		if (InputEventHandler.isUsingAndroidInput())
			showAndroidUI();
	}

	public void draw(float delta) {
		stage.act(delta);
		fpsLabel.setText(Integer.toString(Gdx.graphics.getFramesPerSecond()));
		stage.draw();
	}

	@Subscribe
	public void showSuccessDialog(LevelCompletedEvent e) {
		successDialog.show(stage);
	}

	@Subscribe
	public void showPauseDialog(PauseEvent e) {
		pauseDialog.show(stage);
	}

	@Subscribe
	public void showAfterDeathDialog(AvatarDeadEvent e) {
		afterDeathDialog.show(stage);
	}

	public void hideAndroidUI() {
		joystick.setVisible(false);
	}

	public void showAndroidUI() {
		joystick.setVisible(true);
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

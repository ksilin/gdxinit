package info.silin.gdxinit.ui;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class AvatarJoystick extends Touchpad {

	public AvatarJoystick(float deadzoneRadius, Skin skin) {
		super(deadzoneRadius, skin);
	}

	@Override
	public void act(float delta) {
		if (isTouched()) {
			Vector2 acceleration = World.INSTANCE.getAvatar().getAcceleration();
			acceleration.x = Avatar.MAX_ACC * getKnobPercentX();
			acceleration.y = Avatar.MAX_ACC * getKnobPercentY();
		}
		super.act(delta);
	}

	public static class TouchpadStyle {
		/** Stretched in both directions. Optional. */
		public Drawable background;

		/** Optional. */
		public Drawable knob;

		public TouchpadStyle() {
		}

		public TouchpadStyle(Drawable background, Drawable knob) {
			this.background = background;
			this.knob = knob;
		}

		public TouchpadStyle(TouchpadStyle style) {
			this.background = style.background;
			this.knob = style.knob;
		}
	}
}

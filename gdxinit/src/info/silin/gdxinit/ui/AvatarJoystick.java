package info.silin.gdxinit.ui;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.state.Idle;
import info.silin.gdxinit.entity.state.Walking;

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
		Avatar avatar = World.INSTANCE.getAvatar();
		if (isTouched()) {
			avatar.setState(Walking.getINSTANCE());
			float forceX = avatar.getMaxForce() * getKnobPercentX();
			float forceY = avatar.getMaxForce() * getKnobPercentY();
			Vector2 force = new Vector2(forceX, forceY);
			avatar.setForce(force);

		} else {
			avatar.setState(Idle.getINSTANCE());
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

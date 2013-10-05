package info.silin.gdxinit.entity.state.boid;

import info.silin.gdxinit.entity.Boid;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.entity.steering.Steering;
import info.silin.gdxinit.renderer.RendererController;

import com.badlogic.gdx.math.Vector2;

public class SeekingMouse extends State<Boid> {

	@Override
	public void enter(Boid entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Boid enemy, float delta) {
		goToMouse(enemy, delta);
		super.execute(enemy, delta);
	}

	private void goToMouse(Boid v, float delta) {
		Vector2 targetPos = RendererController.getUnprojectedMousePosition();
		Vector2 targetForce = Steering.seek(targetPos, v);
		v.setTargetPos(targetPos);
		v.setForce(targetForce);
	}

	@Override
	public void exit(Boid entity) {
		super.exit(entity);
	}
}

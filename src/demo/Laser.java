package demo;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.util.Point;

public class Laser extends Entity {
	public Laser(Point position, boolean side) {
		this.position.set(position);
		width = height = Res.laser.getWidth();
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_LEFT)) {
			angle -= side ? 90 : 30;
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_RIGHT)) {
			angle += side ? 90 : 30;
		}
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		position.moveAtAngle(0, -1000 * delta, angle);
		if (position.x < -100 || position.x > Game.getGameInstance().getWidth() + 100 || position.y < -100) {
			getParent().removeChild(this);
		}
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawImage(Res.laser);
	}
}
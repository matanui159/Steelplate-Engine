package demo;

import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.node.Node;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.util.math.Point;

public class Laser extends Node {
	private double speed;
	public Laser(Point position, boolean side) {
		this.position.setAll(position);
		width = Res.laser.getWidth();
		height = Res.laser.getHeight();
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_LEFT)) {
			angle -= side ? 90 : 30;
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_RIGHT)) {
			angle += side ? 90 : 30;
		}
		speed = Math.abs(angle) == 90 ? 1000 : 500;
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		position.moveAtAngle(0, -speed * delta, angle);
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawImage(Res.laser);
	}
}
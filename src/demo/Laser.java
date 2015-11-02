package demo;

import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.math.Point;
import com.redmintie.steelplate.node.Node;
import com.redmintie.steelplate.render.Canvas;

public class Laser extends Node {
	public Laser(Point position) {
		this.position.setAll(position);
		width = Res.laser.getWidth();
		height = Res.laser.getHeight();
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_LEFT)) {
			angle -= 30;
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_RIGHT)) {
			angle += 30;
		}
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		position.moveAtAngle(0, -500 * delta, angle);
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawImage(Res.laser);
	}
}
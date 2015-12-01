package demo.game;

import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.util.Point;

public class Star extends Entity {
	private Point speed = new Point(0, 0).moveAtAngle(0, Math.random() * 300, Math.random() * 360);
	private double aSpeed = Math.random() * 360 - 180;
	private double fSpeed;
	public Star(Point pos) {
		position.set(pos);
		angle = Math.random() * 360;
		
		width = Res.star.getWidth();
		height = Res.star.getHeight();
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		fSpeed += 10 * delta;
		position.add(speed.x * delta, speed.y * delta);
		angle += aSpeed * delta;
		position.moveTowards(-100, -100, fSpeed);
		
		if (position.x == -100 && position.y == -100) {
			Demo.score++;
			getParent().removeChild(this);
		}
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawImage(Res.star);
	}
}
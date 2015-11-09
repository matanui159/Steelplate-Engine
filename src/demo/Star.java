package demo;

import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.util.Point;

public class Star extends Entity {
	private Point speed = new Point(0, 0);
	private double aSpeed = Math.random() * 20 - 10;
	private double fSpeed;
	public Star(Point pos) {
		position.set(pos);
		angle = Math.random() * 360;
		
		width = Res.star.getWidth();
		height = Res.star.getHeight();
		
		speed.moveAtAngle(0, Math.random() * 300, Math.random() * 360);
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		addChild(new Particle(Res.star, position, angle));
		
		fSpeed += 10 * delta;
		position.add(speed.x * delta, speed.y * delta);
		angle += aSpeed;
		position.moveTowards(-100, -100, fSpeed);
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawImage(Res.star);
		canvas.resetMatrix();
	}
}
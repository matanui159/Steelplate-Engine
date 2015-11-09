package demo;

import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Image;
import com.redmintie.steelplate.util.Point;

public class Particle extends Entity {
	private Image image;
	private double alpha = 255;
	public Particle(Image image, Point position, double angle) {
		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
		this.position.set(position);
		this.angle = angle;
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		alpha -= 255 * 5 * delta;
		if (alpha <= 0) {
			getParent().removeChild(this);
		}
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.setAlpha((int)alpha);
		canvas.drawImage(image);
		canvas.setAlpha(255);
	}
}
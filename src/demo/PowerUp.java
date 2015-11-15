package demo;

import com.redmintie.steelplate.render.Canvas;

public class PowerUp extends DownMovingEntity {
	private static final int SHIELD = 0;
	private static final int STAR = 1;
	private static final int RAPID_FIRE = 2;
	private static final int PILL = 3;
	
	private int power;
	private double speed = Math.random() * 360 - 180;
	public PowerUp() {
		power = (int)(Math.random() * 1);
		width = Res.powers[power].getWidth();
		height = Res.powers[power].getHeight();
		angle = Math.random() * 360;
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		angle += speed * delta;
		
		Player player = Demo.player;
		if (getParent() != null && player.testOverlap(this)) {
			switch (power) {
			case SHIELD:
				if (player.shield < 3) {
					player.shield++;
				}
				break;
			}
			getParent().removeChild(this);
		}
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawImage(Res.powers[power]);
	}
}
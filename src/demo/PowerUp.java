package demo;

import com.redmintie.steelplate.render.Canvas;

public class PowerUp extends DownMovingEntity {
	private static final int SHIELD = 0;
	private static final int STAR = 1;
	private static final int RAPID_FIRE = 2;
	private static final int BURST = 3;
	private static final int PILL = 4;
	
	private int power;
	private double speed = Math.random() * 360 - 180;
	public PowerUp() {
		power = (int)(Math.random() * 5);
		width = Res.powers[power].getWidth();
		height = Res.powers[power].getHeight();
		angle = Math.random() * 360;
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		if (getParent() == null) {
			return;
		}
		angle += speed * delta;
		
		Player player = Demo.player;
		if (player.lives >= 0 &&  player.testOverlap(this)) {
			switch (power) {
			case SHIELD:
				if (player.shield < 3) {
					player.shield++;
				}
				break;
			case STAR:
				for (int i = 0; i < 50; i++) {
					Demo.stars.addChild(new Star(position));
				}
				break;
			case RAPID_FIRE:
				player.ammo = 30;
				player.reload = 0;
				player.count = -1;
				break;
			case BURST:
				player.ammo = 40;
				player.reload = 0;
				player.count = 0;
				break;
			case PILL:
				Demo.pill = 3;
				break;
			}
			Res.upSound.play();
			getParent().removeChild(this);
		}
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawImage(Res.powers[power]);
	}
}
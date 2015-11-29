package demo.game;

import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.render.Canvas;

public class Enemy extends DownMovingEntity {
	private static final long serialVersionUID = 0;
	
	public Enemy() {
		width = Res.enemy.getWidth();
		height = Res.enemy.getHeight();
	}
	@Override
	public void update(double delta) {
		if (Demo.pill > 0) {
			delta /= 2;
		}
		super.update(delta);
		if (getParent() == null) {
			return;
		}
		
		Player player = Demo.player;
		if (player.lives >= 0 && player.testOverlap(this)) {
			if (player.shield > 0) {
				player.shield--;
			} else {
				player.lives--;
			}
			Res.play(Res.downSound);
			getParent().removeChild(this);
		} else {
			for (Entity laser : player) {
				if (laser.testOverlap(this)) {
					for (int i = 0; i < 30; i++) {
						Demo.stars.addChild(new Star(position));
					}
					player.removeChild(laser);
					getParent().removeChild(this);
					break;
				}
			}
		}
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawImage(Res.enemy);
	}
}
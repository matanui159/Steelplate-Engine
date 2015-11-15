package demo;

import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.render.Canvas;

public class Enemy extends DownMovingEntity {
	public Enemy() {
		width = Res.enemy.getWidth();
		height = Res.enemy.getHeight();
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		
		Player player = Demo.player;
		if (getParent() != null) {
			if (player.lives >= 0 && player.testOverlap(this)) {
				if (player.shield > 0) {
					player.shield--;
				} else {
					player.lives--;
				}
				getParent().removeChild(this);
			} else {
				for (Entity laser : player) {
					if (laser.testOverlap(this)) {
						for (int i = 0; i < 30; i++) {
							Demo.stars.addChild(new Star(position));
						}
						player.removeChild(laser);
						getParent().removeChild(this);
					}
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
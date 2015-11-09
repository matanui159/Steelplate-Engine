package demo;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.render.Canvas;

public class Enemy extends Entity {
	public Enemy() {
		position.set(Math.random() * Game.getGameInstance().getWidth(), -100);
		width = Res.enemy.getWidth();
		height = Res.enemy.getHeight();
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		position.add(0, 300 * delta);
		
		Player player = Demo.player;
		if (position.y > Game.getGameInstance().getHeight() + 100) {
			getParent().removeChild(this);
		} else if (player.lives >= 0 && player.testOverlap(this)) {
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
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawImage(Res.enemy);
	}
}
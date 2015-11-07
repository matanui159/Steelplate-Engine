package demo;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.render.Canvas;

public class Enemy extends Entity {
	public Enemy() {
		position.setAll(Math.random() * Game.getGameInstance().getWidth(), -100);
		width = Res.enemy.getWidth();
		height = Res.enemy.getHeight();
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		position.add(0, 300 * delta);
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawImage(Res.enemy);
		canvas.resetMatrix();
	}
}
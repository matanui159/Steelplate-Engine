package demo.game;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.entity.Entity;

public class DownMovingEntity extends Entity {
	public DownMovingEntity() {
		position.set(Math.random() * Game.getGameInstance().getWidth(), -100);
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		position.add(0, 300 * delta);
		if (position.y > Game.getGameInstance().getHeight() + 100) {
			getParent().removeChild(this);
		}
	}
}
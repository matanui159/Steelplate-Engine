package demo.net;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.Mouse;

public class Player extends Entity {
	public Player() {
		position.set(Game.getGameInstance().getWidth() / 2, Game.getGameInstance().getHeight() / 2);
	}
	@Override
	public void update(double delta) {
		angle = position.getAngleTo(Mouse.getMouse().getX(), Mouse.getMouse().getY()) - 90;
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_A)) {
			position.moveAtAngle(-100 * delta, 0, angle);
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_D)) {
			position.moveAtAngle(100 * delta, 0, angle);
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_W)) {
			position.moveAtAngle(0, -100 * delta, angle);
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_S)) {
			position.moveAtAngle(0, 100 * delta, angle);
		}
	}
}
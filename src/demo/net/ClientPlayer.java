package demo.net;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.Mouse;

public class ClientPlayer extends Player {
	public ClientPlayer() {
		position.set(Game.getGameInstance().getWidth() / 2, Game.getGameInstance().getHeight() / 2);
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		
		angle = position.getAngleTo(Mouse.getMouse().getX(), Mouse.getMouse().getY()) + 90;
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_A)) {
			position.add(-500 * delta, 0);
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_D)) {
			position.add(500 * delta, 0);
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_W)) {
			position.add(0, -500 * delta);
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_S)) {
			position.add(0, 500 * delta);
		}
	}
}
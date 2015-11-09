package demo;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.event.KeyAdapter;
import com.redmintie.steelplate.input.event.KeyEvent;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.util.ease.EaseOut;

public class Player extends Entity {
	private EaseOut ease = new EaseOut(100, -100, 1, 2);
	public int lives = 3;
	public int shield = 0;
	public Player() {
		position.x = Game.getGameInstance().getWidth() / 2;
		width = Res.player.getWidth();
		height = Res.player.getHeight();
		
		Keyboard.getKeyboard().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (lives >= 0 && e.getKey() == Keyboard.KEY_SPACE) {
					Res.laserSound.play();
					addChild(new Laser(position, e.isKeyDown(Keyboard.KEY_CONTROL)));
				}
			}
		});
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		position.y = Game.getGameInstance().getHeight() + ease.update(delta);
		
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_LEFT)) {
			position.add(-500 * delta, 0);
			if (position.x < 0) {
				position.x = 0;
			}
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_RIGHT)) {
			position.add(500 * delta, 0);
			if (position.x > Game.getGameInstance().getWidth()) {
				position.x = Game.getGameInstance().getWidth();
			}
		}
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (lives >= 0) {
			canvas.drawImage(Res.player);
			if (lives < 3) {
				canvas.drawImage(Res.damage[lives], -1, -1);
			}
		}
	}
}
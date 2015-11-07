package demo;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.event.KeyAdapter;
import com.redmintie.steelplate.input.event.KeyEvent;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.util.Array;
import com.redmintie.steelplate.util.math.ease.EaseOut;

public class Player extends Entity {
	private EaseOut ease = new EaseOut(100, -100, 1, 2);
	public Array<Laser> lasers = new Array<Laser>();
	public int lives = 3;
	public Player() {
		position.setX(Game.getGameInstance().getWidth() / 2);
		width = Res.player.getWidth();
		height = Res.player.getHeight();
		
		Keyboard.getKeyboard().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (lives >= 0 && e.getKey() == Keyboard.KEY_SPACE) {
					Res.laserSound.play();
					lasers.add(new Laser(position, e.isKeyDown(Keyboard.KEY_CONTROL)));
				}
			}
		});
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		position.setY(Game.getGameInstance().getHeight() + ease.update(delta));
		
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_LEFT)) {
			position.add(-500 * delta, 0);
			if (position.getX() < 0) {
				position.setX(0);
			}
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_RIGHT)) {
			position.add(500 * delta, 0);
			if (position.getX() > Game.getGameInstance().getWidth()) {
				position.setX(Game.getGameInstance().getWidth());
			}
		}
		for (Laser laser : lasers) {
			laser.update(delta);
			if (laser.position.getY() < -100) {
				lasers.remove(laser);
			}
		}
	}
	@Override
	public void draw(Canvas canvas) {
		for (Laser laser : lasers) {
			laser.draw(canvas);
		}
		
		if (lives >= 0) {
			super.draw(canvas);
			canvas.drawImage(Res.player);
			if (lives < 3) {
				canvas.drawImage(Res.damage[lives], -1, -1);
			}
			canvas.resetMatrix();
		}
	}
}
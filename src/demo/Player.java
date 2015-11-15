package demo;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.Mouse;
import com.redmintie.steelplate.input.event.MouseAdapter;
import com.redmintie.steelplate.input.event.MouseEvent;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.util.Point;
import com.redmintie.steelplate.util.ease.EaseOut;

public class Player extends Entity {
	private EaseOut ease = new EaseOut(100, -100, 1, 2);
	public int lives = 3;
	public int shield = 0;
	
	public int ammo;
	public double reload;
	public int count;
	
	private Point buffer = new Point();
	
	public Player() {
		position.x = Game.getGameInstance().getWidth() / 2;
		width = Res.player.getWidth();
		height = Res.player.getHeight();
		
		Mouse.getMouse().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseButtonPressed(MouseEvent e) {
				if (lives >= 0 && e.getMouseButton() == Mouse.BUTTON1) {
					reload = 0;
					if (count != -1) {
						count = 0;
					}
					Res.laserSound.play();
					addChild(new Laser(position, angle));
				}
			}
		});
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		position.y = Game.getGameInstance().getHeight() + ease.update(delta);
		
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_A)) {
			position.add(-500 * delta, 0);
			if (position.x < 0) {
				position.x = 0;
			}
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_D)) {
			position.add(500 * delta, 0);
			if (position.x > Game.getGameInstance().getWidth()) {
				position.x = Game.getGameInstance().getWidth();
			}
		}
		angle = buffer.set(position).getAngleTo(Mouse.getMouse().getX(), Mouse.getMouse().getY()) + 90;
		
		if (lives >= 0 && Mouse.getMouse().isButtonDown(Mouse.BUTTON1)) {
			reload += delta;
			while (reload >= 0.1) {
				if (ammo > 0 && count < 4) {
					Res.laserSound.play();
					addChild(new Laser(position, angle));
					ammo--;
					if (count != -1) {
						count++;
					}
				}
				reload -= 0.1;
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
			if (shield > 0) {
				canvas.drawImage(Res.shield[shield], -23, -31);
			}
		}
	}
}
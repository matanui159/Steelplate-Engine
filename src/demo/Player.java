package demo;

import java.util.ArrayList;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.event.KeyAdapter;
import com.redmintie.steelplate.input.event.KeyEvent;
import com.redmintie.steelplate.node.Node;
import com.redmintie.steelplate.render.Canvas;

public class Player extends Node {
	private ArrayList<Laser> lasers = new ArrayList<Laser>();
	public Player() {
		position.setX(Game.getGameInstance().getWidth() / 2);
		width = Res.player.getWidth();
		height = Res.player.getHeight();
		Keyboard.getKeyboard().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKey() == Keyboard.KEY_SPACE) {
					lasers.add(new Laser(position));
				}
			}
		});
	}
	@Override
	public void update(double delta) {
		super.update(delta);
		position.setY(Game.getGameInstance().getHeight() - 100);
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_LEFT)) {
			position.add(-500 * delta, 0);
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_RIGHT)) {
			position.add(500 * delta, 0);
		}
		for (Laser laser : lasers) {
			laser.update(delta);
		}
	}
	@Override
	public void draw(Canvas canvas) {
		for (Laser laser : lasers) {
			laser.draw(canvas);
			canvas.resetMatrix();
		}
		
		super.draw(canvas);
		canvas.drawImage(Res.player);
	}
}
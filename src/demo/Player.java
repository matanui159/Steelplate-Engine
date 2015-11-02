package demo;

import java.util.ArrayList;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.event.KeyAdapter;
import com.redmintie.steelplate.input.event.KeyEvent;
import com.redmintie.steelplate.math.Point;
import com.redmintie.steelplate.math.ease.EaseOut;
import com.redmintie.steelplate.node.Node;
import com.redmintie.steelplate.render.Canvas;

public class Player extends Node {
	private EaseOut ease = new EaseOut(
			new Point(0, 100),
			new Point(0, -100),
			1, 3
	);
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
		double x = position.getX();
		ease.update(delta, position);
		position.add(x, Game.getGameInstance().getHeight());
		
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
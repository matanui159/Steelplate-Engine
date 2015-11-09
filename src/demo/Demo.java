package demo;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Color;
import com.redmintie.steelplate.render.Image;
import com.redmintie.steelplate.util.array.IDArray;

public class Demo extends Game {
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");
		new Demo();
	}
	public Demo() {
		setTitle("Space Shooter");
		begin();
	}
	
	private double time;
	
	private Image old;
	private Image current;
	private Color clear = new Color(0, 0, 0, 0);
	
	private Player player;
	
	private IDArray<Enemy> enemies = new IDArray<Enemy>();
	
	private IDArray<Star> stars = new IDArray<Star>();
	private int score;
	
	@Override
	public void init() {
		try {
			Res.init();
			old = Image.createImage(getWidth(), getHeight());
			current = Image.createImage(getWidth(), getHeight());
			player = new Player();
		} catch (IOException|UnsupportedAudioFileException ex) {
			ex.printStackTrace();
			end();
		}
	}
	@Override
	public void update(double delta) {
		time += delta * 2;
		while (time >= 1) {
			for (int i = 0; i < 3; i++) {
				enemies.add(new Enemy());
			}
			time -= 1;
		}
		
		if (old.getWidth() != getWidth() || old.getHeight() != getHeight()) {
			old.destroy();
			current.destroy();
			try {
				old = Image.createImage(getWidth(), getHeight());
				current = Image.createImage(getWidth(), getHeight());
			} catch (IOException ex) {
				ex.printStackTrace();
				end();
			}
		}
		
		player.update(delta);
		
		for (Enemy enemy : enemies) {
			enemy.update(delta);
			if (player.lives >= 0 && enemy.testOverlap(player)) {
				if (player.shield > 0) {
					player.shield--;
				} else {
					player.lives--;
				}
				enemies.remove(enemy);
			} else if (enemy.position.y > Game.getGameInstance().getHeight() + 100) {
				enemies.remove(enemy);
			} else {
				for (Entity laser : player) {
					if (laser.testOverlap(enemy)) {
						for (int i = 0; i < 30; i++) {
							stars.add(new Star(enemy.position));
						}
						player.removeChild(laser);
						enemies.remove(enemy);
					}
				}
			}
		}
		
		for (Star star : stars) {
			star.update(delta);
			if (star.position.x == -100 && star.position.y == -100) {
				score++;
				stars.remove(star);
			}
		}
	}
	@Override
	public void draw(Canvas canvas) {
		for (int x = 0; x < (double)getWidth() / Res.background.getWidth(); x++) {
			for (int y = -1; y < (double)getHeight() / Res.background.getHeight(); y++) {
				canvas.drawImage(Res.background,
						x * Res.background.getWidth(),
						(y + time) * Res.background.getHeight());
			}
		}
		
		Canvas c = current.getCanvas();
		c.setColor(clear);
		c.clear();
		c.setAlpha(200);
		c.drawImage(old);
		c.setAlpha(255);
		
		player.draw(c);
		
		canvas.drawImage(current);
		Image buffer = old;
		old = current;
		current = buffer;
		
		for (Enemy enemy : enemies) {
			enemy.draw(canvas);
		}
		
		for (Star star : stars) {
			star.draw(canvas);
		}
		
		for (int i = 0; i < 8; i++) {
			int j = score / (int)Math.pow(10, 7 - i);
			while (j >= 10) {
				j -= 10;
			}
			canvas.drawImage(Res.numbers[j], i * 30 + 10, 10);
		}
		
		for (int i = 0; i < player.lives; i++) {
			canvas.drawImage(Res.life, i * 50 + 10, 40);
		}
	}
	@Override
	public void close() {
		end();
	}
}
package demo.game;
import java.io.IOException;

import com.redmintie.steelplate.core.DeviceException;
import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.event.KeyAdapter;
import com.redmintie.steelplate.input.event.KeyEvent;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Color;
import com.redmintie.steelplate.render.Image;
import com.redmintie.steelplate.sound.Sound;
import com.redmintie.steelplate.util.data.DataStorage;
import com.redmintie.steelplate.util.multithread.MultiThreadAdapter;

public class Demo extends Game {
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");
		try {
			new Demo();
		} catch (DeviceException ex) {
			ex.printStackTrace();
		}
	}
	public Demo() throws DeviceException {
		setTitle("Space Shooter");
		setMaximized(true);
		start();
	}
	
	private double time;
	private int count;
	
	private Color clear = new Color(0, 0, 0, 0);
	private Image old;
	private Image current;
	
	public static Player player;
	private Entity enemies = new Entity();
	private Entity powerups = new Entity();
	public static Entity stars = new Entity();
	
	public static int score;
	public static double pill;
	
	@Override
	public void init() {
		Keyboard.getKeyboard().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKey() == Keyboard.KEY_ESCAPE) {
					close();
				}
			}
		});
		
		try {
			Sound song = Sound.loadSound("res/song.ogg", true);
			song.setLoopEnabled(true);
			song.setVolume(0.1);
			song.play();
			
			old = Image.createImage(getWidth(), getHeight());
			current = Image.createImage(getWidth(), getHeight());
			Res.init();
			player = new Player();
		} catch (IOException|DeviceException ex) {
			ex.printStackTrace();
			end();
		}
	}
	@Override
	public void update(double delta) {
		if (pill > 0) {
			pill -= delta;
		}
		
		time += delta * 2;
		while (time >= 1) {
			for (int i = 0; i < 3; i++) {
				enemies.addChild(new Enemy());
			}
			time -= 1;
			count++;
			if (count == 5) {
				powerups.addChild(new PowerUp());
				count = 0;
			}
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
		enemies.update(delta);
		powerups.update(delta);
		stars.update(delta);
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
		enemies.draw(c);
		powerups.draw(c);
		stars.draw(c);
		
		canvas.drawImage(current);
		Image buffer = old;
		old = current;
		current = buffer;
		
		for (int i = 0; i < 8; i++) {
			int j = score / (int)Math.pow(10, 7 - i);
			while (j >= 10) {
				j -= 10;
			}
			canvas.drawImage(Res.numbers[j], i * 30 + 10, 10);
		}
		
		for (int i = 0; i < player.lives + player.shield; i++) {
			if (i < player.lives) {
				canvas.drawImage(Res.life, i * 50 + 10, 40);
			} else {
				canvas.drawImage(Res.shield, i * 50 + 10, 40);
			}
		}
	}
	@Override
	public void close() {
		DataStorage.writeObjectLater("demo.game.Player.position", player.position, new MultiThreadAdapter() {
			@Override
			public void actionStarted() {
				System.out.println("Saving Data...");
			}
			@Override
			public void actionFinished() {
				System.out.println("\tDone!");
			}
		});
		end();
	}
}
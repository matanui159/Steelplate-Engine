package demo;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Color;
import com.redmintie.steelplate.render.Image;

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
	}
	@Override
	public void close() {
		end();
	}
}
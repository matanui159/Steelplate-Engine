package demo;
import java.io.IOException;

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
	private Image background;
	
	private Image buffer1;
	private Image buffer2;
	private Color clear = new Color(0, 0, 0, 0);
	
	private Player player;
	
	@Override
	public void init() {
		try {
			background = Image.loadImage("res/images/background.png");
			buffer1 = Image.createImage(getWidth(), getHeight());
			buffer2 = Image.createImage(getWidth(), getHeight());
			player = new Player();
		} catch (IOException ex) {
			ex.printStackTrace();
			end();
		}
	}
	@Override
	public void update(double delta) {
		time += delta;
		while (time >= 1) {
			time -= 1;
		}
		
		if (buffer1.getWidth() != getWidth() || buffer1.getHeight() != getHeight()) {
			buffer1.destroy();
			buffer2.destroy();
			try {
				buffer1 = Image.createImage(getWidth(), getHeight());
				buffer2 = Image.createImage(getWidth(), getHeight());
			} catch (IOException ex) {
				ex.printStackTrace();
				end();
			}
		}
		
		player.update(delta);
	}
	@Override
	public void draw(Canvas canvas) {
		for (int x = 0; x < (double)getWidth() / background.getWidth(); x++) {
			for (int y = -1; y < (double)getHeight() / background.getHeight(); y++) {
				canvas.drawImage(background, x * background.getWidth(), (y + time) * background.getHeight());
			}
		}
		
		Canvas c = buffer1.getCanvas();
		c.setColor(clear);
		c.clear();
		c.setAlpha(200);
		c.drawImage(buffer2);
		c.setAlpha(255);
		player.draw(c);
		canvas.drawImage(buffer1);
		
		Image buffer = buffer1;
		buffer1 = buffer2;
		buffer2 = buffer;
	}
	@Override
	public void close() {
		end();
	}
}
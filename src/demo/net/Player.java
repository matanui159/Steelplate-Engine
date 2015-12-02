package demo.net;

import java.io.IOException;

import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Image;

public class Player extends PlayerPosition {
	private static Image image;
	public Player() {
		if (image == null) {
			try {
				image = Image.loadImage("res/images/player.png");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		width = image.getWidth();
		height = image.getHeight();
	}
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		
		canvas.drawImage(image);
	}
}
package demo;

import java.io.IOException;

import com.redmintie.steelplate.render.Color;
import com.redmintie.steelplate.render.Image;

public class Res {
	public static Color clear = new Color(0, 0, 0, 0);
	public static Image background;
	public static Image player;
	public static Image laser;
	public static void init() throws IOException {
		background = Image.loadImage("res/images/background.png");
		player = Image.loadImage("res/images/player.png");
		laser = Image.loadImage("res/images/laser.png");
	}
}
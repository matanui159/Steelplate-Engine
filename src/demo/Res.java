package demo;

import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.redmintie.steelplate.render.Color;
import com.redmintie.steelplate.render.Image;
import com.redmintie.steelplate.sound.Sound;

public class Res {
	public static Color clear = new Color(0, 0, 0, 0);
	public static Image background;
	public static Image player;
	public static Image laser;
	public static Image enemy;
	
	public static Sound laserSound;
	public static void init() throws IOException, UnsupportedAudioFileException {
		background = Image.loadImage("res/images/background.png");
		player = Image.loadImage("res/images/player.png");
		laser = Image.loadImage("res/images/laser.png");
		enemy = Image.loadImage("res/images/enemy.png");
		
		laserSound = Sound.loadSound("res/sounds/laser.wav");
	}
}
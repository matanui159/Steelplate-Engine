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
	public static Image[] damage;
	public static Image laser;
	public static Image enemy;
	public static Image star;
	public static Image[] numbers;
	public static Image life;
	
	public static Sound laserSound;
	public static void init() throws IOException, UnsupportedAudioFileException {
		background = Image.loadImage("res/images/background.png");
		player = Image.loadImage("res/images/player.png");
		damage = new Image[] {
				Image.loadImage("res/images/damage/0.png"),
				Image.loadImage("res/images/damage/1.png"),
				Image.loadImage("res/images/damage/2.png"),
		};
		laser = Image.loadImage("res/images/laser.png");
		enemy = Image.loadImage("res/images/enemy.png");
		star = Image.loadImage("res/images/star.png");
		numbers = new Image[] {
				Image.loadImage("res/images/numbers/0.png"),
				Image.loadImage("res/images/numbers/1.png"),
				Image.loadImage("res/images/numbers/2.png"),
				Image.loadImage("res/images/numbers/3.png"),
				Image.loadImage("res/images/numbers/4.png"),
				Image.loadImage("res/images/numbers/5.png"),
				Image.loadImage("res/images/numbers/6.png"),
				Image.loadImage("res/images/numbers/7.png"),
				Image.loadImage("res/images/numbers/8.png"),
				Image.loadImage("res/images/numbers/9.png")
		};
		life = Image.loadImage("res/images/life.png");
		
		laserSound = Sound.loadSound("res/sounds/laser.wav");
	}
}
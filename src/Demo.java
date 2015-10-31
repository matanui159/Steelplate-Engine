import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.event.KeyAdapter;
import com.redmintie.steelplate.input.event.KeyEvent;
import com.redmintie.steelplate.math.Point;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Color;
import com.redmintie.steelplate.render.Image;

public class Demo extends Game {
	public static void main(String[] args) {
		new Demo();
	}
	private Image player;
	private Point pos = new Point(getWidth() / 2, getHeight() / 2);
	private double angle;
	public Demo() {
		setTitle("Demo");
		begin();
	}
	@Override
	public void init() {
		try {
			player = Image.loadImage("res/player.png");
		} catch (Exception ex) {
			ex.printStackTrace();
			close();
		}
		Keyboard.getKeyboard().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKey() == Keyboard.KEY_SPACE) {
					keepBackground(!isBackgroundKept());
				}
			}
		});
	}
	@Override
	public void update(double delta) {
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_LEFT)) {
			angle -= 360 * delta;
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_RIGHT)) {
			angle += 360 * delta;
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_UP)) {
			pos.moveAtAngle(0, -500 * delta, angle);
		}
		if (Keyboard.getKeyboard().isKeyDown(Keyboard.KEY_DOWN)) {
			pos.moveAtAngle(0, 500 * delta, angle);
		}
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.setColor(new Color(0, 0, 0, 30));
		canvas.clear();
		
		canvas.translate(pos.getX(), pos.getY());
		canvas.rotate(angle);
		canvas.translate(-player.getWidth() / 2, -player.getHeight() / 2);
		
		canvas.drawImage(player);
	}
	@Override
	public void close() {
		end();
	}
}
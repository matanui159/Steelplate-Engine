package demo.net;

import com.redmintie.steelplate.core.DeviceException;
import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Color;

public class Demo extends Game {
	public static void main(String[] args) {
		try {
			new Demo();
		} catch (DeviceException ex) {
			ex.printStackTrace();
		}
	}
	public Demo() throws DeviceException {
		setTitle("Net Demo");
		begin();
	}
	@Override
	public void init() {
		
	}
	@Override
	public void update(double delta) {
		
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.setColor(new Color(0, 0, 0, 0));
		canvas.clear();
	}
	@Override
	public void close() {
		end();
	}
}
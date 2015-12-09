package demo.ui;


import java.io.IOException;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.entity.ui.Button;
import com.redmintie.steelplate.entity.ui.TextField;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Color;
import com.redmintie.steelplate.render.Font;
import com.redmintie.steelplate.render.Image;

public class Demo extends Game {
	public static void main(String[] args) throws Exception {
		new Demo();
	}
	public Demo() throws Exception {
		setTitle("UI Demo");
		start();
	}
	private Image up;
	private Image down;
	private Image img;
	private Font font;
	private Font arial;
	private Button button;
	private TextField text;
	@Override
	public void init() {
		try {
			up = Image.loadImage("res/images/ui/buttonup.png");
			down = Image.loadImage("res/images/ui/buttondown.png");
			img = Image.loadImage("res/images/ui/textfield.png");
			font = Font.loadFont("res/font.ttf", Font.PLAIN, 30);
			arial = Font.loadFont("Arial", Font.PLAIN, 30);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		button = new Button() {
			{
				position.set(300, 300);
				width = down.getWidth();
				height = down.getHeight();
			}
			@Override
			public void draw(Canvas canvas) {
				super.draw(canvas);
				
				if (getButtonState()) {
					canvas.drawImage(down);
				} else {
					canvas.translate(0, -4);
					canvas.drawImage(up);
				}
				
				String text = "Hello!";
				canvas.setColor(Color.WHITE);
				canvas.setFont(font);
				canvas.drawText(text,
						down.getWidth() / 2 - font.getTextWidth(text) / 2,
						down.getHeight() / 2 - font.getTextHeight(text) / 2);
			}
			@Override
			public void buttonPressed() {
				System.out.println("PRESSED");
			}
		};
		
		text = new TextField() {
			{
				position.set(600, 300);
				width = img.getWidth();
				height = img.getHeight();
			}
			@Override
			public void draw(Canvas canvas) {
				super.draw(canvas);
				canvas.drawImage(img);
				canvas.setColor(Color.BLACK);
				canvas.setFont(arial);
				String text = getText();
				canvas.drawText(text, 10,
						img.getHeight() / 2 - arial.getTextHeight(text) / 2);
			}
			@Override
			public void textEntered(String text) {
				System.out.println("TEXT: " + text);
			}
		};
	}
	@Override
	public void update(double delta) {
		button.update(delta);
		text.update(delta);
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.setColor(Color.BLACK);
		canvas.clear();
		button.draw(canvas);
		text.draw(canvas);
	}
	@Override
	public void close() {
		end();
	}
}
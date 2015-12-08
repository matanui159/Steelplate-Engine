package com.redmintie.steelplate.entity.ui;

import java.awt.Desktop;
import java.net.URL;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.input.Mouse;
import com.redmintie.steelplate.input.event.MouseAdapter;
import com.redmintie.steelplate.input.event.MouseEvent;
import com.redmintie.steelplate.input.event.MouseListener;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Color;
import com.redmintie.steelplate.render.Image;

public class LogoScene implements Scene {
	private Image logo;
	private Color background;
	private String link;
	private double time;
	private Scene next;
	private MouseListener listener = new MouseAdapter() {
		@Override
		public void mouseButtonReleased(MouseEvent e) {
			if (Desktop.isDesktopSupported() && e.getMouseButton() == Mouse.BUTTON1) {
				try {
					Desktop.getDesktop().browse(new URL(link).toURI());
				} catch (Exception ex) {}
			}
		}
	};
	private double t;
	public LogoScene(Image logo, Color background, String link, double time, Scene next) {
		this.logo = logo;
		this.background = background;
		this.link = link;
		this.next = next;
	}
	@Override
	public void init() {
		Mouse.getMouse().addMouseListener(listener);
	}
	@Override
	public void update(View view, double delta) {
		t += delta;
		if (t >= time) {
			view.setScene(next);
		}
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.setColor(background);
		canvas.clear();
		canvas.drawImage(logo,
				Game.getGameInstance().getWidth() / 2 - logo.getWidth() / 2,
				Game.getGameInstance().getHeight() / 2 - logo.getHeight() / 2);
	}
	@Override
	public void end() {
		Mouse.getMouse().removeMouseListener(listener);
	}
}
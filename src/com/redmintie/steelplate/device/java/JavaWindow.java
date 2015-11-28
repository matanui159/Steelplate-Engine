package com.redmintie.steelplate.device.java;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.device.Window;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.Mouse;
import com.redmintie.steelplate.render.Font;
import com.redmintie.steelplate.render.Image;

public class JavaWindow extends Window {
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel() {
		private static final long serialVersionUID = 0;
		@Override
		public void paintComponent(Graphics g) {
			if (canvas == null) {
				canvas = new JavaCanvas();
			}
			canvas.setGraphics(g);
			if (logo) {
				drawLogo(canvas);
			} else {
				Game.getGameInstance().update(getDelta());
				Game.getGameInstance().draw(canvas);
				repaint();
			}
		}
	};
	private JavaCanvas canvas;
	private boolean logo = true;
	public JavaWindow() {
		frame.add(panel);
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Game.getGameInstance().close();
			}
		});
	}
	@Override
	public int getType() {
		return Game.TYPE_DESKTOP;
	}
	@Override
	public void begin() {
		frame.setVisible(true);
		panel.repaint();
	}
	@Override
	public void loop() {
		logo = false;
		panel.repaint();
	}
	@Override
	public void end() {
		frame.dispose();
	}
	@Override
	public void setTitle(String title) {
		frame.setTitle(title);
	}
	@Override
	public void setIcon(BufferedImage icon) {
		frame.setIconImage(icon);
	}
	@Override
	public void setSize(int width, int height) {
		panel.setPreferredSize(new Dimension(width, height));;
		frame.pack();
	}
	@Override
	public int getWidth() {
		return panel.getWidth();
	}
	@Override
	public int getHeight() {
		return panel.getHeight();
	}
	@Override
	public void keepBackground(boolean keep) {
	}
	@Override
	public Keyboard getKeyboard() {
		return new JavaKeyboard(frame);
	}
	@Override
	public Mouse getMouse() {
		return new JavaMouse(panel);
	}
	@Override
	public Image loadImage(BufferedImage image) {
		return new JavaImage(image);
	}
	@Override
	public Image createImage(int width, int height) {
		return new JavaImage(GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration()
				.createCompatibleImage(width, height, Transparency.TRANSLUCENT));
	}
	@Override
	public Font loadFont(java.awt.Font font) {
		return new JavaFont(font);
	}
}
package com.redmintie.steelplate.render;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.redmintie.steelplate.core.Resource;

public class Spritesheet {
	private Image image;
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	public Spritesheet(String name) throws IOException {
		if (name.toLowerCase().endsWith(".xml")) {
			Document doc;
			try {
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.parse(Resource.getResourceAsStream(name));
			} catch (ParserConfigurationException|SAXException ex) {
				throw new IOException(ex);
			}
			doc.getDocumentElement().normalize();
			if (!doc.getDocumentElement().getNodeName().equals("TextureAtlas")) {
				throw new IOException("Not a spritesheet.");
			}
			
			image = Image.loadImage(name.substring(0, Math.max(name.lastIndexOf('/'), name.lastIndexOf('\\')) + 1)
					+ doc.getDocumentElement().getAttribute("imagePath"));
			
			NodeList nodes = doc.getElementsByTagName("SubTexture");
			for (int i = 0; i < nodes.getLength(); i++) {
				Element elem = (Element)nodes.item(i);
				addSprite(elem.getAttribute("name"),
						Integer.parseInt(elem.getAttribute("x")),
						Integer.parseInt(elem.getAttribute("y")),
						Integer.parseInt(elem.getAttribute("width")),
						Integer.parseInt(elem.getAttribute("height")));
			}
		} else {
			image = Image.loadImage(name);
		}
	}
	public Spritesheet(String name, int spritew, int spriteh, int xgap, int ygap,
			int offsetx, int offsety) throws IOException {
		image = Image.loadImage(name);
		int i = 0;
		for (int y = 0; y < Math.floor((image.getHeight() - offsety * 2 + ygap) / (spriteh + ygap)); y++) {
			for (int x = 0; x < Math.floor((image.getWidth() - offsetx * 2 + xgap) / (spritew + xgap)); x++) {
				addSprite(Integer.toString(i++),
						x * (spritew + xgap) + offsetx,
						y * (spriteh + ygap) + offsety,
						spritew, spriteh);
			}
		}
	}
	public Spritesheet(String name, int spritew, int spriteh, int xgap, int ygap) throws IOException {
		this(name, spritew, spriteh, xgap, ygap, 0, 0);
	}
	public Spritesheet(String name, int spritew, int spriteh) throws IOException {
		this(name, spritew, spriteh, 0, 0, 0, 0);
	}
	public void addSprite(String name, int x, int y, int width, int height) {
		sprites.put(name, new Sprite(x, y, width, height));
	}
	public int getSpriteWidth(String name) {
		return sprites.get(name).width;
	}
	public int getSpriteHeight(String name) {
		return sprites.get(name).height;
	}
	public void drawSprite(Canvas canvas, String name, int x, int y, int width, int height) {
		Sprite sprite = sprites.get(name);
		canvas.drawImage(image, sprite.x, sprite.y, sprite.width, sprite.height, x, y, width, height);
	}
	public void drawSprite(Canvas canvas, String name, int x, int y) {
		Sprite sprite = sprites.get(name);
		drawSprite(canvas, name, x, y, sprite.width, sprite.height);
	}
	public void drawSprite(Canvas canvas, String name) {
		Sprite sprite = sprites.get(name);
		drawSprite(canvas, name, 0, 0, sprite.width, sprite.height);
	}
	private class Sprite {
		public int x;
		public int y;
		public int width;
		public int height;
		public Sprite(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}
}
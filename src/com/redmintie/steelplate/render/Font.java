package com.redmintie.steelplate.render;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;

import com.redmintie.steelplate.core.Resource;
import com.redmintie.steelplate.core.Game;

public abstract class Font {
	public static final int PLAIN = java.awt.Font.PLAIN;
	public static final int ITALIC = java.awt.Font.ITALIC;
	public static final int BOLD = java.awt.Font.BOLD;
	public static final int ITALIC_BOLD = ITALIC | BOLD;
	private static HashMap<FontInfo, Font> fonts = new HashMap<FontInfo, Font>();
	private static FontInfo info = new FontInfo(null, 0, 0);
	public static Font loadFont(String family, int style, int size) {
		info.set(family, style, size);
		if (!fonts.containsKey(info)) {
			java.awt.Font font;
			try {
				font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
						Resource.getResourceAsStream(family)).deriveFont(style, size);
			} catch (IOException|FontFormatException ex) {
				font = new java.awt.Font(family, style, size);
			}
			fonts.put(new FontInfo(family, style, size), Game.getGameInstance().getWindow().loadFont(font));
		}
		return fonts.get(info);
	}
	private static class FontInfo {
		public String family;
		public int style;
		public int size;
		public FontInfo(String family, int style, int size) {
			set(family, style, size);
		}
		public void set(String family, int style, int size) {
			this.family = family;
			this.style = style;
			this.size = size;
		}
		@Override
		public boolean equals(Object other) {
			if (other instanceof FontInfo) {
				FontInfo info = (FontInfo)other;
				return family == info.family && style == info.style && size == info.size;
			}
			return false;
		}
		@Override
		public int hashCode() {
			return family.hashCode() ^ style ^ size;
		}
	}
	public abstract String getFamily();
	public abstract int getStyle();
	public abstract int getSize();
	protected abstract int getLineWidth(String line);
	public int getTextWidth(String text) {
		String[] lines = text.replace("\t", "    ").split("\n");
		int width = 0;
		for (String line : lines) {
			int w = getLineWidth(line);
			if (w > width) {
				width = w;
			}
		}
		return width;
	}
	public abstract int getLineHeight();
	public int getTextHeight(String text) {
		String[] lines = text.replace("\t", "    ").split("\n");
		return getLineHeight() * lines.length;
	}
}
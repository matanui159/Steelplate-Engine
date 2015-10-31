package gen;

import java.awt.Color;
import java.lang.reflect.Field;

public class GenColors {
	public static void main(String[] args) {
		System.out.println("\t// GENERATED CODE BEGINS HERE");
		Field[] colors = Color.class.getDeclaredFields();
		for (Field color : colors) {
			if (color.getType().equals(Color.class)) {
				String name = color.getName();
				if (name.toUpperCase().charAt(0) == name.charAt(0)) {
					try {
						Color value = (Color)color.get(null);
						System.out.println("\tpublic static final Color " + color.getName()
								+ " = new Color(" + value.getRed() + ", "
								+ value.getGreen() + ", "
								+ value.getBlue() + ");");
					} catch (IllegalAccessException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		System.out.println("\t// GENERATED CODE ENDS HERE");
	}
}
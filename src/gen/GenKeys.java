package gen;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

public class GenKeys {
	public static void main(String[] args) {
		System.out.println("\t// GENERATED CODE BEGINS HERE");
		Field[] keys = KeyEvent.class.getDeclaredFields();
		for (Field key : keys) {
			if (key.getName().startsWith("VK_")) {
				try {
					System.out.println("\tpublic static final int KEY_" + key.getName().substring(3)
							+ " = " + key.getInt(null) + ";");
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
				}
			}
		}
		System.out.println("\t// GENERATED CODE ENDS HERE");
	}
}
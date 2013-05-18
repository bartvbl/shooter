package io;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {
	public static void init() throws LWJGLException {
		Mouse.create();
		Keyboard.create();
	}


}

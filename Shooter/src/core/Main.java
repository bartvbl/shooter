package core;

import io.Input;

import org.lwjgl.LWJGLException;

import util.Dialog;

public class Main {
	public static void main(String[] args) {
		GameMain main = new GameMain();
		try {
			FrameUtils.initWindow();
			Input.init();

			main.init();
			System.out.println("Game started.");
			main.mainLoop();
		} catch (LWJGLException e) {
			Dialog.showErrorDialog("An exception related to LWJGL occurred. Message:\n" + e.getMessage());
			e.printStackTrace();
		}
	}
}

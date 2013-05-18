package util;

import javax.swing.JOptionPane;

public class Dialog {

	public static void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(null, message, "error", JOptionPane.ERROR_MESSAGE);
	}

}

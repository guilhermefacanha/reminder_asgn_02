package com.csis.reminder.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;

/**
 * @author Reminder Group
 * Util class for SWING screen
 */
public class ScreenUtil {

	
	/**
	 * Method to resize screen based on the users monitor size
	 * @param rate {@link Double} percent rate of the screen to cover
	 * @return {@link Rectangle} rectangle object with dimensions for the screen
	 */
	public static Rectangle resizeScreen(double rate) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Double width = screenSize.getWidth() * rate;
		Double height = screenSize.getHeight() * rate;

		return new Rectangle(100, 100, width.intValue(), height.intValue());
	}
	
	/**
	 * Method to center screen for a window
	 * @param frame {@link Window} class be centered
	 */
	public static void centerWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}

}

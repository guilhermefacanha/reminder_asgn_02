package com.csis.reminder.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class ScreenUtil {

	public static Rectangle resizeScreen(double rate) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Double width = screenSize.getWidth() * rate;
		Double height = screenSize.getHeight() * rate;

		return new Rectangle(100, 100, width.intValue(), height.intValue());
	}

}

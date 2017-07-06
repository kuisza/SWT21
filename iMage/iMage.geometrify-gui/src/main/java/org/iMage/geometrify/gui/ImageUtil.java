package org.iMage.geometrify.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Utility class that contains image manipulation functions.
 * 
 * @author Christopher Guckes
 * @version 1.0
 */
public final class ImageUtil {
	/**
	 * Private constructor to prevent utility class instantiation.
	 */
	private ImageUtil() {
	}

	/**
	 * Resizes an image while maintaining aspect ratio.
	 * 
	 * @param input
	 *            the image to resize
	 * @param maxWidth
	 *            the maximum width we want to resize to
	 * @param maxHeight
	 *            the maximum height we want to resize to
	 * @return the resized image.
	 */
	public static BufferedImage resize(BufferedImage input, int maxWidth, int maxHeight) {
		Image image = null;
		if (input.getHeight() > input.getWidth()) {
			image = input.getScaledInstance(-1, maxHeight, BufferedImage.SCALE_DEFAULT);
		} else {
			image = input.getScaledInstance(maxWidth, -1, BufferedImage.SCALE_DEFAULT);
		}

		BufferedImage myReturn = new BufferedImage(image.getWidth(null), image.getHeight(null), input.getType());
		myReturn.getGraphics().drawImage(image, 0, 0, null);
		return myReturn;
	}
}

package org.iMage.geometrify.gui;

import java.awt.image.BufferedImage;

import org.iMage.geometrify.AbstractPrimitivePictureFilter;

/**
 * Interface for classes that want to be informed about the progress of the
 * application of a {@link AbstractPrimitivePictureFilter}.
 * 
 * @author Dominic Ziegler
 * @version 1.0
 */
public interface PictureFilterObserver {
	/**
	 * This method is called by an observed
	 * {@link AbstractPrimitivePictureFilter} after each iteration.
	 * 
	 * @param image
	 *            the current image
	 */
	void update(BufferedImage image);
}

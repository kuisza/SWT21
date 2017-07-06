/**
 * 
 */
package org.iMage.geometrify.gui;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A {@link PictureFilterObserver} which updates the preview {@link JPanel}
 * 
 * @author Tobias Hey
 *
 */
public class UpdatePreviewObserver implements PictureFilterObserver {

	private JLabel preview;

	/**
	 * Constructs a new {@link UpdatePreviewObserver} which is able to update
	 * the specified {@link JLabel}
	 * 
	 * @param preview
	 *            The {@link JLabel} to update
	 */
	public UpdatePreviewObserver(JLabel preview) {
		this.preview = preview;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.iMage.geometrify.gui.PictureFilterObserver#update(java.awt.image.
	 * BufferedImage)
	 */
	@Override
	public void update(BufferedImage image) {
		preview.setIcon(new ImageIcon(image));
		preview.setVisible(true);

	}

}

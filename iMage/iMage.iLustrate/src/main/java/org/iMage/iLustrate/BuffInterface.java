package org.iMage.iLustrate;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public interface BuffInterface<Void> extends Callable<Void> {

	
	public Void call();
	
	public void setImage(BufferedImage image, int i);

}

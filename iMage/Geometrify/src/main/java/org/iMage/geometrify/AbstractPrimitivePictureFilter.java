package org.iMage.geometrify;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * The {@link AbstractPrimitivePictureFilter} represents a
 * {@link IPrimitiveFilter} which is able to apply the "Geometrify" filter to an
 * image
 *
 * @author Tobias Hey
 *
 */
public abstract class AbstractPrimitivePictureFilter implements IPrimitiveFilter {
	/** the point generator */
	protected IPointGenerator pointGenerator;

	/**
	 * Constructs a new instance of an {@link AbstractPrimitivePictureFilter}
	 * with its defining arguments.
	 *
	 * @param pointGenerator
	 *            The {@link IPointGenerator} to use for generating the needed
	 *            seed points
	 */
	public AbstractPrimitivePictureFilter(IPointGenerator pointGenerator) {
		this.pointGenerator = pointGenerator;
	}
	
	

	/**
	 * Constructs a new instance of an {@link AbstractPrimitivePictureFilter}
	 * without arguments.
	 *
	 */
	public AbstractPrimitivePictureFilter() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.iMage.geometrify.IPrimitiveFilter#setPointGenerator(org.iMage.
	 * geometrify.IPointGenerator)
	 */
	@Override
	public void setPointGenerator(IPointGenerator pointGenerator) {
		this.pointGenerator = pointGenerator;
	}
	
	public abstract String getName();

	/**
	 * Calculates the average color of the pixels inside the primitive of an
	 * image
	 *
	 * @param original
	 *            the original image
	 * @param primitive
	 *            the primitive to compute the color for
	 * @return the average color
	 */
	protected abstract Color calculateColor(BufferedImage original, IPrimitive primitive);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.iMage.primitive.IPrimitiveFilter#apply(java.awt.image.BufferedImage)
	 */
	@Override
	public abstract BufferedImage apply(final BufferedImage image, int numberOfIterations, int numberOfSamples);

	/**
	 * Generates a new instance of a {@link IPrimitive}
	 *
	 * @return the primitive
	 */
	protected abstract IPrimitive generatePrimitive();

	/**
	 * Calculates the difference between the original image and the current
	 * image plus the specified primitive
	 *
	 * @param original
	 *            the original image to compare to
	 * @param current
	 *            the current result image
	 * @return the absolute difference
	 */
	protected abstract int calculateDifference(final BufferedImage original, BufferedImage current, IPrimitive primitive);

	/**
	 * Adds this primitive to the specified image.
	 *
	 * @param image
	 *            the image to be modified
	 * @param primitive
	 *            the primitive to add
	 */
	protected abstract void addToImage(BufferedImage image, IPrimitive primitive);

}

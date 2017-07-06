package org.iMage.geometrify;

import java.awt.Point;

/**
 * Point generator interface, providing points for generating geometric primitives.
 * 
 * @author Dominic Ziegler
 * @version 1.0
 */
public interface IPointGenerator {
	/**
	 * Generates the next (random) point.
	 * 
	 * @return a new point
	 */
	public Point nextPoint();
	
	public int nextHeight(Point p);
	
	public int nextWidth(Point p);
	
	public int nextRadious(Point p);

}
package org.iMage.geometrify;

import java.awt.Color;
import java.awt.Point;

public class Ellipse implements IPrimitive{
	private BoundingBox boundingBox;
	private Color color;
	//	Alternative Implementierung:
	//	private Polygon polygon;
	protected Point o;
	protected int height;
	protected int width;
	
	
	/**
	 * Creates a new triangle from the given vertices.
	 *
	 * @param x centre of the Ellipse
	 * @param height of the Ellipse
	 * @param width of the Ellipse
	 */
	public Ellipse(Point o, int height, int width) {
		Point upperLeft = new Point(o.x-width , o.y-height);
		Point lowerRight = new Point(o.x+width , o.y+height);
		boundingBox = new BoundingBox(upperLeft, lowerRight);
		this.o = o;
		this.height = height;
		this.width = width;
	}
	
	
	
	@Override
	public BoundingBox getBoundingBox() {
		// TODO Auto-generated method stub
		return boundingBox;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public boolean isInsidePrimitive(Point p) {
		double w = Math.abs(o.getX()-p.getX());
		double h = Math.abs(o.getY()-p.getY());
		return ((((w*w)/(width*width)) + ((h*h)/(height*height))) < 1);

	}

	@Override
	public void setColor(Color c) {
		color = c;
		
	}

}

package org.iMage.geometrify;

import java.awt.Color;
import java.awt.Point;

public class Square implements IPrimitive{
	private BoundingBox boundingBox;
	private Color color;
	//	Alternative Implementierung:
	//	private Polygon polygon;
	protected Point a;
	protected Point b;

	/**
	 * Creates a new triangle from the given vertices.
	 *
	 * @param a one of the corners
	 * @param b one of the corners
	 */
	public Square(Point a,Point b) {
		int upperLeftX = Math.min(a.x, b.x);
		int upperLeftY = Math.min(a.y, b.y);
		int lowerRightX = Math.max(a.x, b.x);
		int lowerRightY = Math.max(a.y, b.y);
		
		Point upperLeft = new Point(upperLeftX, upperLeftY);
		Point lowerRight = new Point(lowerRightX, lowerRightY);

		boundingBox = new BoundingBox(upperLeft, lowerRight);
		//		Alternative Implementierung:
		
		this.a = a;
		this.b = b;
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
		boolean b1, b2, b3, b4;
		b1 = boundingBox.getUpperLeftCorner().getX() < p.x;
		b2 = boundingBox.getLowerRightCorner().getX() > p.x;
		b3 = boundingBox.getUpperLeftCorner().getY() < p.y;
		b4 = boundingBox.getLowerRightCorner().getY() > p.y;
		return b1 && b2 && b3 && b4;
	}

	@Override
	public void setColor(Color c) {
		color = c;
		
	}

}

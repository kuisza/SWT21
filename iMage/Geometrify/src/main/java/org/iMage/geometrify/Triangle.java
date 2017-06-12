package org.iMage.geometrify;

import java.awt.Color;
import java.awt.List;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A triangle.
 *
 * @author Dominic Ziegler, Martin Blersch
 * @version 1.0
 */
public class Triangle implements IPrimitive {
	
	private Point a;
	private Point b;
	private Point c;
	private Color color;
	private BoundingBox boundingBox;
	private Polygon polygon;


	/**
	 * Creates a new triangle from the given vertices.
	 *
	 * @param a
	 *            the first vertex
	 * @param b
	 *            the second vertex
	 * @param c
	 *            the third vertex
	 */
	public Triangle(Point a, Point b, Point c) {
		this.a = a;
		this.b = b;
		this.c = c;
		Point upperLeft = new Point(Math.min(Math.min(a.x, b.x),
				c.x), Math.min(Math.min(a.y, b.y), c.y));
				Point lowerRight = new Point(Math.max(Math.max(a.x, b.x),
				c.x), Math.max(Math.max(a.y, b.y), c.y));
				this.setBoundingBox(new BoundingBox(upperLeft, lowerRight));
				
				polygon = new Polygon();
				polygon.addPoint(a.x, a.y);
				polygon.addPoint(b.x, b.y);
				polygon.addPoint(c.x, c.y);

	}

	private void setBoundingBox(BoundingBox boudingbox) {
		this.boundingBox = boudingbox;
		
	}

	@Override
	public boolean isInsidePrimitive(Point point) {
		return polygon.contains(point.x, point.y);
	}

	@Override
	public BoundingBox getBoundingBox() {
		//getting the coordinates
				int ax = (int) a.getX();
				int ay =  (int) a.getY();
				int bx =  (int) b.getX();
				int by =  (int) b.getY();
				int cx =  (int) c.getX();
				int cy =   (int) c.getY();
		//put X-coordinates in a List
				ArrayList<Integer> Xlist = new ArrayList<Integer>();
				Xlist.add(ax);
				Xlist.add(bx);
				Xlist.add(cx);
				Collections.sort(Xlist);
		//put Y-coordinates in a List
				ArrayList<Integer> Ylist = new ArrayList<Integer>();
				Ylist.add(ay);
				Ylist.add(by);
				Ylist.add(cy);
				Collections.sort(Ylist);
				
				
		return new BoundingBox(new Point(Xlist.get(0),Ylist.get(2)) , new Point(Xlist.get(2),Ylist.get(0)));
	}

	@Override
	public Color getColor() {
		
		return this.color;
	}
	
	@Override
	public void setColor(Color c) {
		this.color = c;
	}
}

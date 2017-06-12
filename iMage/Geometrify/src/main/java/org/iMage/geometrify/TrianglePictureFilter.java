package org.iMage.geometrify;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;


/**
 * Trianglepicture filter.
 *
 * @author Student
 * @version 1.0
 */
public class TrianglePictureFilter extends AbstractPrimitivePictureFilter {


	/**
	 * @param pointGenerator it generates a point?
	 */
	public TrianglePictureFilter(IPointGenerator pointGenerator) {
		super(pointGenerator);
	}

	@Override
	protected Color calculateColor(BufferedImage original, IPrimitive primitive) {
		// bandwidth: Bandbreite des Bilds (3 oder 4 Pixel)
		// width, height: Breite und Höhe des Bilds
		// wStart, wEnd, hStart, hEnd: Bounding Box Koordinaten
		// red, green, blue, alpha: RGBA-Werte
		// numPixels: Anzahl Pixel in Primitive
		int width = original.getWidth();
		int height = original.getHeight();
		int bandwidth;
		if(original.getAlphaRaster() == null) {
			bandwidth = 3;
		}
		else {
			bandwidth = 4;
		}
		int[] imgPixels = new int[width * height * bandwidth];
		original.getRaster().getPixels(0, 0, width, height,
		imgPixels);
		
		int hStart = (int) primitive.getBoundingBox().getLowerRightCorner().getY();
		int hEnd = (int) primitive.getBoundingBox().getUpperLeftCorner().getY();
		int wStart = (int) primitive.getBoundingBox().getUpperLeftCorner().getX();
		int wEnd = (int) primitive.getBoundingBox().getLowerRightCorner().getX();
		Point currPixel = null;
		int numPixels = 0;
		int alpha = 0;
		int green = 0;
		int blue = 0;
		int red = 0;
		for (int y = hStart; y <= hEnd; y++) {
			for (int x = wStart; x <= wEnd; x++) {
			
			currPixel.setLocation(x, y);
			if (primitive.isInsidePrimitive(currPixel)) {
			int pixelIndex = (y * width + x) * bandwidth;
			numPixels++;
			if (bandwidth == 4) {
			alpha += imgPixels[pixelIndex + 3];
			}
			red += imgPixels[pixelIndex + 2];
			green += imgPixels[pixelIndex + 1];
			blue += imgPixels[pixelIndex];
				}
			} 
			
		}
		
		// Normalisieren der Farbwerte
		if (numPixels > 0) {
		alpha = alpha / numPixels;
		red = red / numPixels;
		green = green / numPixels;
		blue = blue / numPixels;
		}
		// Rückgabe des durchschnittlichen Farbwerts
		if (original.getColorModel().hasAlpha()) {
		return new Color(red, green, blue, alpha);
		} else {
		return new Color(red, green, blue);
			}
		}



	@Override
	public BufferedImage apply(BufferedImage image, int numberOfIterations, int numberOfSamples) {
		int width = image.getWidth();
		int height = image.getHeight();
		int bandwidth;
		if(image.getAlphaRaster() == null) {
			bandwidth = 3;
		}
		else {
			bandwidth = 4;
		}
		BufferedImage result = new BufferedImage(width, height,
				image.getType());
				for (int i = 0; i < numberOfIterations; i++) {
				int bestDifference = Integer.MAX_VALUE;
				IPrimitive bestPrimitive = null;
				for (int s = 0; s < numberOfSamples; s++) {
				// erzeuge neues Dreieck
					IPrimitive sample = generatePrimitive();
				// berechne und speichere Durchschnittsfarbe des Dreiecks
					sample.setColor(calculateColor(image, sample));
				// berechne Farbunterschied zwischen Originalbild und
				// aktuellem Bild + Dreieck
					int difference = calculateDifference(image, result, sample);
				// merke Dreieck, falls Farbunterschied kleiner als bisher
					if (difference <= bestDifference) {
						bestDifference = difference;
						bestPrimitive = sample;
						}

				}
				addToImage(result, bestPrimitive); // Füge bestes Bild hinzu
				}
				return result; // Gib finales Bild zurück
 
	}
	

	@Override
	protected IPrimitive generatePrimitive() {
		
		  ArrayList<Point> points = new ArrayList<Point>();
		 
		 int i = 0;
		 while( i < 3) {
		  Point newpoint = this.pointGenerator.nextPoint();
		  
		  if (!points.contains(newpoint)) {
			points.add(newpoint);  
			i++;
		   }
		  }
		return new Triangle (points.get(0) , points.get(1) , points.get(2));
	}

	@Override
	protected int calculateDifference(BufferedImage original, BufferedImage current, IPrimitive primitive) {
		// bandwidth: Bandbreite des Bilds (3 oder 4 Pixel)
		// width, height: Breite und Höhe des Bilds
		// currPixel: Momentan zu betrachtender Punkt
		
		int width = original.getWidth();
		int height = original.getHeight();
		int bandwidth;
		Point currPixel = new Point();
		if(original.getAlphaRaster() == null) {
			bandwidth = 3;
		}
		else {
			bandwidth = 4;
		}
		int[] orgPixels = new int[width * height * bandwidth];
		int[] currPixels = new int[width * height * bandwidth];
		current.getRaster().getPixels(0, 0, width, height,
		currPixels);
		original.getRaster().getPixels(0, 0, width, height,
		orgPixels);

		int difference = 0;
		for (int x = 0; x < width; x++) {
		for (int y = 0; y < height; y++) {
		int pixelIndex = (y * width + x) * bandwidth;
		currPixel.setLocation(x, y);
		if (primitive.isInsidePrimitive(currPixel)) {
			int argb = primitive.getColor().getRGB();
			int red = (argb >> 16) & 0xFF;
			int green = (argb >> 8) & 0xFF;
			int blue = argb & 0xFF;
			difference += Math.abs(orgPixels[pixelIndex] - ((currPixels[pixelIndex] + blue) / 2));
			// analog für rot und grün
			if (bandwidth == 4) {
			int alpha = (argb >> 24) & 0xFF;
			// Differenzberechnung für Alpha-Kanal (s.o)
			}
		} else {
			// berechne Differenz aus original & momentanen Bild
			for (int n = 0; n < bandwidth; n++) {
			difference += Math.abs(orgPixels[pixelIndex + n]
			- currPixels[pixelIndex + n]);
			}
		}
		if (difference < 0) {
		return Integer.MAX_VALUE;
				}
		
			}
		}
		return difference;

	
		
	}

	@Override
	protected void addToImage(BufferedImage current, IPrimitive primitive) {
			// Variablendeklaration: bandwidth, width, height,
			// currPixel, wStart, wEnd, hStart, hEnd (siehe Folien)
		int hStart = (int) primitive.getBoundingBox().getLowerRightCorner().getY();
		int hEnd = (int) primitive.getBoundingBox().getUpperLeftCorner().getY();
		int wStart = (int) primitive.getBoundingBox().getUpperLeftCorner().getX();
		int wEnd = (int) primitive.getBoundingBox().getLowerRightCorner().getX();
			int width = current.getWidth();
			int height = current.getHeight();
			int bandwidth;
			Point currPixel = new Point();
			if(current.getAlphaRaster() == null) {
				bandwidth = 3;
			}
			else {
				bandwidth = 4;
			}
			int[] imgPixels = new int[width * height * bandwidth];
			current.getRaster().getPixels(0, 0, width, height, imgPixels);
			for (int y = hStart; y <= hEnd; y++) {
			for (int x = wStart; x <= wEnd; x++) {
			currPixel.setLocation(x, y);
			if (primitive.isInsidePrimitive(currPixel)) {
				int pixelIndex = (y * width + x) * bandwidth;
				int argb = primitive.getColor().getRGB();
				int red = (argb >> 16) & 0xFF;
				int green = (argb >> 8) & 0xFF;
				int blue = argb & 0xFF;
				imgPixels[pixelIndex] = Math.max(0, Math.min(255,
				((blue + imgPixels[pixelIndex]) / 2)));
				// analog für grün und rot
				if (bandwidth == 4) {
				int alpha = (argb >> 24) & 0xFF;
				// Pixelwert setzen analog zu oben
					}
				}
			}
			current.getRaster().setPixels(0, 0, width, height, imgPixels);
			}

	}
}

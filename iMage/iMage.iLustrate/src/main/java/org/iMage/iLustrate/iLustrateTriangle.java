package org.iMage.iLustrate;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.Callable;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.iMage.geometrify.IPointGenerator;
import org.iMage.geometrify.IPrimitive;
import org.iMage.geometrify.TrianglePictureFilter;

public class iLustrateTriangle extends TrianglePictureFilter{
	
	

	public iLustrateTriangle(IPointGenerator pointGenerator) {
		super(pointGenerator);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.iMage.geometrify.AbstractPrimitivePictureFilter#apply(java.awt.image.
	 * BufferedImage, int, int)
	 */
	public BufferedImage apply2(BufferedImage image, int numberOfIterations, int numberOfSamples,  BuffInterface<Void> func) {
		int width = image.getWidth();
		int height = image.getHeight();

		// construct "empty" image
		BufferedImage result = new BufferedImage(width, height, image.getType());
		BufferedImage stepimage = image;

		for (int i = 0; i < numberOfIterations; i++) {
			int bestDifference = Integer.MAX_VALUE;
			IPrimitive bestPrimitive = null;

			for (int s = 0; s < numberOfSamples; s++) {
				IPrimitive sample = generatePrimitive();
				sample.setColor(calculateColor(image, sample));
				int difference = calculateDifference(image, result, sample);

				if (difference <= bestDifference) {
					bestDifference = difference;
					bestPrimitive = sample;
				}
			}
			
			addToImage(result, bestPrimitive);

			try {
				func.setImage(result, i);
				func.call();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//String path = "C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\iMage.iLustrate\\Pictures\\thepics" + i + ".png";
			//saveimage(path,result);
		}

		return result;
	}
	
	
	
		
			

	public void saveimage(String path, BufferedImage image) {

		if (image != null) {
			try (FileOutputStream fos = new FileOutputStream("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\iMage.iLustrate\\Pictures\\thepics");
					ImageOutputStream ios = ImageIO.createImageOutputStream(fos);) {
				ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
				writer.setOutput(ios);
				
				ImageInputStream iis = ImageIO.createImageInputStream(path);
				ImageReader reader = ImageIO.getImageReadersByFormatName("png").next(); 
	        	
				reader.setInput(iis, true);
				ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
				iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // mode explicit necessary
				IIOMetadata imeta = reader.getImageMetadata(0);
				// set JPEG Quality
				iwparam.setCompressionQuality(1f);
				writer.write(imeta, new IIOImage(image, null, null), iwparam);
				writer.dispose();
			} catch (IOException e) {
				
			}
		}
	}
	
	
}

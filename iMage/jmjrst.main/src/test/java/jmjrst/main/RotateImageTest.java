package jmjrst.main;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataController;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.jis.generator.Generator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RotateImageTest {
	
	private Generator generatortest = new Generator(null , 0);
	private BufferedImage i;
	
	@Before
	public void setUp() {
		File file = new File("src/test/resources/picture.jpg");
		i = null;
	    IIOMetadata imeta = null;
		
		try
	    {
	      ImageInputStream iis = ImageIO.createImageInputStream(file);
	      ImageReader reader = ImageIO.getImageReadersByFormatName("jpg").next();
	      reader.setInput(iis, true);
	      ImageReadParam params = reader.getDefaultReadParam();
	      i = reader.read(0, params);
	      imeta = reader.getImageMetadata(0);
	    }
	    catch (IOException e)
	    {
	      System.err.println("Error while reading File: " + file.getAbsolutePath());
	      e.printStackTrace();
	      return;
	    }
	}

	@Test
	public void test() {
		assertEquals(123456, 123456);
	}
	
	@Test
	public void testrotateImage() {
		assertEquals(i, generatortest.rotateImage(i, 0.0));
		assertEquals(null, generatortest.rotateImage(null, 0.0));
		teardown(i);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testrotateImage2() { 
	     generatortest.rotateImage(i, 0.7);
	     teardown(i);
	}
	
	@Test
	public void testrotating() {
		BufferedImage image = i;
		assertEquals(image, i);
		image = generatortest.rotateImage(i, Generator.ROTATE_90);
		teardown(image);
		assertEquals(image.getWidth(null), i.getHeight(null));
		assertEquals(i.getWidth(null), image.getHeight(null));
		image = generatortest.rotateImage(image, Generator.ROTATE_270);
		assertEquals(bufferedImagesEqual(i, image), true);
		teardown(image);
	}
	
	@Test
	public void testrotatingwith180() {
		BufferedImage image =  generatortest.rotateImage(i,  Generator.ROTATE_180);
		assertEquals(i.getHeight(null), image.getHeight(null));
		assertEquals(i.getWidth(null), image.getWidth(null));
		assertEquals(buffImRotEqual(i , image), true);
		teardown(image);
	}
	
	
	
	
	boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
		
		
	    if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
	        for (int x = 0; x < img1.getWidth(); x++) {
	            for (int y = 0; y < img1.getHeight(); y++) {
	                if (img1.getRGB(x, y) != img2.getRGB(x, y))
	                    return false;
	            }
	        }
	    } else {
	        return false;
	    }
	    return true;
	}
	
	
	boolean buffImRotEqual(BufferedImage img1, BufferedImage img2) {
		boolean same = false;
		
		if (bufferedImagesEqual(img1,img2)) {
			same = true;
		}
		
		
		if (bufferedImagesEqual(generatortest.rotateImage(img1, Generator.ROTATE_90) , img2)) {
			same = true;
		}
		
		
		if (bufferedImagesEqual(generatortest.rotateImage(img1, Generator.ROTATE_180) ,img2)) {
			same = true;
		}
		
		
		if (bufferedImagesEqual(generatortest.rotateImage(img1, Generator.ROTATE_270),img2)) {
			same = true;
		}
		
		return same;
	}
	
	
	
	public void teardown(BufferedImage image){
		File f = new File("target/data_test");

		
		String timeStamp = new SimpleDateFormat("HHmmss_SSS").format(Calendar.getInstance().getTime());
		String path = "target/data_test/rotatedPicture_" + timeStamp + ".jpg";
		
		if(!f.exists()) { 
			f.mkdir();
		}
		
		try {
			   ImageIO.write(image, "jpg", new File(path));  // ignore returned boolean
			} catch(IOException e) {
			 System.out.println("Write error for " + f.getPath() +
			                               ": " + e.getMessage());
			  }
	  }
	 
	
	
	
	

}

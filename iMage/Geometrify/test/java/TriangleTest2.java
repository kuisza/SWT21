import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.iMage.geometrify.BoundingBox;
import org.iMage.geometrify.IPointGenerator;
import org.iMage.geometrify.RandomPointGenerator;
import org.iMage.geometrify.Triangle;
import org.iMage.geometrify.TrianglePictureFilter;
import org.junit.Before;
import org.junit.Test;

public class TriangleTest2 {
	private BufferedImage original;
	private BufferedImage filtered;
	private BufferedImage expected;
	private IIOMetadata imeta;
	private static final File IMAGE_FILE = new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\jmjrst.main\\src\\test\\resources\\resforapply\\dices_alpha.png");
	
	
	@Before
	
	public void setUp() throws IOException {

		original = null;
		imeta = null;
		filtered = null;
		expected = null;

		try  {
			ImageInputStream iis = ImageIO.createImageInputStream(IMAGE_FILE);
			ImageReader reader = ImageIO.getImageReadersByFormatName("png").next();
			reader.setInput(iis, true);
			ImageReadParam params = reader.getDefaultReadParam();
			original = reader.read(0, params);
			imeta = reader.getImageMetadata(0);
			reader.dispose();
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		
		RandomPointGenerator random = new RandomPointGenerator(original.getWidth(), original.getHeight());
		TrianglePictureFilter triang = new TrianglePictureFilter(random);
		
		filtered = triang.apply(original, 500, 30);
		
		

		
	}
	
	@Test
	public void isEqual() {
		boolean equals = imageEquals(original,filtered);
		assertEquals(equals,true);
	}
	

	@Test
	public void tearDown() {
		
		
		
		File outputFile = new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\jmjrst.main\\src\\test\\resources\\resforapply\\filtered.png");

		if (filtered != null) {
			try {
			    // retrieve image
			    BufferedImage bi = filtered;
			    File outputfile = new File("filtered.png");
			    ImageIO.write(bi, "png", outputfile);
			} catch (IOException e) {
			    fail();
			}
		}
		
	      
	      
	}
	
	
	public static boolean imageEquals(BufferedImage expected,
			BufferedImage actual) {
		if (expected == null || actual == null) {
			return false;
		}

		if (expected.getHeight() != actual.getHeight()) {
			return false;
		}

		if (expected.getWidth() != actual.getWidth()) {
			return false;
		}

		for (int i = 0; i < expected.getHeight(); i++) {
			for (int j = 0; j < expected.getWidth(); j++) {
				if (expected.getRGB(j, i) != actual.getRGB(j, i)) {
					return false;
				}
			}
		}

		return true;
	}
	
	
	

} 

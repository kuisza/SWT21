import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import org.iMage.geometrify.BoundingBox;
import org.iMage.geometrify.IPointGenerator;
import org.iMage.geometrify.RandomPointGenerator;
import org.iMage.geometrify.Triangle;
import org.iMage.geometrify.TrianglePictureFilter;
import org.junit.Test;

public class TriangleTest {

	@Test
	public void isInsidePrimitive() {
		Triangle triangle = new Triangle(new Point(2,12) , new Point(2,2), new Point(6,6));
		
		assertEquals(true , triangle.isInsidePrimitive(new Point(4,6)));
		assertEquals(false , triangle.isInsidePrimitive(new Point(4,18)));
	}
	
	@Test
	public void BoundingBoxTest() {
		Triangle triangle = new Triangle(new Point(2,12) , new Point(2,2), new Point(6,6));
		
		BoundingBox box = triangle.getBoundingBox();
		assertEquals(new Point(2,12) , box.getUpperLeftCorner());
		assertEquals(new Point(6,2) , box.getLowerRightCorner());
		
	}
	
	
	@Test
	public void RandomPointTest() {
		RandomPointGenerator randomP = new RandomPointGenerator(5 , 5);
		
		
		Point p;
		
		for (int i = 0; i < 100; i++) {
		p = randomP.nextPoint();
		
		
		assertEquals(true , p.getX() <= 5);
		assertEquals(true, p.getY() <= 5);
		}
	}
	
	
	@Test
	public void FilterTest() throws IOException  {
		IPointGenerator pointGenerator = null;
		TrianglePictureFilter tripigfil = new TrianglePictureFilter(pointGenerator);
		
		  ImageInputStream iis = ImageIO.createImageInputStream(new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\Geometrify\\test\\res\\dices_alpha.png"));
	      ImageReader reader = ImageIO.getImageReadersByFormatName("png").next();
	      reader.setInput(iis, true);
	      ImageReadParam params = reader.getDefaultReadParam();
	      BufferedImage DicesAlpha = reader.read(0, params);
	      
	      iis = ImageIO.createImageInputStream(new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\Geometrify\\test\\res\\dices_alpha_out_1000iterations_50samples.png"));
	      reader = ImageIO.getImageReadersByFormatName("png").next();
	      reader.setInput(iis, true);
	      params = reader.getDefaultReadParam();
	      BufferedImage DicesAlphaFilter2 = reader.read(0, params);
	      
	      iis = ImageIO.createImageInputStream(new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\Geometrify\\test\\res\\walter_no_alpha.png"));
	      reader = ImageIO.getImageReadersByFormatName("png").next();
	      reader.setInput(iis, true);
	      params = reader.getDefaultReadParam();
	      BufferedImage Walter = reader.read(0, params);
	      
	      iis = ImageIO.createImageInputStream(new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\Geometrify\\test\\res\\walter_no_alpha_out_500iterations_30samples.png"));
	      reader = ImageIO.getImageReadersByFormatName("png").next();
	      reader.setInput(iis, true);
	      params = reader.getDefaultReadParam();
	      BufferedImage WalterFilter = reader.read(0, params);
	      
	      BufferedImage testDices = tripigfil.apply(DicesAlpha, 1000, 50);
	      
	}
	
	
	

} 

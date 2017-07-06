package org.iMage.iLustrate;



import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.iMage.geometrify.AbstractPrimitivePictureFilter;
import org.iMage.geometrify.IPointGenerator;
import org.iMage.geometrify.RandomPointGenerator;
import org.iMage.geometrify.TrianglePictureFilter;

/**
 * Hello world!
 *
 */
public class App extends JFrame implements ActionListener, ChangeListener, WindowListener, MouseListener {
	private JLabel celLabel;
	private JTextField textField;
	protected IPointGenerator pointGenerator;
	private IIOMetadata imeta;
	private JButton button;
	private JPanel panel;
	private BufferedImage IMGtosave;
	private JLabel fahrLabel;
	private Timer timer = new Timer(5, this);
	int frameNumber = 0;
	private final String celText = "iLlustrae";
	private String originalname;
	private JLabel snapshots = new JLabel();
	private boolean startitornot = false;
	JLabel originalimageLabel = new JLabel();
	int snaps = 5;
	BufferedImage image1 = null;
	BufferedImage image2 = null;
	BufferedImage original = null;
	BufferedImage filtered = null;
	int iters = 100;
	int samps = 30;
	JSlider iterator = null;
	JSlider sampler = null;
	JSlider snapshotslider = null;
    JCheckBox checker = new JCheckBox("Countinous Update", true);
	JLabel iterations = null;
	JLabel samples = null;
	JFrame frame2 = new JFrame();
	BufferedImage[] images = null;
	boolean checkbox = true;
	int makepicsmall = 0;
	JButton runbutton;
	JButton loadbutton;
	JButton savebuttin;
	
	private boolean RunWindowOpene = false;
	
	
	public App() {
		setTitle(celText);
		setPreferredSize(new Dimension(400, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		initComponents();
		setVisible(true);
		pack();
		
		
		
		
		
	}
	
	private void initComponents() {
		
		if(image1 == null)  {
		image1 = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		}
		else  {
			image1 = scale(image1, 150, 150);
		}
		if(image2 == null)  {
		image2 = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB); 
			}
		else  {
			image2 = scale(image2, 150, 150);
			}
		
		if (panel != null) {
			remove(panel);
		}
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10,10,10,10);
		JLabel imageLabel = new JLabel(new ImageIcon(image1)); 
		JLabel imageLabel2 = new JLabel(new ImageIcon(image2)); 
		panel.add(imageLabel , c);
		c.gridx = 1;
		c.gridy = 0;
		panel.add(imageLabel2 , c);
		
		iterations = new JLabel("Iterations(" + String.valueOf(iters) + ") :");
		iterations.setFont(new Font(iterations.getName(), Font.PLAIN, 18));
		c.gridx = 0;
		c.gridy = 2;
		panel.add(iterations,c);
		
		samples = new JLabel("Samples(" + String.valueOf(samps) + ") :");
		samples.setFont(new Font(iterations.getName(), Font.PLAIN, 18));
		c.gridx = 0;
		c.gridy = 3;
		panel.add(samples,c);
		
		
		iterator = new JSlider(0, 2000, iters);
		iterator.addChangeListener(this);
		c.gridx = 1;
		c.gridy = 2;
		iterator.setMajorTickSpacing(2000);
		iterator.setPaintTicks(true);
		iterator.setPaintLabels(true);
		panel.add(iterator, c);
		
		sampler = new JSlider(0, 200, samps);
		sampler.addChangeListener(this);
		c.gridx = 1;
		c.gridy = 3;
		sampler.setMajorTickSpacing(200);
		sampler.setPaintTicks(true);
		sampler.setPaintLabels(true);
		panel.add(sampler, c);
		
		c.gridx = 0;
		c.gridy = 4;
		loadbutton = new JButton("Load");
		loadbutton.addActionListener(this);
		loadbutton.addMouseListener(this);
		panel.add(loadbutton, c);
		c.gridx = 1;
		c.gridy = 4;
		panel.add(Box.createRigidArea(new Dimension(50,0)) , c);
		runbutton = new JButton("Run");
		runbutton.addMouseListener(this);
		runbutton.addActionListener(this);
		panel.add(runbutton, c);
		add(panel);
				

	}


	public static void main( String[] args )
    {
		new App();
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand() == "Load") {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		 JFileChooser filechooser= new JFileChooser();
		  filechooser.setDialogTitle("Choose Your File");
		  filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		  FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "png");
		  filechooser.setFileFilter(filter);
		  frame.add(filechooser);
		  int returnval=filechooser.showOpenDialog(this);
		  if(returnval==JFileChooser.APPROVE_OPTION)
		    {
		        File file = filechooser.getSelectedFile();
		        BufferedImage bi;
		        try 
		        {   
		        	ImageInputStream iis = ImageIO.createImageInputStream(file);
		        	String extension = "";
		        	ImageReader reader = null;
		        	
		        	originalname = file.getName();

		        	int i = file.getPath().lastIndexOf('.');
		        	if (i > 0) {
		        	    extension = file.getPath().substring(i+1);
		        	}
		        	
		        	reader = ImageIO.getImageReadersByFormatName("png").next(); 
		        	
					reader.setInput(iis, true);
					ImageReadParam params = reader.getDefaultReadParam();
					BufferedImage testImage = reader.read(0, params);
					imeta = reader.getImageMetadata(0);
					reader.dispose();
					
					if (testImage.getHeight() > 1024 || testImage.getWidth() > 768) {
						//Custom button text
						Object[] options = { "No, I will countinue with this size", "Yes, please"};
						int yesorno = JOptionPane.showOptionDialog(frame,
						    "The size of your image is quite big. Would you like it that the program resizes it?",
						    "Imagesize is quite big",
						    JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null,
						    options,
						    options[1]);
						System.out.println(String.valueOf(yesorno));
						makepicsmall = yesorno;
						
					}
					
					if (makepicsmall == 0) {
						 original = testImage;
					}
					
					else if (makepicsmall == 1) {
						Dimension scales = getScaledDimension(new Dimension(testImage.getWidth(), testImage.getHeight()),new Dimension(1024, 768));
						original = scale(testImage, (int)scales.getWidth(), (int)scales.getHeight());
					}
					
					
					
					
					
				//	Dimension scaller = getScaledDimension(new Dimension(testImage.getWidth(), testImage.getHeight()),new Dimension(150, 150));
					BufferedImage originalsmall = scale(testImage, 150, 150);
					RandomPointGenerator random = new RandomPointGenerator(150 , 150);
					TrianglePictureFilter triang = new TrianglePictureFilter(random);
					
					BufferedImage filteredsmall = triang.apply(originalsmall,500, 30);
					image1 = originalsmall;
					image2 = filteredsmall;
					initComponents();
					setVisible(true);
					pack();
					frame.dispose();
					
		        }
		        catch(IOException e)
		        {
		        	 System.err.println("Exception: this is no BufferedImage: " + e.getMessage());
		        }
		  frame.pack();
		}
		
		}
		
		else if (arg0.getActionCommand() == "Run"){
			File file = new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\iMage.iLustrate\\Pictures\\thepics");
			if (file.exists()) {
			for (File file1 : file.listFiles()) {
				file1.delete();
			}
			file.delete();
			
		}
						
			frame2 = new JFrame();
			frame2.setVisible(true);
			BufferedImage filtering = original;
			images = new BufferedImage[iters+1];
			
			RandomPointGenerator random = new RandomPointGenerator(original.getWidth() , original.getHeight());
			TrianglePictureFilter triang = new TrianglePictureFilter(random);
			
			file = new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\iMage.iLustrate\\Pictures\\thepics");
			if (file.exists()) {
			for (File file1 : file.listFiles()) {
				file1.delete();
			}
			file.delete();
			
		}
			
			
			originalimageLabel = new JLabel(new ImageIcon(original));
			//originalimageLabel = new JLabel(new ImageIcon(triang.apply(original, iters, samps)));

			
			frame2.setTitle(originalname + "(" + iters + " iteratons, " + samps + " samples)" );
			int width = original.getWidth();
			int height = original.getHeight() + 300;
			//frame2.setPreferredSize(new Dimension(width , height));
			JPanel p1 = new JPanel();
			checker = new JCheckBox("Countinous Update");
			checker.setSelected(true);
			checker.addActionListener(this);
			p1.add(checker);
			
			p1.add(Box.createRigidArea(new Dimension(300 ,1)));
			savebuttin = new JButton("Save");
			savebuttin.addMouseListener(this);
			savebuttin.addActionListener(this);
            p1.add(savebuttin);
			frame2.add(p1, BorderLayout.NORTH);
			JPanel p2 = new JPanel();
			p2.add(originalimageLabel);
			frame2.add(p2, BorderLayout.CENTER);
			JPanel p3 = new JPanel();;
			snapshotslider = new JSlider(0,iters,0);
			
			snapshotslider.setMajorTickSpacing(20);
			snapshotslider.setPaintTicks(true);
			snapshotslider.setPaintLabels(true);
			Dimension d = snapshotslider.getPreferredSize();
			snapshotslider.setPreferredSize(new Dimension(d.width+70,d.height));
			snapshots.setText("     Snapshot(0)");
			snapshotslider.addChangeListener(this);
			snapshots.setFont(new Font(snapshots.getName(), Font.PLAIN, 18));
			p3.add(snapshotslider);
			p3.add(snapshots, BorderLayout.EAST);
		//p3.setPreferredSize(new Dimension(frame2.getWidth(), 300));
			
			frame2.add(p3, BorderLayout.SOUTH);
			
		
			
			frame2.pack();
			frame2.addWindowListener(this);
			
			

			
		
			
			}
		
		
		else if (arg0.getActionCommand() == "Countinous Update") {

			System.out.println(checker.isSelected());
			
		}
		
		else if (arg0.getActionCommand() == "Save") {
			JFrame chooserframe = new JFrame();
			chooserframe.setVisible(true);
			System.out.println("Save");
			 JFileChooser filechooser= new JFileChooser();
			  filechooser.setDialogTitle("Where do you want to save your file");
			  filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			  filechooser.setFileFilter( new FolderFilter() );
			  chooserframe.add(filechooser);
			  chooserframe.pack();
			  int returnval=filechooser.showOpenDialog(this);
			  if(returnval==JFileChooser.APPROVE_OPTION)
			    { 
				  File file = filechooser.getSelectedFile();
				  String filetosave = file.getAbsolutePath() + ".jpg";
				  saveimage(filetosave, IMGtosave);
				  System.out.println(file.getAbsolutePath());
				  
				  
				  
				  chooserframe.dispose();
				  
			    }
			
		}
		
	}
	
	
	
	public BufferedImage scale(BufferedImage img, int targetWidth, int targetHeight) {

	    int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
	    BufferedImage ret = img;
	    BufferedImage scratchImage = null;
	    Graphics2D g2 = null;

	    int w = img.getWidth();
	    int h = img.getHeight();

	    int prevW = w;
	    int prevH = h;

	    do {
	        if (w > targetWidth) {
	            w /= 2;
	            w = (w < targetWidth) ? targetWidth : w;
	        }

	        if (h > targetHeight) {
	            h /= 2;
	            h = (h < targetHeight) ? targetHeight : h;
	        }

	        if (scratchImage == null) {
	            scratchImage = new BufferedImage(w, h, type);
	            g2 = scratchImage.createGraphics();
	        }

	        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);

	        prevW = w;
	        prevH = h;
	        ret = scratchImage;
	    } while (w != targetWidth || h != targetHeight);

	    if (g2 != null) {
	        g2.dispose();
	    }

	    if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
	        scratchImage = new BufferedImage(targetWidth, targetHeight, type);
	        g2 = scratchImage.createGraphics();
	        g2.drawImage(ret, 0, 0, null);
	        g2.dispose();
	        ret = scratchImage;
	    }

	    return ret;

	}
	
	public void stateChanged(ChangeEvent e) {
	    JSlider source = (JSlider)e.getSource();
	    int value = (int)source.getValue();
	    
	   
	    if (source == iterator) {
	    	
	    	iters = value;
	    	iterations.setText("Iterations(" + String.valueOf(iters) + ") :");

	    	
	    	
	    }
	    else if (source == sampler){
	    	samps = value;
	    	samples.setText("Samples(" + String.valueOf(samps) + ") :");
	    	
	    }
	    
	    else if (source == snapshotslider) {
	    	checker.setSelected(false);
	    	snapshots.setText("     Snapshot(" + value + ")");
	    	File picfile = new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\iMage.iLustrate\\Pictures\\thepics\\filter" + value + ".png");
	    	if (picfile.exists()) {
	    		ImageInputStream iis = null;
				try {
					iis = ImageIO.createImageInputStream(picfile);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
	        	String extension = "";
	        	ImageReader reader = null;
	        	

	        	
	        	reader = ImageIO.getImageReadersByFormatName("png").next(); 
	        	
				reader.setInput(iis, true);
				ImageReadParam params = reader.getDefaultReadParam();
				BufferedImage toSet = null;
				try {
					toSet = reader.read(0, params);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					imeta = reader.getImageMetadata(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				reader.dispose();
				
			    BufferedImage toset = toSet;
			    
			    originalimageLabel.setIcon(new ImageIcon(toset));
			    IMGtosave = toset;
  
	    	}
	    	else  {
	    		
	    		int p = iters;
	    		File fillo = new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\iMage.iLustrate\\Pictures\\thepics\\filter" + p + ".png");;
	    		boolean exists = false;
	    		while (p > 0 && !fillo.exists()) {
	    			p--;
	    			fillo = new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\iMage.iLustrate\\Pictures\\thepics\\filter" + p + ".png");
	    			if (fillo.exists()) {
	    				snapshotslider.setValue(p);
	    			}
	    		}
	    	}
	    	

	    	
	    }
	    
	}


	protected void updatePicture(int frameNum) {
        //Get the image if we haven't already.
        if (images[frameNum] == null) {
        	originalimageLabel.setText("image #" + frameNum + " not found");
        }

        //Set the image.
        if (images[frameNum] != null) {
        	originalimageLabel.setIcon(new ImageIcon(images[frameNum]));
        } else { //image not found
        	originalimageLabel.setText("image #" + frameNum + " not found");
        }
    }
	
	

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	
		System.out.println("Run WindowClosed");
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		File file = new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\iMage.iLustrate\\Pictures\\thepics");
		for (File file1 : file.listFiles()) {
			file1.delete();
		}
		file.delete();
		System.out.println("Run WindowClosing");
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		
					System.out.println("Run window opened");
					RandomPointGenerator random = new RandomPointGenerator(original.getWidth() , original.getHeight());
					iLustrateTriangle triang = new iLustrateTriangle(random);
		
					File dir = new File("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\iMage.iLustrate\\Pictures\\thepics");
					if (!dir.exists()) {
					dir.mkdir();
					}
					else if (dir.exists()) {
					dir.delete();
					dir.mkdir();
					}
					String picname = "";
					
					BuffInterface<Void> func = new BuffInterface<Void>() {
					 	
					 	BufferedImage funcimage;
					 	int iter = 0;
					 	
					 	
						public void setImage(BufferedImage theres, int i) {
					 		funcimage = theres;
					 	//	originalimageLabel.setIcon(new ImageIcon(funcimage));
					 		iter = i;
					 		
					 	}
					 	
					 
						@Override
						public Void call() {
							if (checker.isSelected()) {
							snapshotslider.setValue(iter+1);
							checker.setSelected(true);
							originalimageLabel.setIcon(new ImageIcon(funcimage));
							}
							saveimage("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\iMage.iLustrate\\Pictures\\thepics\\filter"+ "0" + ".png" , original);
							saveimage("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\iMage.iLustrate\\Pictures\\thepics\\filter"+ (iter+1) + ".png" ,funcimage);
							return null;
							
						}

					} ;
					new Thread(new Runnable() {
					    public void run() {
					    	originalimageLabel.setIcon(new ImageIcon(original));
					    	BufferedImage lastimage = triang.apply2(original, iters, samps, func);
					    	originalimageLabel.setIcon(new ImageIcon(lastimage));
					    	snapshotslider.setValue(iters);
					    	
					    }
					}).start();
					//triang.apply2(original, iters, samps, func);
					//for (int i = 0; i < iters; i++) {
					//	BufferedImage filtering = triang.apply(original, i, samps);
					//	saveimage("C:\\Users\\bedaa\\OneDrive\\Documents\\SWT2\\iMage\\iMage.iLustrate\\Pictures\\thepics\\filter"+ i + ".png", filtering);
					//}
						
	}
	
	
	 
	
	public void saveimage(String path, BufferedImage image) {

		if (image != null) {
			try (FileOutputStream fos = new FileOutputStream(path);
					ImageOutputStream ios = ImageIO.createImageOutputStream(fos);) {
				ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
				writer.setOutput(ios);

				ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
				iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // mode explicit necessary

				// set JPEG Quality
				iwparam.setCompressionQuality(1f);
				writer.write(imeta, new IIOImage(image, null, null), iwparam);
				writer.dispose();
			} catch (IOException e) {
				
			}
		}
	}
	
	  private static class FolderFilter extends javax.swing.filechooser.FileFilter {
	      @Override
	      public boolean accept( File file ) {
	        return file.isDirectory();
	      }

	      @Override
	      public String getDescription() {
	        return "We only take directories";
	      }
	    }

	@Override
	public void mouseClicked(MouseEvent arg0) {
	
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (arg0.getComponent() == runbutton) {
		
		runbutton.setToolTipText("Create your filtered image");
		}
		
		else if (arg0.getComponent() == loadbutton) {
			loadbutton.setToolTipText("Upload image");
			}
		
		else if (arg0.getComponent() == savebuttin) {
			savebuttin.setToolTipText("Save the current image (current iteration)");
			}
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	

		
		
		
	}
	
	
	
	public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

	    int original_width = imgSize.width;
	    int original_height = imgSize.height;
	    int bound_width = boundary.width;
	    int bound_height = boundary.height;
	    int new_width = original_width;
	    int new_height = original_height;

	    // first check if we need to scale width
	    if (original_width > bound_width) {
	        //scale width to fit
	        new_width = bound_width;
	        //scale height to maintain aspect ratio
	        new_height = (new_width * original_height) / original_width;
	    }

	    // then check if we need to scale even with the new height
	    if (new_height > bound_height) {
	        //scale height to fit instead
	        new_height = bound_height;
	        //scale width to maintain aspect ratio
	        new_width = (new_height * original_width) / original_height;
	    }

	    return new Dimension(new_width, new_height);
	}
	
	
	 

	

}


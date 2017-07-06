package swingdemo;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CelciusFrame extends JFrame implements ActionListener{
	private JLabel celLabel;
	private JTextField textField;
	private JButton button;
	private JLabel fahrLabel;
	private final String celText = "Celsius converter";
	
	public CelciusFrame() {
		setTitle(celText);
		setPreferredSize(new Dimension(500, 100));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		initComponents();
		setVisible(true);
		pack();
		
	}
	
	private void initComponents() {
		celLabel = new JLabel("Celsius");
		add(celLabel);
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(75 , 20));
		add(textField);
		
		button = new JButton("Convert!");
		button.addActionListener(this);
		add(button);
		
		fahrLabel = new JLabel("Fahrentheit");
		add(fahrLabel);
		
	}

	public static void main(String[] args) {
		new CelciusFrame();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)  {
		double celcius = Double.parseDouble(textField.getText());
		int tempFahr = (int)(celcius * 1.8+32);
		fahrLabel.setText(tempFahr + " Fahrenheut");
		
	}
}

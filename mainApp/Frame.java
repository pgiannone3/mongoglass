package mainApp;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import applicationJDialog.*;
import menuBar.*;
import javax.swing.JFrame;
import mongoDB.MongoDBClient;
import upperPanel.*;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int X_AXIS = -5;
	private static final int Y_AXIS = 0;
	private static final int WIDTH= 2048;
	private static final int HEIGHT = 1024;
	private LeftPanel leftPanel;
	private MongoDBClient mongoclient;

	public MongoDBClient getMongoclient() {
		return mongoclient;
	}

	public void setMongoclient(MongoDBClient mongoclient) {
		this.mongoclient = mongoclient;
	}
	public LeftPanel getLeftPanel() {
		return leftPanel;
	}

	public void setLeftPanel(LeftPanel leftPanel) {
		this.leftPanel = leftPanel;
	}

	public Frame() {

		Frame f = this;
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				ExitApplicationDialog dialog = new ExitApplicationDialog(f, mongoclient);
				dialog.setVisible(true);
			}
		}
				);

		this.pack();
		this.setTitle("MongoGlass");

		this.setBounds(X_AXIS,Y_AXIS,WIDTH,HEIGHT);

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("mongodb.png");
			Image image = ImageIO.read(input);
			this.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		JButton settingsButton = new JButton();

		
		
		settingsButton.setText("Settings");
		settingsButton.setHorizontalTextPosition(AbstractButton.CENTER);
		settingsButton.setVerticalTextPosition(AbstractButton.BOTTOM);
		settingsButton.setToolTipText("Limit and depth settings");
		settingsButton.setFont(new Font("Arial", Font.PLAIN, 12));
		settingsButton.setEnabled(false);

		MyMenuBar menuBar = new MyMenuBar(this,settingsButton);
		this.setJMenuBar(menuBar);


	}


}
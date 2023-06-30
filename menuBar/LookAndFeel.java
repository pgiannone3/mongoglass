package menuBar;

import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mainApp.*;

public class LookAndFeel extends JMenu {

	private static final long serialVersionUID = 1L;

	public LookAndFeel(Frame frame) {

		this.setText("Theme");

		JMenuItem menuItem0 = new JMenuItem("Java");
		menuItem0.setFont(new Font("Arial", Font.PLAIN, 12));
		setIcon(menuItem0, "Java20.png");

		this.add(menuItem0);

		menuItem0.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{

					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

					for(Window window: JFrame.getWindows()) {
						SwingUtilities.updateComponentTreeUI(window);

					}

				}
				catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
			}});





		JMenuItem menuItem = new JMenuItem("Windows Classic");
		menuItem.setFont(new Font("Arial", Font.PLAIN, 12));
		setIcon(menuItem, "windowsclassic.png");

		this.add(menuItem);

		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{

					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");

					for(Window window: JFrame.getWindows()) {
						SwingUtilities.updateComponentTreeUI(window);

					}

				}
				catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
			}});

		JMenuItem menuItem1 = new JMenuItem("Windows");

		setIcon(menuItem1, "microsoft.png");

		menuItem1.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(menuItem1);

		menuItem1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}

				for(Window window: JFrame.getWindows()) {
					SwingUtilities.updateComponentTreeUI(window);

				}
			}
		});

		JMenuItem menuItem2 = new JMenuItem("Mac");

		setIcon(menuItem2, "apple.png");

		menuItem2.setFont(new Font("Arial", Font.PLAIN, 12));
		menuItem2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}

				for(Window window: JFrame.getWindows()) {
					SwingUtilities.updateComponentTreeUI(window);

				}
			}
		});


		this.add(menuItem2);


		JMenuItem menuItem3 = new JMenuItem("Unix");

		setIcon(menuItem3, "linux16.png");

		menuItem3.setFont(new Font("Arial", Font.PLAIN, 12));
		menuItem3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}

				for(Window window: JFrame.getWindows()) {
					SwingUtilities.updateComponentTreeUI(window);

				}
			}
		});


		this.add(menuItem3);		
	}

	public void setIcon(JMenuItem component, String imageString) {

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(imageString);
			Image image = ImageIO.read(input);
			component.setIcon(new ImageIcon(image));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}

}

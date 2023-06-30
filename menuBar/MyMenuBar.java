package menuBar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import mainApp.*;

public class MyMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private Frame frame;
	private SettingsMenu settings;

	
	public MyMenuBar(Frame frame, JButton settingsButton) {

		this.settings = new SettingsMenu(this.frame);
		settings.setFont(new Font("Arial",Font.PLAIN,12));
		
		this.frame = frame;
		Menu1 file = new Menu1(this.frame,settings.getAnalyzeOptions(),settingsButton);
		file.setFont(new Font("Arial",Font.PLAIN,12));

		JMenu subMenu = new JMenu("View");
		
		LookAndFeel theme = new LookAndFeel(this.frame);
		theme.setFont(new Font("Arial",Font.PLAIN,12));
		subMenu.setFont(new Font("Arial",Font.PLAIN,12));
		
		JMenuItem gray = new JMenuItem("Gray");
		gray.setFont(new Font("Arial",Font.PLAIN,12));
		
		gray.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{

					UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
					
					for(Window window: JFrame.getWindows()) {
						SwingUtilities.updateComponentTreeUI(window);
						
					}
					
				}
					catch(Exception ex) {
						System.out.println(ex.getMessage());
					}
			}});
		
		JMenuItem texture = new JMenuItem("Texture");
		texture.setFont(new Font("Arial",Font.PLAIN,12));
		
		texture.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{

					UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
					
					for(Window window: JFrame.getWindows()) {
						SwingUtilities.updateComponentTreeUI(window);
						
					}
					
				}
					catch(Exception ex) {
						System.out.println(ex.getMessage());
					}
			}});

		JMenuItem contrast = new JMenuItem("Contrast");
		contrast.setFont(new Font("Arial",Font.PLAIN,12));
		
		contrast.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{

					UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
					
					for(Window window: JFrame.getWindows()) {
						SwingUtilities.updateComponentTreeUI(window);
						
					}
					
				}
					catch(Exception ex) {
						System.out.println(ex.getMessage());
					}
			}});

		
		
		
		subMenu.add(theme);
		subMenu.addSeparator();
		subMenu.add(gray);
		subMenu.add(texture);
		subMenu.add(contrast);
		
		this.add(file);
		this.add(settings);		
		this.add(subMenu);
		

	}
	
	public SettingsMenu getSettings() {
		return settings;
	}

	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		this.setBorder(new LineBorder(new Color(170, 170, 170)));
		Graphics2D g2d = (Graphics2D) arg0;
		g2d.setColor(new Color(255,255,255));
		g2d.fillRect(0, 0, getWidth()-1, getHeight()-1);
	}

}

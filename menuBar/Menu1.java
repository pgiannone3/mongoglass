package menuBar;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import applicationJDialog.ExitApplicationDialog;
import listener.ConnectItemListener;
import mainApp.*;

public class Menu1 extends JMenu {

	private static final long serialVersionUID = 1L;
	private Frame frame;
	
	public Menu1(Frame frame, JMenuItem profilingOption, JButton settingsButton) {
	
		this.frame=frame;
		this.setText("File");
		this.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem connect = new JMenuItem("Connect");
		KeyStroke ctrlNKeyStroke = KeyStroke.getKeyStroke("control N");
		connect.setAccelerator(ctrlNKeyStroke);
		
		setIcon(connect, "connect16.png");
		
		
		JMenuItem exit = new JMenuItem("Exit");
		
		setIcon(exit, "closeButton.png");
		
		ConnectItemListener listener = new ConnectItemListener(this.frame, settingsButton,profilingOption);
		connect.addActionListener(listener);

		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ExitApplicationDialog dialog = new ExitApplicationDialog(frame,frame.getMongoclient());
				dialog.setVisible(true);
				
			}
		});
			
		this.add(connect);
		this.addSeparator();
		this.add(exit);
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


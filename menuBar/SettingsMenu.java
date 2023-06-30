package menuBar;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import mainApp.*;

public class SettingsMenu extends JMenu{

	private static final long serialVersionUID = 1L;
	private JMenuItem analyzeOptions;

	public SettingsMenu(Frame frame) {

		this.setText("Settings");

		analyzeOptions = new JMenuItem("Profiling Options");
		
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("settings16.png");
			Image image = ImageIO.read(input);
			analyzeOptions.setIcon(new ImageIcon(image));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
		analyzeOptions.setAccelerator(ctrlSKeyStroke);

		this.add(analyzeOptions);
		analyzeOptions.setEnabled(false);
	}
	public JMenuItem getAnalyzeOptions() {
		return analyzeOptions;
	}
}
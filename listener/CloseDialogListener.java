package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

public class CloseDialogListener implements ActionListener {
	
	JDialog dialog;
	
	public  CloseDialogListener(JDialog dialog) {
	
		this.dialog = dialog;
		KeyStroke k = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0);
		int w = JComponent.WHEN_IN_FOCUSED_WINDOW;
		dialog.getRootPane().registerKeyboardAction(e -> dialog.dispose(), k, w);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
	
		this.dialog.dispose();
		
	}

	
	
	
	
}

package listener;

import java.awt.Color;
import upperPanel.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class TitleMouseListener extends MouseAdapter {

	private RightPanel rightPanel;
	private JLabel label;
	private TabPanel panel;
	
	public TitleMouseListener(RightPanel rightPanel, JLabel label, TabPanel panel) {
		this.label = label;
		this.rightPanel = rightPanel;
		this.panel = panel;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		this.label.setText("  x");
		this.label.setForeground(Color.BLACK);

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(this.rightPanel.getSelectedComponent().equals(this.panel)) 
		this.label.setText("  x");
		
		if(!this.rightPanel.getSelectedComponent().equals(this.panel)) 
			this.label.setText("  ");
			
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		this.rightPanel.setSelectedComponent(this.panel);
		
	}
}

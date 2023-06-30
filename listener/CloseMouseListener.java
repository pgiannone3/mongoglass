package listener;

import java.awt.Color;
import upperPanel.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;


public class CloseMouseListener extends MouseAdapter{

	private RightPanel rightPanel;
	private JLabel label;
	private TabPanel panel;
	
	public CloseMouseListener(RightPanel rightPanel, JLabel label, TabPanel panel) {
		this.label = label;
		this.rightPanel = rightPanel;
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.rightPanel.remove(panel);
		this.rightPanel.invalidate();
		this.rightPanel.revalidate();
		this.rightPanel.repaint();
		this.rightPanel.getTabList().remove(this.panel);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.label.setText("  x");
		Color selectedColor = new Color(255,0,0);
		this.label.setForeground(selectedColor);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(this.rightPanel.getSelectedComponent().equals(this.panel)) {
			this.label.setText("  x");
			this.label.setForeground(Color.BLACK);
		}
		if(!this.rightPanel.getSelectedComponent().equals(this.panel)) 
			this.label.setText("  ");
			
		
	}
}

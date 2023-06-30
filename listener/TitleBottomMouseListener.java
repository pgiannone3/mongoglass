package listener;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

import bottomPanel.*;

public class TitleBottomMouseListener extends MouseAdapter {

	private BottomPanel bottomPanel;
	private JLabel label;
	private TabbedBottomPane panel;
	
	public TitleBottomMouseListener(BottomPanel bottomPanel, JLabel label, TabbedBottomPane panel) {
		this.label = label;
		this.bottomPanel = bottomPanel;
		this.panel = panel;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		this.label.setText("  x");
		this.label.setForeground(Color.BLACK);

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(this.bottomPanel.getSelectedComponent().equals(this.panel)) 
		this.label.setText("  x");
		
		if(!this.bottomPanel.getSelectedComponent().equals(this.panel)) 
			this.label.setText("  ");
			
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		this.bottomPanel.setSelectedComponent(this.panel);
		
	}
}

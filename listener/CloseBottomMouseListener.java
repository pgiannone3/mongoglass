package listener;


import java.awt.Color;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import org.jdesktop.swingx.JXTreeTable;

import bottomPanel.*;

public class CloseBottomMouseListener extends MouseAdapter{

	private BottomPanel bottomPanel;
	private JLabel label;
	private TabbedBottomPane panel;
	private JXTreeTable table;
	
	public CloseBottomMouseListener(JXTreeTable table, BottomPanel bottomPanel, JLabel label, TabbedBottomPane panel) {
		this.table = table;
		this.label = label;
		this.bottomPanel = bottomPanel;
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.bottomPanel.remove(panel);
		this.bottomPanel.invalidate();
		this.bottomPanel.getTabBottomList().remove(this.panel);
		this.bottomPanel.validate();
		this.bottomPanel.repaint();
		this.table.clearSelection();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.label.setText("  x");
		Color selectedColor = new Color(255,0,0);
		this.label.setForeground(selectedColor);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(this.bottomPanel.getSelectedComponent().equals(this.panel)) {
			this.label.setText("  x");
			this.label.setForeground(Color.BLACK);
			
		}
		if(!this.bottomPanel.getSelectedComponent().equals(this.panel)) 
			this.label.setText("  ");
			
		
	}
}

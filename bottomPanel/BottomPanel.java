package bottomPanel;

import java.awt.Color;
import javax.swing.JTabbedPane;

import treeTable.TabbedBottomList;

public class BottomPanel extends JTabbedPane{
 
	private static final long serialVersionUID = 1L;
	private TabbedBottomList tabBottomList;
	
	public BottomPanel() {
	
		this.revalidate();
		
		this.setBackground(new Color(255, 255, 255));	
		this.tabBottomList = new TabbedBottomList();
		this.setVisible(true);

	}
	public void setTab(TabbedBottomPane bottomPane) {
		
		this.add(bottomPane);
	}
	
	public TabbedBottomList getTabBottomList() {
		return tabBottomList;
	}
}

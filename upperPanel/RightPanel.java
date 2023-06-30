package upperPanel;

import java.awt.Color;
import javax.swing.JTabbedPane;
import bottomPanel.*;

public class RightPanel extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	private BottomPanel bottomPanel;
	private TabbedList tabList;


	public RightPanel() {		
		
		this.tabList = new TabbedList();
		Color customColor = new Color(204, 204, 204);
		this.setBackground(customColor);
		this.bottomPanel = new BottomPanel();
		this.bottomPanel.setVisible(true);
	}
	
	public BottomPanel getBottomPanel() {
		return bottomPanel;
	}
	public TabbedList getTabList() {
		return tabList;
	}
}

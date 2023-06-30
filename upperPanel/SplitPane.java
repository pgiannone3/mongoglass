package upperPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import mongoDB.*;
import com.google.common.collect.ListMultimap;

import mainApp.Frame;

public class SplitPane extends JSplitPane{

	private static final long serialVersionUID = 1L;
	private static final int SPLIT_WEIGHT  = 250;
	private LeftPanel leftPanel;
	private RightPanel rightPanel;

	public SplitPane(MongoDBClient client, ListMultimap<Database, Collection> multimap,String connectionName, Frame frame) {

		
		rightPanel = new RightPanel();
		leftPanel = new LeftPanel(client, multimap,rightPanel,connectionName,frame);
		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		this.setDividerLocation(SPLIT_WEIGHT);
		this.setLeftComponent(leftPanel);
		this.setRightComponent(rightPanel);
		this.setVisible(true);
		
		this.invalidate();
		this.validate();
		this.repaint();
		
		
	}

	public RightPanel getRightPanel() {
		return rightPanel;
	}

	
	public JScrollPane getLeftPanel() {
		return leftPanel;
	}
}


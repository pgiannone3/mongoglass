package upperPanel;
import java.awt.Color;
import javax.swing.JScrollPane;
import com.google.common.collect.ListMultimap;

import mainApp.Frame;
import mongoDB.*;

public class LeftPanel extends JScrollPane{

	public Panel getPanel() {
		return panel;
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	private static final long serialVersionUID = 1L;
	private Panel panel;
	
	public LeftPanel(MongoDBClient client, ListMultimap<Database, Collection> multimap, RightPanel rightPanel,String connectionName, Frame frame) {	

		panel = new Panel(client, multimap,rightPanel,connectionName, frame);
		this.setBackground(Color.WHITE);
		
		this.setViewportView(panel);
	
		
	}
	
}

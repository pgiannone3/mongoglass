package upperPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import mongoDB.*;
import treeMenu.*;

import com.google.common.collect.ListMultimap;

import mainApp.Frame;


public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private TreeMenu menu;
	
	public Panel(MongoDBClient client, ListMultimap<Database, Collection> multimap, RightPanel rightPanel, String connectionName,Frame frame) {
		
		this.setLayout(new BorderLayout());
		menu = new TreeMenu(client, multimap,rightPanel,connectionName, frame);
		this.add(menu, BorderLayout.PAGE_START);
		this.setBackground(Color.WHITE);

				
	}


}

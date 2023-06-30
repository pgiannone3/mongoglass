package listener;
import java.util.List;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import com.google.common.collect.ListMultimap;
import com.mongodb.DBCollection;

import configurationConnect.Configs;
import mongoDB.*;
import treeMenu.*;
import upperPanel.*;

public class TreeListener implements TreeSelectionListener{

	private TreeMenu treeMenu;
	private MapReduceWorker worker;
	private ListMultimap<Database, Collection> multimap;
	private RightPanel rightPanel;
	private MongoDBClient client;

	public TreeListener(TreeMenu treeMenu, MongoDBClient client, ListMultimap<Database, Collection> multimap, 
			RightPanel rightPanel) {
		this.rightPanel = rightPanel;
		this.multimap=multimap;
		this.treeMenu = treeMenu;
		this.client = client;
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeMenu.getLastSelectedPathComponent();

		if (node == null)
			return;

		if(node.isLeaf()) {

			Configs config = new Configs();
			LimitDepth ld = config.readLimitAndDepth(this.client.getConnectionName(), this.client.getServerName(), this.client.getPortNumber());
			int limit = ld.getLimit();
			int depth = ld.getDepth();

			TreeNode parentNode = node.getParent();
			Database db = findDB(parentNode.toString());
			Collection coll = findColl(db, node.toString());

			Settings settings = new Settings(db, coll.getColl(), client, limit, depth, coll.getCollName());
			DBCollection myCollection = null;

			if(limit != 0) {
				if(limit<coll.getColl().find().count()) {
					myCollection = settings.createTempCollection();
				}

				else if(limit>= coll.getColl().find().count()) {
					limit = 0;
				}
			}
			System.out.println(Thread.currentThread().getName() + " is working in background to run map/reduce");

			
			TabPanel p = new TabPanel(parentNode.toString(), node.toString(),this.client,limit,depth);
			
			if(this.rightPanel.getTabList().addElement(p)) { 
				worker = new MapReduceWorker(this.client, db,coll,rightPanel,p,settings, limit,depth,myCollection,this.rightPanel.getTabList());
				worker.execute();
			}
			else{
				this.rightPanel.setSelectedComponent(p);

			}
		}
	}
	public Database findDB(String s) {
		for(Database db:multimap.keySet()) {
			if(db.getDbName().equals(s))
				return db;
		}
		return null;
	}
	public Collection findColl(Database db, String s) {
		List<Collection> collection = multimap.get(db);
		for(Collection coll:collection) {
			if(s.equals(coll.getCollName()))
				return coll;
		}
		return null;
	}
}
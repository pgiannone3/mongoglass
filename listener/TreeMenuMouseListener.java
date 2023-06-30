package listener;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.google.common.collect.ListMultimap;
import com.mongodb.DBCollection;

import configurationConnect.Configs;
import mainApp.Frame;
import menuBar.PopupMenu;
import mongoDB.Collection;
import mongoDB.Database;
import mongoDB.MongoDBClient;
import mongoDB.Settings;
import treeMenu.TreeMenu;
import upperPanel.LeftPanel;
import upperPanel.LimitDepth;
import upperPanel.MapReduceWorker;
import upperPanel.RightPanel;
import upperPanel.TabPanel;

public class TreeMenuMouseListener extends MouseAdapter {

	private TreeMenu treeMenu;
	private MapReduceWorker worker;
	private ListMultimap<Database, Collection> multimap;
	private RightPanel rightPanel;
	private MongoDBClient client;
	private LeftPanel leftPanel;
	private Frame frame;


	public TreeMenuMouseListener(TreeMenu treeMenu, MongoDBClient client, ListMultimap<Database, Collection> multimap, 
			RightPanel rightPanel,LeftPanel leftPanel,Frame frame) {

		this.treeMenu = treeMenu;
		this.client = client;
		this.multimap = multimap;
		this.rightPanel = rightPanel;
		this.frame = frame;

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if(SwingUtilities.isLeftMouseButton(e)) {
			JTree treeMenu = (JTree) e.getSource();

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
		
		else if(SwingUtilities.isRightMouseButton(e)) {
			
			JTree treeMenu = (JTree) e.getSource();
			TreePath path = treeMenu.getPathForLocation ( e.getX (), e.getY () );
			Rectangle pathBounds = treeMenu.getUI ().getPathBounds ( treeMenu, path );
			
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
			
			if ( pathBounds != null && pathBounds.contains ( e.getX (), e.getY () ) && node.isRoot() ) {
				
				PopupMenu menu = new PopupMenu(this.client,leftPanel,this.treeMenu,frame);
				menu.show(treeMenu, pathBounds.x+50, pathBounds.y+10);
				
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


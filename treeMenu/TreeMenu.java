package treeMenu;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import upperPanel.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import com.google.common.collect.ListMultimap;
import listener.*;
import mainApp.Frame;
import mongoDB.*;


public class TreeMenu extends JTree{

	private static final long serialVersionUID = 1L;
	private ListMultimap<Database, Collection> multimap;
	private DefaultMutableTreeNode root;
	private MongoDBClient client;
	private LeftPanel leftPanel;

	public TreeMenu(MongoDBClient client, ListMultimap<Database, Collection> multimap, RightPanel rightPanel, String connectionName, Frame frame) {

		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) this.getCellRenderer();
		ImageIcon icon1 = null;
		ImageIcon icon2 = null;
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("database4.png");
			Image image = ImageIO.read(input);
			icon1 = new ImageIcon(image);
			
			InputStream input1 = classLoader.getResourceAsStream("folder.png");
			Image image1 = ImageIO.read(input1);
			icon2 = new ImageIcon(image1);
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		renderer.setOpenIcon(icon1);
		renderer.setLeafIcon(icon2);
		renderer.setClosedIcon(icon1);

		this.multimap = multimap;
		this.client = client;

		UIManager.put("Tree.rendererFillBackground", false);
		root = new DefaultMutableTreeNode(connectionName);

		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		this.setEditable(false);

		model.setRoot(root);

		setTreeNodes(root);

		this.addMouseListener(new TreeMenuMouseListener(this,this.client, multimap,rightPanel,leftPanel,frame));

	}


	public void setTreeNodes(DefaultMutableTreeNode root) {

		for(Database db :multimap.keySet()) {
			DefaultMutableTreeNode database = new DefaultMutableTreeNode(db.getDbName());
			root.add(database);	
			List<Collection> coll = multimap.get(db);
			for(Collection c:coll) {
				if(c.getCollName().indexOf("system")==-1)
					database.add(new DefaultMutableTreeNode(c.getCollName()));
			}
		}
	}


}
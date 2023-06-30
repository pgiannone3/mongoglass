package menuBar;

import java.awt.Font;
import mainApp.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import listener.RefreshListener;
import mongoDB.MongoDBClient;
import treeMenu.TreeMenu;
import upperPanel.LeftPanel;

public class PopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	private JMenuItem disconnect;
	private JMenuItem refresh;
	private Frame frame;
	private MongoDBClient client;


	public PopupMenu(MongoDBClient client,LeftPanel panel,TreeMenu treeMenu, Frame frame) {

		Font f = new Font("Arial",Font.PLAIN, 12);
		this.client = client;
		this.frame = frame;
		this.disconnect = new JMenuItem("Disconnect");
		
		this.refresh = new JMenuItem("Refresh");
		this.refresh.setFont(f);
		this.disconnect.setFont(f);
		this.add(disconnect);
		this.addSeparator();
		this.add(refresh);

		this.disconnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				treeMenu.clearSelection();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeMenu.getModel().getRoot();
				int childrenCount = treeMenu.getModel().getChildCount(root);

				for(int i = 0; i<childrenCount; i++) {
					DefaultMutableTreeNode child = (DefaultMutableTreeNode) treeMenu.getModel().getChild(root, i);
					child.removeAllChildren();
				}

				treeMenu.setModel(null);
				client.getClient().close();

			}
		});
		this.client.getClient().close();
		this.refresh.addActionListener(new RefreshListener(frame, client.getConnectionName(), client.getServerName(), client.getPortNumber()));
	}

	public Frame getFrame() {
		return frame;
	}




}

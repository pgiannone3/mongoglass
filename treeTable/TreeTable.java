package treeTable;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import javax.swing.ListSelectionModel;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import com.mongodb.DBCollection;
import listener.TreeTableListener;
import mongoDB.*;
import upperPanel.*;

public class TreeTable {

	private String[] headings = {"Field","Type","Number of occurrences","Rate of occurrences"};
	private Node root;
	private Collection coll;
	private Database db;
	private DefaultTreeTableModel model;
	private JXTreeTable table;
	private Settings s;
	private List<String[]> content;
	private Stack<ChildNode> nodes;
	private RightPanel panel;
	private int limit;
	private int depth;
	private DBCollection myCollection;

	public TreeTable(Database db, Collection coll, List<String[]> content, Settings s, 
			RightPanel rightPanel,int limit,int depth, DBCollection myCollection) {
		
		this.myCollection = myCollection;
		this.db = db;
		this.coll = coll;
		this.content = content;
		this.s = s;
		this.panel = rightPanel;
		this.limit = limit;
		this.depth = depth;
	}

	public JXTreeTable getTreeTable() {
		root = new RootNode("root");
		ChildNode myChild = null;

		this.nodes = new Stack<>(); 

		for(String[] data : this.content) {

			ChildNode child = new ChildNode(data);

			if(StringUtils.countMatches(data[0], ".")==0) {
				root.add(child);
				myChild = child;
			}
			else if(StringUtils.countMatches(data[0], ".")==1) {
				ChildNode a = new ChildNode(data);
				nodes.push(a);
				myChild.add(a);
			}
			else if(StringUtils.countMatches(data[0], ".")>1) {
				String k = data[0].substring(0, data[0].lastIndexOf("."));

				if(nodes.peek().getValueAt(0).equals(k)) {
					ChildNode n = nodes.peek();
					nodes.push(child);
					n.add(child);
				}
				else{

					for(int i = 0; i<nodes.size(); i++) {
						ChildNode n = nodes.get(i);
						if(k.equals(n.getValueAt(0))) {
							nodes.push(child);
							n.add(child);
							break;
						}
					}
				}
			}
		}
		model = new DefaultTreeTableModel(root,Arrays.asList(headings));
		table = new JXTreeTable(model);
		table.setShowGrid(true);
		table.setRowSelectionAllowed(true);
		
		
		
		ListSelectionModel cellSelectionModel = table.getSelectionModel();
		
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			
		cellSelectionModel.addListSelectionListener(new TreeTableListener(this.table, this.panel, this.s,this.db,this.coll, 
															this.limit, this.depth, this.myCollection));

			table.packAll();
			return table;
	}
	public RightPanel getPanel() {
		return panel;
	}

	public Collection getColl() {
		return coll;
	}

	public Database getDb() {
		return db;
	}

	public Settings getS() {
		return s;
	}
}
package listener;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;
import com.mongodb.DBCollection;

import bottomPanel.TabbedBottomPane;
import upperPanel.*;
import treeTable.Node;
import treeTable.SearchWorker;
import treeTable.TabbedBottomList;
import mongoDB.*;

public class TreeTableListener implements ListSelectionListener {

	private JXTreeTable table;
	private RightPanel panel;
	private Settings s;
	private Collection coll;
	private Database db;
	private int limit;
	private DBCollection myCollection;
	private int depth;

	public TreeTableListener(JXTreeTable table, RightPanel panel,Settings s, Database db, 
			Collection coll, int limit, int depth, DBCollection myCollection) {

		this.table=table;
		this.panel = panel;
		this.s = s;
		this.coll= coll;
		this.db=db;
		this.limit = limit;
		this.myCollection = myCollection;
		this.depth = depth;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		if(table.getSelectedRow() > -1) {

			TreePath path = this.table.getPathForRow(this.table.getSelectedRow());
			ArrayList<Object> node = new ArrayList<>();
			int pathCount = path.getPathCount();
			
			for(int i = 0; i<pathCount-1; i++) {
				Node treeNode = (Node)path.getLastPathComponent();
				String field = (String)treeNode.getValueAt(0);
				String type = (String)treeNode.getValueAt(1);
				String occ = (String)treeNode.getValueAt(2);
				String occRate = (String) treeNode.getValueAt(3);

				Node t = new Node(new Object[] {field,type,occ,occRate});
				
				
				
				if(treeNode!=null)
					node.add(t);
				path = path.getParentPath();
			}
			
			

			Collections.reverse(node);

			int row = table.getSelectedRow();
			int columnField = 0;
			int columnType = 1; 
			int columnOccurrences = 2;
			String field = (String) table.getValueAt(row, columnField);
			String type = (String) table.getValueAt(row, columnType);
			String numberOfOccurrences = (String) table.getValueAt(row, columnOccurrences);

			TabbedBottomList list = this.panel.getBottomPanel().getTabBottomList();
			boolean moreTypes = false;
			boolean isArray = false;
			
			if(type.indexOf(",")!= -1) {
				moreTypes = true;
			}
			
			for(int i = 0; i<node.size(); i++) {

				Node n = (Node) node.get(i);
				String t = (String) n.getValueAt(1);

				if(t.indexOf("Array")!= -1) {
					isArray = true;

				}
			}
						
//singolo tipo Array
			if(!moreTypes && isArray && !type.equals("Object") && !type.equals("Array")) {

				TabbedBottomPane tabPane = new TabbedBottomPane(db, coll, field, limit, depth);
				if(list.addElement(tabPane)) {

					SearchWorker worker = new SearchWorker(this.table,this.panel, field, type, s, coll, db, 
							isArray, moreTypes, limit, this.myCollection, this.depth, tabPane, node,list);
					worker.execute();

				}
				Component [] component = this.panel.getBottomPanel().getComponents();
				for(int i = 0; i<component.length; i++) {
					if(component[i].equals(tabPane)) {
						this.panel.getBottomPanel().setSelectedComponent(tabPane);
						break;
					}
				}
			}
		
//singolo tipo non array:

			else if(!isArray && !moreTypes && !type.equals("Array") && !type.equals("Object")) {

				TabbedBottomPane tabPane = new TabbedBottomPane(db, coll, field, limit, depth);

				if(list.addElement(tabPane)) {
					SearchWorker worker = new SearchWorker(this.table,this.panel, field, type, s, coll, db, 
							isArray,moreTypes,this.limit,
							this.myCollection,this.depth, tabPane,list);
					worker.execute();
				}

				else {
					Component [] component = this.panel.getBottomPanel().getComponents();
					for(int i = 0; i<component.length; i++) {
						if(component[i].equals(tabPane)) {
							this.panel.getBottomPanel().setSelectedComponent(tabPane);
							break;
						}
					}

				}

			}
			else if(moreTypes) {
//Più tipi diversi ma non array:
				if(!isArray && type.indexOf("Array")== -1) {
					TabbedBottomPane tabPane = new TabbedBottomPane(db, coll, field, limit, depth);

					if(list.addElement(tabPane)) {
						SearchWorker worker = new SearchWorker(this.table,this.panel, field, type, s, coll, db, 
								isArray,moreTypes,this.limit,
								this.myCollection,this.depth,tabPane,list);
						worker.execute();

					}

					else {
						Component [] component = this.panel.getBottomPanel().getComponents();
						for(int i = 0; i<component.length; i++) {
							if(component[i].equals(tabPane)) {
								this.panel.getBottomPanel().setSelectedComponent(tabPane);
								break;
							}
						}
					}


				}
// caso più tipi tra cui array				
				
				else {
					isArray = true;
					TabbedBottomPane tabPane = new TabbedBottomPane(db, coll, field, limit, depth);

					if(list.addElement(tabPane)) {
						SearchWorker worker = new SearchWorker(this.table,this.panel, field, type, s, coll, db, 
								isArray,moreTypes,this.limit,
								this.myCollection,this.depth,tabPane,numberOfOccurrences, node,list);
						worker.execute();

					}

					else {
						Component [] component = this.panel.getBottomPanel().getComponents();
						for(int i = 0; i<component.length; i++) {
							if(component[i].equals(tabPane)) {
								this.panel.getBottomPanel().setSelectedComponent(tabPane);
								break;
							}
						}
					}
				}
				
// caso più tipi array e più tipi elemento di array
				
		
			}
		}
	}
}


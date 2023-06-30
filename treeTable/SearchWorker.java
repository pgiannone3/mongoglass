package treeTable;

import bottomPanel.LeftBottomPanel;
import bottomPanel.RightBottomPanel;
import bottomPanel.SplitBottomPane;
import bottomPanel.TabbedBottomPane;
import fieldsTable.FieldsOccurrences;
import fieldsTable.Table;
import histogram.Histogram;
import listener.*;
import upperPanel.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import org.jdesktop.swingx.JXTreeTable;
import com.mongodb.DBCollection;

import mongoDB.*;

public class SearchWorker extends SwingWorker<Void, Void>{

	private RightPanel rightPanel;
	private String field;
	private Settings s;
	private TreeMap<KeyMap,Integer> treeMapOccurrences;
	private Table table;
	private TabbedBottomPane tabBottomPane;
	private Collection coll;
	private String type;
	private Database db;
	private JXTreeTable treeTable;
	private boolean isArray;
	private boolean moreTypes;
	private int limit;
	private DBCollection myCollection;
	private int depth;
	private ArrayList<Object> node;
	private int numberOfOccurrences;
	private TabbedBottomList list;

	public SearchWorker(JXTreeTable treeTable, RightPanel rightPanel, String field, String type, Settings s, 
			Collection coll, Database db, boolean isArray, boolean moreTypes, 
			int limit, DBCollection myCollection, int depth, TabbedBottomPane tabPane,TabbedBottomList list)
	{
		this.rightPanel = rightPanel;
		this.field = field;
		this.treeTable = treeTable;
		this.type = type;
		this.s = s;
		this.coll = coll;
		this.db = db;
		this.isArray = isArray;
		this.moreTypes = moreTypes;
		this.limit = limit;
		this.myCollection = myCollection;
		this.depth = depth;
		this.tabBottomPane = tabPane;
		this.list = list;
	}
	
	public SearchWorker(JXTreeTable treeTable, RightPanel rightPanel, String field, String type, Settings s, 
			Collection coll, Database db, boolean isArray, boolean moreTypes, 
			int limit, DBCollection myCollection, int depth, TabbedBottomPane tabPane, ArrayList<Object> node,
			TabbedBottomList list) {
	
		this.rightPanel = rightPanel;
		this.field = field;
		this.treeTable = treeTable;
		this.type = type;
		this.s = s;
		this.coll = coll;
		this.db = db;
		this.isArray = isArray;
		this.moreTypes = moreTypes;
		this.limit = limit;
		this.myCollection = myCollection;
		this.depth = depth;
		this.tabBottomPane = tabPane;
		this.node = node;
		this.list = list;
	}
	
	public SearchWorker(JXTreeTable treeTable, RightPanel rightPanel, String field, String type, Settings s, 
			Collection coll, Database db, boolean isArray, boolean moreTypes, 
			int limit, DBCollection myCollection, int depth, TabbedBottomPane tabPane, 
			String numberOfOccurrences, ArrayList<Object> node,TabbedBottomList list) {
	
		this.rightPanel = rightPanel;
		this.field = field;
		this.treeTable = treeTable;
		this.type = type;
		this.s = s;
		this.coll = coll;
		this.db = db;
		this.isArray = isArray;
		this.moreTypes = moreTypes;
		this.limit = limit;
		this.myCollection = myCollection;
		this.depth = depth;
		this.tabBottomPane = tabPane;
		this.numberOfOccurrences = Integer.parseInt(numberOfOccurrences);
		this.node = node;
		this.list = list;
	}

	@Override
	protected Void doInBackground() throws Exception {

		System.out.println("Il worker \"SearchWorker\" is working");
		try{

			if(!moreTypes) {
				if(!this.isArray)	{
					if(limit == 0)
						this.treeMapOccurrences = this.s.getSingleValues(field, type, this.coll.getColl());
					else if(limit != 0) {
						this.treeMapOccurrences = this.s.getSingleValues(field, type, myCollection);

					}
					this.rightPanel.getBottomPanel().setVisible(true);
					this.rightPanel.getBottomPanel().validate();
					this.table = new Table(field, treeMapOccurrences);

				}

				else if(this.isArray) {
					if(limit == 0)
						this.treeMapOccurrences = this.s.getArrayValues(field, type, this.coll.getColl(), node);		
					else if(limit != 0) {
						this.treeMapOccurrences = this.s.getArrayValues(field, type, myCollection, node);		

					}
					this.rightPanel.getBottomPanel().setVisible(true);
					this.rightPanel.getBottomPanel().validate();
					this.table = new Table(field, treeMapOccurrences);
				}

			}
			else if(moreTypes) {

				if(!this.isArray) {
					if(limit == 0)
						this.treeMapOccurrences = this.s.getMultipleValues(field, type, this.coll.getColl());
					else if(limit != 0) {
						this.treeMapOccurrences = this.s.getMultipleValues(field, type, myCollection);						
					}
					this.rightPanel.getBottomPanel().setVisible(true);
					this.rightPanel.getBottomPanel().validate();
					this.table = new Table(field, treeMapOccurrences);
				}
				
				else {
					
					if(limit == 0)
						this.treeMapOccurrences = this.s.getMultipleValuesArray(field, type, this.coll.getColl(), numberOfOccurrences,node);
					else if(limit != 0) {
						this.treeMapOccurrences = this.s.getMultipleValuesArray(field, type, myCollection,numberOfOccurrences,node);						
					}
					this.rightPanel.getBottomPanel().setVisible(true);
					this.rightPanel.getBottomPanel().validate();
					this.table = new Table(field, treeMapOccurrences);
				}
					
			}
			return null;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;

		}
	}

	protected void done() {

		System.out.println("Il worker \"SearchWorker\" runs done method");  

		ArrayList<FieldsOccurrences> fieldOcc = new ArrayList<>();

		int nRow = this.table.getRowCount();
		int columName = 0;
		int columnOccurrences = 2;
		String value = (String) this.table.getTableHeader().getColumnModel().getColumn(0).getHeaderValue();

		for(int index = 0; index<nRow; index++) {
			String name = (String) this.table.getValueAt(index, columName);
			int occurrences = (Integer) this.table.getValueAt(index, columnOccurrences);
			String type = (String) this.table.getValueAt(index, 1);
			fieldOcc.add(new FieldsOccurrences(name, occurrences,type));
		}

		String title = "<" + this.db.getDbName() +","+ this.coll.getCollName() + ">" + field + " - lim/dep: " + limit + "/" + depth;
		this.rightPanel.getBottomPanel().setTab(tabBottomPane);
		this.rightPanel.getBottomPanel().setSelectedComponent(tabBottomPane);
		int index = this.rightPanel.getBottomPanel().indexOfTab(title);
		JPanel pnlTab = new JPanel(new GridBagLayout());
		pnlTab.setOpaque(false);
		JLabel lblTitle = new JLabel(title);
		JLabel lblClose = new JLabel("  ");

		if(this.rightPanel.getBottomPanel().getSelectedComponent().equals(this.tabBottomPane)) {
			lblClose.setText("  x");
		}
		if(!this.rightPanel.getBottomPanel().getSelectedComponent().equals(this.tabBottomPane)) {
			lblClose.setText("  ");
		}
		lblClose.setPreferredSize(new Dimension(20, 10));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;

		pnlTab.add(lblTitle, gbc);


		lblClose.setFont(new Font("Arial", Font.LAYOUT_RIGHT_TO_LEFT, 14));
		lblClose.setForeground(Color.BLACK);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 2;
		pnlTab.add(lblClose,gbc);

		lblClose.addMouseListener(new CloseBottomMouseListener(this.treeTable,this.rightPanel.getBottomPanel(), lblClose, tabBottomPane));
		pnlTab.addMouseListener(new TitleBottomMouseListener(this.rightPanel.getBottomPanel(), lblClose,this.tabBottomPane));

		this.rightPanel.getBottomPanel().setTabComponentAt(index, pnlTab);

		this.rightPanel.getBottomPanel().setSelectedComponent(tabBottomPane);

		LeftBottomPanel leftBottom = new LeftBottomPanel();
		
		JPopupMenu menu = new JPopupMenu();
		JMenuItem closeAll = new JMenuItem("Close other tabs");
		closeAll.setFont(new Font("Arial", Font.PLAIN, 12));
		
		menu.add(closeAll);
		
		pnlTab.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
		
				if(SwingUtilities.isRightMouseButton(e)) {
					
					menu.show(pnlTab, e.getX(), e.getY());
					
				}
				
			}
		});
		
		closeAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				for(int i = 0; i<list.size(); i++) {
					TabbedBottomPane tab = list.get(i);
					if(!tab.equals(tabBottomPane)) {
						
						rightPanel.getBottomPanel().invalidate();
						rightPanel.getBottomPanel().remove(list.get(i));
						rightPanel.getBottomPanel().validate();
						rightPanel.getBottomPanel().repaint();
						list.remove(i);
						i--;
				

					}
				}
				
			}
		});
		
		Histogram chart = new Histogram(fieldOcc,value,table);
		JPanel panelChart = chart.getPanel();
		leftBottom.invalidate();
		leftBottom.setViewportView(panelChart);
		leftBottom.repaint();
		leftBottom.validate();
		
		RightBottomPanel rightBottom = new RightBottomPanel(table, leftBottom);
		
		rightBottom.validate();

		SplitBottomPane splitPane = new SplitBottomPane(rightBottom, leftBottom);
		tabBottomPane.add(splitPane,BorderLayout.CENTER);
	}
}
package upperPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import com.mongodb.DBCollection;
import listener.*;
import treeTable.TreeTable;
import mongoDB.*;

public class MapReduceWorker extends SwingWorker<Void, Void>{

	private Collection coll;
	private TabPanel p;
	private RightPanel rightPanel;
	private List<String[]> list;
	private Settings settings;
	private Database db;
	private int limit;
	private int depth;
	private DBCollection myCollection;
	private TabbedList tabbedList;
	private long start;
	private long end;

	public MapReduceWorker(MongoDBClient client, Database db, Collection coll, 
			RightPanel rightPanel, TabPanel p, Settings settings, int limit, int depth, 
			DBCollection myCollection, TabbedList tabbedList) {

		this.db = db;
		this.coll = coll;
		this.rightPanel = rightPanel;
		this.p = p;
		this.settings = settings; 
		this.limit = limit;
		this.depth = depth;
		this.myCollection = myCollection;
		this.tabbedList = tabbedList;
	}

	@Override
	protected Void doInBackground() throws Exception{
		try{			
			this.start = System.currentTimeMillis();
			if(limit==0 || limit == this.coll.getColl().find().count())
				list = settings.getFieldType(this.coll.getColl());

			else if(limit != 0) {

				list = settings.getFieldType(this.myCollection);	
			}

			return null;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	protected void done() {
		System.out.println("Il worker \"MapReduceWorker\" runs done() method");    

		String title = "<" + this.p.getDb() + "," + this.p.getColl() + "> - lim/dep: " + limit + "/" + depth;;
		this.rightPanel.addTab(title,p);
		int index = this.rightPanel.indexOfTab(title);
		JPanel pnlTab = new JPanel(new GridBagLayout());

		pnlTab.setOpaque(false);
		JLabel lblTitle = new JLabel(title);
		JLabel lblClose = new JLabel("  ");

		if(this.rightPanel.getSelectedComponent().equals(this.p)) {
			lblClose.setText("  x");
		}
		if(!this.rightPanel.getSelectedComponent().equals(this.p)) {
			lblClose.setText("  ");
		}
		lblClose.setPreferredSize(new Dimension(20, 10));

		lblClose.setFont(new Font("Arial", Font.LAYOUT_RIGHT_TO_LEFT, 14));
		lblClose.setForeground(Color.BLACK);



		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;

		pnlTab.add(lblTitle, gbc);

		lblClose.addMouseListener(new CloseMouseListener(this.rightPanel, lblClose,this.p));
		pnlTab.addMouseListener(new TitleMouseListener(this.rightPanel, lblClose,this.p));

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 2;
		pnlTab.add(lblClose,gbc);

		this.rightPanel.setTabComponentAt(index, pnlTab);
		this.rightPanel.setSelectedComponent(p);
		
		
		JPopupMenu menu = new JPopupMenu();
		JMenuItem closeAll = new JMenuItem("Close other tabs");
		closeAll.setFont(new Font("Arial", Font.PLAIN, 12));

		menu.add(closeAll);

		long end = System.currentTimeMillis();
		long durata1 = (end - start);
		long durata = (end - start);
		
		long ore = durata / (1000 * 60 * 60);
		durata -= (ore * 1000 * 60 * 60);
		long minuti = durata / (1000 * 60);
		durata -= (minuti * 1000 * 60 );
		long secondi = durata / 1000;
		
		
		System.out.println("*******Tempo*******: ******* " + durata1 + "******** ore:" + ore + " minuti: " + minuti + " secondi: " + secondi);
		
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
				
				for(int i = 0; i<tabbedList.size(); i++) {
					TabPanel tab = tabbedList.get(i);
					if(!tab.equals(p)) {
						
						rightPanel.invalidate();
						rightPanel.remove(tab);
						rightPanel.validate();
						rightPanel.repaint();
						
						tabbedList.remove(i);
						i--;
				
					}
				}
				
			}
		});

		TreeTable treeTable = new TreeTable(this.db,this.coll,list,settings,rightPanel, limit, depth, this.myCollection);
		p.add(new JScrollPane(treeTable.getTreeTable()), null);
	}
}



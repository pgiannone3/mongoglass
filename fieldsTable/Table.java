package fieldsTable;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import upperPanel.*;

public class Table extends JTable {

	private static final long serialVersionUID = 1L;
	
	private String s;
	private TreeMap<KeyMap, Integer> map;
	private List<Object []> content;

	public Table(String s, TreeMap<KeyMap, Integer> map) {
		this.s = s;
		this.map = map;

		final String [] columnsName = {this.s, "type", "Number of occurrences"};
		this.content = new ArrayList<>();
		Iterator<KeyMap> i = this.map.keySet().iterator();

		DefaultTableModel model = new DefaultTableModel(columnsName,0) {
			public boolean isCellEditable(int row,int column) {
				return false;
			}

			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return Integer.class;
				default:
					return String.class;
				}
			}
		};

		int index = 0;
		while(i.hasNext()) {
			KeyMap key = i.next();
			String field = key.getField();
			String type = key.getType();
			int occurrences = (this.map.get(key));
			content.add(new Object[]{field,type,occurrences});
			model.addRow(content.get(index));
			index++;
		}
		
		this.setModel(model);
		
		this.setPreferredScrollableViewportSize(new Dimension(400, 400));
		this.setFillsViewportHeight(true);
		this.setShowGrid(true);
		this.setRowSelectionAllowed(true);
		this.setColumnSelectionAllowed(false);
		this.setAutoCreateRowSorter(true);
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		

	}
	
	
	
}
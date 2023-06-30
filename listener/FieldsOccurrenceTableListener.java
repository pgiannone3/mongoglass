package listener;

import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;

import bottomPanel.LeftBottomPanel;
import fieldsTable.FieldsOccurrences;
import fieldsTable.Table;
import histogram.Histogram;

public class FieldsOccurrenceTableListener implements ListSelectionListener{

	private Table table;
	private ArrayList<FieldsOccurrences> list;
	private JPanel panel;
	LeftBottomPanel leftBottomPanel;

	public FieldsOccurrenceTableListener(Table table, LeftBottomPanel leftBottomPanel) {
		this.table = table;
		this.leftBottomPanel = leftBottomPanel;

	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {

		this.list = new ArrayList<>();

		if(this.table.getSelectedRow()>-1) {

			JTableHeader header = this.table.getTableHeader();
			String value = (String) header.getColumnModel().getColumn(0).getHeaderValue();

			int rows [] = this.table.getSelectedRows();
			int columName = 0;
			int columnOccurrences = 2;

			for(int index = 0; index<rows.length; index++) {
				String name = (String) this.table.getValueAt(rows[index], columName);
				int occurrences = (Integer) this.table.getValueAt(rows[index], columnOccurrences);
				String type = (String) this.table.getValueAt(rows[index], 1);
				
				FieldsOccurrences element = new FieldsOccurrences(name, occurrences,type);
				list.add(element);


				for(int idx = 0; idx<list.size(); idx++) {
					if(list.get(idx) != null)
						System.out.println(list.get(idx).getFieldName() + " " + list.get(idx).getOccurrences());
				}

				Histogram chart = new Histogram(list, value,table);
				this.panel = chart.getPanel();
				if(arg0.getValueIsAdjusting() == false) {
					this.leftBottomPanel.invalidate();
					this.leftBottomPanel.setViewportView(this.panel);
					this.leftBottomPanel.repaint();
					this.leftBottomPanel.validate();
				}

			}
		}
	}
	public ArrayList<FieldsOccurrences> getList() {
		return list;
	}
	public JPanel getPanel() {
		return panel;
	}
}
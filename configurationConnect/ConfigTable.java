package configurationConnect;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ConfigTable extends JTable {

	private static final long serialVersionUID = 1L;
	private List<Object []> content;
	private DefaultTableModel model;

	public ConfigTable(ArrayList<ConfigurationRecord> connections) {
		
		this.content = new ArrayList<>();
		final String[] columnsName = {"Connection name", "Address"};

		this.model = new DefaultTableModel(columnsName,0) {
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
					return Integer.class;
				default:
					return String.class;
				}
			}
		};

		for(int i = 0; i<connections.size(); i++) {

			String connectionName = connections.get(i).getConnectionName();
			String serverName = connections.get(i).getServerName();
			int port = connections.get(i).getPortNumber();

			String address = serverName + ":" + port;

			content.add(new Object[] {connectionName,address});
			model.addRow(content.get(i));

		}

		this.setModel(model);
		this.setPreferredScrollableViewportSize(new Dimension(400, 400));
		this.setFillsViewportHeight(true);
		this.setRowSelectionAllowed(true);
		this.setShowGrid(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		this.getTableHeader().setOpaque(false);
		this.getTableHeader().setBackground(new Color(255,255,255));
		this.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

	}

	public void editTable(String connectionName, String serverName, String portNumber, int selectedRow) {

		String address = serverName + ":" + portNumber;

		this.setValueAt(connectionName,selectedRow,0);
		this.setValueAt(address, selectedRow, 1);
		
	}
}

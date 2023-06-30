package listener;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import configurationConnect.ConfigTable;

public class ConfigTableListener implements ListSelectionListener{

	private JTable configTable;
	private ArrayList<String> configInformation;


	public ConfigTableListener(ConfigTable table) {

		this.configTable=table;

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		this.configInformation = new ArrayList<>();

		if(this.configTable.getSelectedRow()> -1) {

			int selectedRow = this.configTable.getSelectedRow();
			int columnConnentionName = 0;
			int columnAddress=1;

			String connectionName = (String) this.configTable.getValueAt(selectedRow,columnConnentionName);
			String address = (String) this.configTable.getValueAt(selectedRow, columnAddress);

			String serverName = address.substring(0, address.indexOf(":"));
			String portNumber = address.substring(address.indexOf(":")+1, address.length());


			this.configInformation.add(connectionName);
			this.configInformation.add(serverName);
			this.configInformation.add(portNumber);
			this.configInformation.add(String.valueOf(selectedRow));
		}
	}

	public ArrayList<String> getConfigInformation() {
		return configInformation;
	}
}
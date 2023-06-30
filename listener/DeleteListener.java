package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import configurationConnect.ConfigTable;
import configurationConnect.Configs;
import configurationConnect.DeleteRowDialog;

public class DeleteListener implements ActionListener {

	
	private int selectedRow;
	private ConfigTable configTable;
	private String serverName;
	private String portNumber;
	private String connectionName;
	private DeleteRowDialog dialog;
	
	public DeleteListener(DeleteRowDialog dialog,String connectionName, String address, int selectedRow, ConfigTable configTable) {
		
		this.selectedRow = selectedRow;
		this.configTable = configTable;
		this.connectionName = connectionName;
		this.serverName = address.substring(0, address.indexOf(":"));
		this.portNumber = address.substring(address.indexOf(":")+1, address.length());
		this.dialog = dialog;		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		DefaultTableModel model = (DefaultTableModel) this.configTable.getModel();
		model.removeRow(selectedRow);
		
		Configs config = new Configs();
		config.deleteSection(connectionName, serverName, Integer.parseInt(portNumber));
		this.dialog.dispose();
	}
}
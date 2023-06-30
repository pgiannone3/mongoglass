package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import applicationJDialog.*;
import configurationConnect.ConfigTable;
import configurationConnect.DeleteRowDialog;
import mainApp.Frame;

public class DeleteRowListener implements ActionListener {

	private ConfigTable configTable;
	private ConfigTableListener listener;
	private ConfigurationJDialog login;
	private Frame frame;

	public DeleteRowListener(ConfigurationJDialog login, ConfigTable configTable, 
			ConfigTableListener listener, Frame frame) 
	{

		this.configTable = configTable;
		this.listener = listener;
		this.login = login;
		this.frame=frame;

	}

	@Override
	public void actionPerformed(ActionEvent e) {


		if(this.configTable.getSelectedRow() == -1) {

			SelectConnection dialog = new SelectConnection(frame);
			dialog.setVisible(true);
		}

		else {
			ArrayList<String> list = listener.getConfigInformation();
			String connName = list.get(0);
			String address = list.get(1) + ":" + list.get(2);
			String selectedRow = list.get(3);

			DeleteRowDialog deleteDialog = new DeleteRowDialog(login,connName,address,selectedRow, configTable);
			deleteDialog.setVisible(true);
		}


	}






}

package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import applicationJDialog.*;
import configurationConnect.*;
import mainApp.Frame;

public class EditButtonListener implements ActionListener {

	private ConfigTable configTable;
	private ConfigTableListener listener;
	private ConfigurationJDialog dialog;
	private Frame frame;
	
	public EditButtonListener(ConfigurationJDialog dialog,ConfigTable table, 
			ConfigTableListener listener, Frame frame) 
	{

		this.configTable = table;
		this.listener = listener;
		this.dialog = dialog;
		this.frame=frame;


	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(this.configTable.getSelectedRow() == -1) {

			SelectConnection dialog = new SelectConnection(frame);
			dialog.setVisible(true);
			
		}

		else 
		{
			ArrayList<String> list = this.listener.getConfigInformation();

			if(list.get(3)!= null) {
				String connectionName = list.get(0);
				String serverName = list.get(1);
				String portNumber = list.get(2);
				String selectedRow = list.get(3);

				if(list != null) {
					Connection connection = new Connection(dialog, this.configTable, connectionName, 
							serverName, portNumber, selectedRow);
					connection.setVisible(true);
				}
			}
			else {
				String title = "Select a row";
				String activity = "Edit a row";
				String exceptionClass = title;
				String exMessage = "You have to select a row before edit it";

				ErrorMessage error = new ErrorMessage(dialog, title, activity, exceptionClass, exMessage);
				error.setVisible(true);
			}

		}
	}
}
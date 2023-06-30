package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import applicationJDialog.*;
import configurationConnect.*;

//This is class implements ActionListener method to handle "Save button" of class Connection
//This class add a new connection to ini file and update the table

public class ConfigListener implements ActionListener {

	private JTextField connectionName;
	private JTextField serverName;
	private JTextField portNumber;
	private JDialog connectionDialog;
	private ConfigTable configTable;

	public ConfigListener(Connection connectionDialog, 
			JTextField connectionName, JTextField serverName, JTextField portNumber, 
			ConfigTable configTable) {

		this.connectionDialog = connectionDialog;
		this.connectionName= connectionName;
		this.serverName = serverName;
		this.portNumber = portNumber;
		this.configTable=configTable;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String connectionDefaultName = "MongoDB Connection";
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher;

		if(serverName.getText().isEmpty() && portNumber.getText().isEmpty()) {

			String title = "Invalid host name and port number";
			String activity = "Add a new Connection";
			String exName = "Invalid hostname and port number";
			String error = "To add a new connection enter an address and a port number!";

			ErrorMessage errorDialog = new ErrorMessage(connectionDialog, title, activity,exName, error);
			errorDialog.setVisible(true);
		}

		else if (serverName.getText().isEmpty()) {

			String title = "Invalid hostname";
			String activity = "Add a new Connection";
			String exName = "Invalid hostname";
			String error = "To add a new connection enter an address!";

			ErrorMessage errorDialog = new ErrorMessage(connectionDialog, title, activity,exName, error);
			errorDialog.setVisible(true);


		}
		else if (portNumber.getText().isEmpty()) {
			
			String title = "Invalid port number";
			String activity = "Add a new Connection";
			String exName = "Invalid port number";
			String error = "To add a new connection enter a port number!";

			ErrorMessage errorDialog = new ErrorMessage(connectionDialog, title, activity,exName, error);
			errorDialog.setVisible(true);


		}

		else if(!connectionName.getText().isEmpty() && 
				!serverName.getText().isEmpty() && 
				!portNumber.getText().isEmpty())  {

			matcher = pattern.matcher(portNumber.getText());
			if(matcher.matches()) {

				String connectionNameValue = this.connectionName.getText();
				String serverNameValue = this.serverName.getText();
				String portNumber = this.portNumber.getText();

				String address = serverNameValue + ":" + portNumber;

				DefaultTableModel model = (DefaultTableModel)this.configTable.getModel();
				model.addRow(new Object[] {this.connectionName.getText(), address});
				
				Configs config = new Configs(connectionNameValue, serverNameValue, portNumber);
				config.writeConfig();
				
				this.configTable.changeSelection(0, 0, false, false);
				this.connectionDialog.dispose();
			

			}

			else {
				String title = "Invalid port number";
				String activity = "Add a new Connection";
				String exName = "Invalid port number";
				String error = "To add a new connection enter a valid port number!";
				ErrorMessage errorDialog = new ErrorMessage(connectionDialog, title, activity,exName, error);
				errorDialog.setVisible(true);
			}
		}

		else if(connectionName.getText().isEmpty() && 
				!serverName.getText().isEmpty() && 
				!portNumber.getText().isEmpty())  {

			matcher = pattern.matcher(portNumber.getText());
			if(matcher.matches()) {
				String connectionNameValue = connectionDefaultName;
				String serverNameValue = this.serverName.getText();
				String portNumber = this.portNumber.getText();

				String address = serverNameValue + ":" + portNumber;
				
				DefaultTableModel model = (DefaultTableModel)this.configTable.getModel();
				model.addRow(new Object[] {connectionDefaultName, address});

				Configs config = new Configs(connectionNameValue, serverNameValue, portNumber);
				config.writeConfig();
				
				this.configTable.changeSelection(0, 0, false, false);
				this.connectionDialog.dispose();
			}
			else {
				String title = "Invalid port number";
				String activity = "Add a new Connection";
				String exName = "Invalid port number";
				String error = "To add a new connection enter a valid port number!";
				ErrorMessage errorDialog = new ErrorMessage(connectionDialog, title, activity,exName, error);
				errorDialog.setVisible(true);
			}

		}
	}
}
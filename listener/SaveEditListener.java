package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JDialog;
import javax.swing.JTextField;

import applicationJDialog.*;
import configurationConnect.*;

public class SaveEditListener implements ActionListener {

	private String connectionNameToEdit;
	private JTextField fieldConnectionName;
	private String serverNameToEdit;
	private JTextField fieldServerName;
	private String portNumberToEdit;
	private JTextField fieldPortNumber;
	private JDialog connectionDialog;
	private ConfigTable configTable;
	private int selectedRow;

	public SaveEditListener(Connection connectionDialog, 
			JTextField fieldConnectionName, JTextField fieldServerName, JTextField fieldPortNumber, 
			String connectionName, String serverName, String portNumber, 
			ConfigTable configTable, int selectedRow) {

		this.connectionDialog = connectionDialog;

		this.fieldConnectionName = fieldConnectionName;
		this.fieldServerName = fieldServerName;
		this.fieldPortNumber = fieldPortNumber;
		this.selectedRow = selectedRow;
		this.connectionNameToEdit= connectionName;
		this.serverNameToEdit = serverName;
		this.portNumberToEdit = portNumber;

		this.configTable = configTable;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String connectionDefaultName = "MongoDB Connection";
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher;

		if(this.fieldServerName.getText().isEmpty() && fieldPortNumber.getText().isEmpty()) {

			String title = "Invalid host name and port number";
			String activity = "Add a new Connection";
			String exName = "Invalid hostname and port number";
			String error = "To add a new connection enter an address and a port number!";

			ErrorMessage errorDialog = new ErrorMessage(connectionDialog, title, activity,exName, error);
			errorDialog.setVisible(true);
		}

		else if (this.fieldServerName.getText().isEmpty()) {

			String title = "Invalid hostname";
			String activity = "Add a new Connection";
			String exName = "Invalid hostname";
			String error = "To add a new connection enter an address!";

			ErrorMessage errorDialog = new ErrorMessage(connectionDialog, title, activity,exName, error);
			errorDialog.setVisible(true);

		}
		else if (fieldPortNumber.getText().isEmpty()) {

			String title = "Invalid port number";
			String activity = "Add a new Connection";
			String exName = "Invalid port number";
			String error = "To add a new connection enter a port number!";

			ErrorMessage errorDialog = new ErrorMessage(connectionDialog, title, activity,exName, error);
			errorDialog.setVisible(true);

		}

		else if(! this.fieldConnectionName.getText().isEmpty() &&
				!this.fieldServerName.getText().isEmpty() && 
				!this.fieldPortNumber.getText().isEmpty())  {

			matcher = pattern.matcher(this.fieldPortNumber.getText());
			if(matcher.matches()) {

				String connectionNameValue = this.fieldConnectionName.getText();
				String serverNameValue = this.fieldServerName.getText();
				String portNumber = this.fieldPortNumber.getText();

				this.configTable.editTable(connectionNameValue, serverNameValue,portNumber, selectedRow);

				Configs config = new Configs(connectionNameValue, serverNameValue, portNumber);
				config.writeConfig();
				config.deleteSection(this.connectionNameToEdit, this.serverNameToEdit, Integer.parseInt(this.portNumberToEdit));

				this.connectionDialog.dispose();
				this.configTable.clearSelection();
				
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

		else if(this.fieldConnectionName.getText().isEmpty() &&
				!this.fieldServerName.getText().isEmpty() && 
				!this.fieldPortNumber.getText().isEmpty())  {

			matcher = pattern.matcher(this.fieldPortNumber.getText());

			if(matcher.matches()) {
				String connectionNameValue = connectionDefaultName;
				String serverNameValue = this.fieldServerName.getText();
				String portNumber = this.fieldPortNumber.getText();

				this.configTable.editTable(connectionNameValue, serverNameValue,portNumber, selectedRow);

				Configs config = new Configs(connectionNameValue, serverNameValue, portNumber);
				config.writeConfig();
				config.deleteSection(this.connectionNameToEdit, this.serverNameToEdit, Integer.parseInt(this.portNumberToEdit));			
				this.connectionDialog.dispose();
				this.configTable.clearSelection();

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
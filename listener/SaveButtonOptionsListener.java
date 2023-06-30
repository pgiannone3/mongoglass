package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;

import applicationJDialog.*;
import configurationConnect.Configs;
import configurationConnect.ErrorMessage;

public class SaveButtonOptionsListener implements ActionListener {

	private JTextField limit;
	private JTextField depth;
	private Configs config;
	private SettingsJDialog dialog;

	public SaveButtonOptionsListener(SettingsJDialog dialog, JTextField textField, JTextField textField_1, String connectionName,
			String serverName, int portNumber) {

		this.dialog = dialog;
		this.limit=textField;
		this.depth = textField_1;
		this.config = new Configs(connectionName, serverName, String.valueOf(portNumber));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Pattern p = Pattern.compile("[0-9]+");

		if(this.limit.getText().isEmpty() && this.depth.getText().isEmpty()) { 

			int limit = 0;
			int depth = 0;
			this.config.writeLimitAndDepth(limit, depth);
			this.dialog.dispose();
			
		}
		
		else if(!this.limit.getText().isEmpty() && this.depth.getText().isEmpty()) {

			Matcher m = p.matcher(this.limit.getText());
			if (m.matches()) {
				int limit = Integer.parseInt(this.limit.getText());
				this.config.writeLimitAndDepth(limit, 0);
				this.dialog.dispose();
			}
			else{
				String title = "Only numbers are allowed for limit field";
				String activity = "Set a new limit for map/reduce";
				String exceptionClass = title;
				String exMessage = "Enter a number or leave blank for default settings";
				ErrorMessage errorDialog = new ErrorMessage(this.dialog, title, activity, exceptionClass, exMessage);
				errorDialog.setVisible(true);
			}
		}

		else if(this.limit.getText().isEmpty() && !this.depth.getText().isEmpty()) {

			Matcher m = p.matcher(this.depth.getText());
			if (m.matches()) {
				int depth = Integer.parseInt(this.depth.getText());
				this.config.writeLimitAndDepth(0, depth);
				this.dialog.dispose();
			}
			else{
				String title = "Only numbers are allowed for depth field";
				String activity = "Set a new max depth for map/reduce";
				String exceptionClass = title;
				String exMessage = "Enter a number or leave blank for default settings";
				ErrorMessage errorDialog = new ErrorMessage(this.dialog, title, activity, exceptionClass, exMessage);
				errorDialog.setVisible(true);
			}
		}
		else if(!this.limit.getText().isEmpty() && !this.depth.getText().isEmpty()) {

			Matcher m = p.matcher(this.limit.getText());
			Matcher m1 = p.matcher(this.depth.getText());
			if (m.matches() && m1.matches()) {
				int depth = Integer.parseInt(this.depth.getText());
				int limit = Integer.parseInt(this.limit.getText());
				this.config.writeLimitAndDepth(limit, depth);
				this.dialog.dispose();
			}
			else{
				String title = "Only numbers are allowed for depth and limit field";
				String activity = "Set a new max depth and a limit for map/reduce";
				String exceptionClass = title;
				String exMessage = "Enter a number or leave blank for default settings";
				ErrorMessage errorDialog = new ErrorMessage(this.dialog, title, activity, exceptionClass, exMessage);
				errorDialog.setVisible(true);
			}
		}
	}
}
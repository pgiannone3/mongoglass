package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import applicationJDialog.*;
import configurationConnect.*;


public class ConnectionButtonListener implements ActionListener {
	
	private ConfigurationJDialog login;
	private ConfigTable configTable;
	
	public ConnectionButtonListener(ConfigurationJDialog login, ConfigTable configTable) {
		
		this.login = login;
		this.configTable = configTable;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Connection connection = new Connection(this.login, this.configTable);
		connection.setVisible(true);
	}
	
	
	
	
}

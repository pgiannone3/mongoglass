package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import applicationJDialog.*;
import mainApp.*;

public class ProfilingOptionsListener implements ActionListener{

	private String connectionName;
	private String serverName;
	private int portNumber;
	private Frame frame;
	
	
	 public ProfilingOptionsListener(Frame frame, String connectionName, String serverName, int portNumber) {
	
		 this.connectionName = connectionName;
		 this.serverName = serverName;
		 this.portNumber = portNumber;
		 this.frame = frame;
		 
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		SettingsJDialog settingsDialog = new SettingsJDialog(this.frame, this.connectionName,this.portNumber, this.serverName);
		settingsDialog.setVisible(true);
		
	}

	
	
}

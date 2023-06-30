package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JMenuItem;

import applicationJDialog.*;
import configurationConnect.*;
import mainApp.*;

public class ConnectItemListener implements ActionListener, ItemListener {

	private Frame frame;
	private ConfigurationJDialog login;
	private JMenuItem profilingOption;
	private JButton settingsButton;


	public ConnectItemListener(Frame frame, JButton settingsButton,JMenuItem profiligOption) {

		this.frame = frame;
		this.settingsButton = settingsButton;
		this.profilingOption = profiligOption;

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Configs readConfiguration = new Configs();
		ArrayList<ConfigurationRecord> connections = readConfiguration.readConfig();
		ConfigTable connectionTable = new ConfigTable(connections);
		connectionTable.setVisible(true);
		this.login = new ConfigurationJDialog(this.frame, connectionTable, this.profilingOption,settingsButton);
		login.setVisible(true);

		connectionTable.changeSelection(0, 0, false, false);
	}

	public ConfigurationJDialog getLogin() {
		return login;
	}

}

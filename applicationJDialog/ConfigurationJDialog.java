package applicationJDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import configurationConnect.*;
import listener.*;
import mainApp.*;

//Dialog with Create Connection, Edit, Delete buttons

public class ConfigurationJDialog extends JDialog{

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	
	private JScrollPane tablePanel;
	private ConfigTable configurationTable;
	private Frame frame;
	private JMenuItem profilingOptions;
	
	public ConfigurationJDialog(Frame frame, ConfigTable configurationTable,JMenuItem profilingOptions,JButton settingsButton) {
		
		/********************************JDIALOG CONFIGURATION /***************************************/
		
		super(frame, ModalityType.APPLICATION_MODAL);
		this.profilingOptions = profilingOptions;
		
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("mongoconn.gif");
			Image image = ImageIO.read(input);
			this.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.setTitle("Connection Manager");
		
		this.profilingOptions = profilingOptions;
		
		setBounds(500, 200, 386, 380);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		this.frame = frame;
		
		/*********************************BUTTONS***************************************************/
		
		JButton createConnection = new JButton("New Connection");
		createConnection.setBackground(new Color(240,240,240));
		createConnection.setActionCommand("New Connection");
		createConnection.setFont(new Font("Arial", Font.PLAIN, 12));
		
		setIcon(createConnection,"add.png");
		
		
		JButton editConnection = new JButton("Edit");
		editConnection.setBackground(new Color(240,240,240));
		editConnection.setActionCommand("Edit");
		editConnection.setFont(new Font("Arial", Font.PLAIN, 12));

		setIcon(editConnection,"edit.png");
		
		JButton deleteConnection = new JButton("Delete");
		deleteConnection.setBackground(new Color(240,240,240));
		deleteConnection.setActionCommand("Delete");
		deleteConnection.setFont(new Font("Arial", Font.PLAIN, 12));
		
		setIcon(deleteConnection,"delete.png");
		
		
		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout());
		panel.add(createConnection, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(editConnection,BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		panel_3.add(deleteConnection,BorderLayout.WEST);

		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		panel_2.setBackground(UIManager.getColor("ComboBox.buttonHighlight"));
		contentPanel.add(panel_2, BorderLayout.CENTER);
		
		/******************************JSCROLLPANE FOR CONFIGURATION TABLE*************************/
		
		this.configurationTable = configurationTable;
		this.tablePanel = new JScrollPane();
		panel_2.add(tablePanel, BorderLayout.CENTER);
		tablePanel.setViewportView(this.configurationTable);

		
		/******************************J-PANEL CONNECT/CLOSE BUTTONS******************************/
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton connectButton = new JButton("Connect");
		connectButton.setActionCommand("Connect");
		connectButton.setBackground(new Color(240,240,240));
		buttonPane.add(connectButton);
		
		
		JButton cancelButton = new JButton("Close");
		cancelButton.setActionCommand("Close");
		cancelButton.setBackground(new Color(240,240,240));
		buttonPane.add(cancelButton);

		/*********************************BUTTONS LISTENER********************************************/
	
		ConfigTableListener listener = new ConfigTableListener(this.configurationTable);
		this.configurationTable.getSelectionModel().addListSelectionListener(listener);
		
		createConnection.addActionListener(new ConnectionButtonListener(this, this.configurationTable));
		
		editConnection.addActionListener(new EditButtonListener(this, this.configurationTable, listener,frame));
	
		deleteConnection.addActionListener(new DeleteRowListener(this, this.configurationTable,listener, frame));
	
		cancelButton.addActionListener(new CloseDialogListener(this));
	
		connectButton.addActionListener(new ConnectButtonListener(this.configurationTable,this,listener, this.frame, this.profilingOptions));
	}
	
	public void setIcon(JButton component, String imageString) {
		
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(imageString);
			Image image = ImageIO.read(input);
			component.setIcon(new ImageIcon(image));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
}
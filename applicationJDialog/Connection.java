package applicationJDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import configurationConnect.*;
import listener.*;

public class Connection extends JDialog{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField connectionName;
	private JTextField serverName;
	private JTextField portNumber;
	private JButton btnSave;
	private JButton btnCancel;
	private ConfigTable configTable;
	private int selectedRow;


	public Connection(ConfigurationJDialog login, ConfigTable configTable) {

		super(login, ModalityType.APPLICATION_MODAL);
		this.configTable = configTable;

		connectionSettings();

		this.setTitle("New Connection");

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("add.png");
			Image image = ImageIO.read(input);
			this.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		btnSave.addActionListener(new ConfigListener(this,
				this.connectionName,this.serverName,this.portNumber, this.configTable));

		btnCancel.addActionListener(new CloseDialogListener(this));

	}

	public Connection(ConfigurationJDialog login, ConfigTable configTable, 
			String connectionNameString, String serverNameString, 
			String portNumberString, String selectedRow) {

		super(login, ModalityType.APPLICATION_MODAL);

		connectionSettings();
		this.connectionName.setText(connectionNameString);
		this.serverName.setText(serverNameString);
		this.portNumber.setText(portNumberString);
		this.configTable = configTable;
		this.selectedRow = Integer.parseInt(selectedRow);

		this.setTitle("Edit Connection");

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("edit.png");
			Image image = ImageIO.read(input);
			this.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		btnSave.addActionListener(new SaveEditListener(this, this.connectionName, this.serverName,this.portNumber,
				this.connectionName.getText(),this.serverName.getText(),this.portNumber.getText(),
				this.configTable, this.selectedRow));

		btnCancel.addActionListener(new CloseDialogListener(this));
	}

	private void connectionSettings() {
		try{
			Font f = new Font("Arial", Font.PLAIN, 12);

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			this.setBounds(470, 300, 454, 313);
			this.validate();
			this.getContentPane().setLayout(new BorderLayout());
			this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
			{
				JPanel panel = new JPanel();
				panel.setBounds(10, 11, 418, 55);
				contentPanel.add(panel);
				panel.setLayout(null);
				{
					JLabel lblNewLabel = new JLabel("Enter a name for this connection:");
					lblNewLabel.setFont(f);
					lblNewLabel.setBounds(0, 0, 296, 14);
					panel.add(lblNewLabel);

				}
				connectionName = new JTextField();
				connectionName.setFont(f);
				connectionName.setBounds(0, 22, 418, 20);
				panel.add(connectionName);
				connectionName.setColumns(10);
			}

			JPanel panel = new JPanel();
			panel.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(109, 109, 109)));
			panel.setBackground(UIManager.getColor("ComboBox.buttonHighlight"));
			panel.setBounds(10, 77, 418, 150);
			contentPanel.add(panel);
			panel.setLayout(null);

			JLabel lblServer = new JLabel("Server:");
			
			setIcon(lblServer,null,"serverLabel1.png");
			
			
			
			lblServer.setFont(f);
			lblServer.setBounds(10, 14, 73, 14);
			panel.add(lblServer);

			serverName = new JTextField();
			serverName.setFont(f);
			serverName.setBorder(new LineBorder(UIManager.getColor("ComboBox.buttonDarkShadow")));
			serverName.setBounds(80, 12, 163, 20);
			panel.add(serverName);
			serverName.setColumns(10);

			JLabel lblPort = new JLabel("Port:");
			setIcon(lblPort,null, "socketLabel.png");
						
			lblPort.setFont(f);
			lblPort.setBounds(265, 14, 46, 14);
			panel.add(lblPort);

			portNumber = new JTextField();
			portNumber.setColumns(10);
			portNumber.setBorder(new LineBorder(UIManager.getColor("ComboBox.buttonDarkShadow")));
			portNumber.setBounds(321, 12, 87, 20);
			panel.add(portNumber);

			JLabel lblEnter = new JLabel("Enter the host name or IP address of your mongodb server");
			lblEnter.setFont(f);
			lblEnter.setBounds(82, 41, 326, 14);
			panel.add(lblEnter);

			JPanel panel_1 = new JPanel();
			panel_1.setBounds(27, 226, 401, 38);
			contentPanel.add(panel_1);
			panel_1.setLayout(null);

			this.btnSave = new JButton("Save");
			btnSave.setActionCommand("Save");
			setIcon(null, btnSave, "saveButton1.png");			
			btnSave.setFont(f);
			btnSave.setBounds(195, 11, 97, 23);
			btnSave.setBackground(new Color(225,225,225));
			panel_1.add(btnSave);

			this.btnCancel = new JButton("Cancel");
			btnCancel.setActionCommand("Cancel");
			setIcon(null, btnCancel, "closeButton.png");						
			btnCancel.setFont(f);
			btnCancel.setBounds(302, 11, 97, 23);
			btnCancel.setBackground(new Color(225,225,225));
			panel_1.add(btnCancel);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void setIcon(JLabel label, JButton button, String imageString) {

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(imageString);
			Image image = ImageIO.read(input);
			if(label != null) {
			label.setIcon(new ImageIcon(image));
			}
			if(button != null) {
				button.setIcon(new ImageIcon(image));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
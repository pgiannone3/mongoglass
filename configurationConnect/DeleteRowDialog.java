package configurationConnect;

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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import listener.CloseDialogListener;
import listener.*;
public class DeleteRowDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private int selectedRow;
	private ConfigTable configTable;
	
	public DeleteRowDialog(JDialog dialog,String connection, String address, 
												String selectedRow, ConfigTable configTable) {
		
		super(dialog,ModalityType.APPLICATION_MODAL);
		Font f = new Font("Arial", Font.PLAIN, 12);
		this.selectedRow = Integer.parseInt(selectedRow);
		this.configTable = configTable;
		
		
		this.setTitle("Are you sure?");
		setBounds(450, 200, 520, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("mark16x16.png");
			Image image = ImageIO.read(input);
			this.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 6, 57, 48);
		contentPanel.add(lblNewLabel);
		
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("mark2.png");
			Image image = ImageIO.read(input);
			lblNewLabel.setIcon(new ImageIcon(image));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		JLabel lblNewLabel_1 = new JLabel("You're deleting the following connection:");
		lblNewLabel_1.setBounds(72, 6, 368, 20);
		contentPanel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(f);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 12));
		btnNewButton.setBackground(new Color(220,220,220));
		btnNewButton.setBounds(281,118, 100, 23);
		contentPanel.add(btnNewButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCancel.setBackground(new Color(220,220,220));
		btnCancel.setBounds(389,118, 100, 23);
		contentPanel.add(btnCancel);
		
		JPanel panel = new JPanel();
		panel.setBounds(72, 37, 411, 70);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblConnectionName = new JLabel("Connection Name:");
		lblConnectionName.setBounds(0, 11, 126, 14);
		panel.add(lblConnectionName);
		lblConnectionName.setFont(f);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(0, 42, 126, 14);
		panel.add(lblAddress);
		lblAddress.setFont(f);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setFont(new Font("Arial", Font.BOLD, 13));
		textField.setDisabledTextColor(Color.BLACK);
		textField.setBorder(new LineBorder(new Color(171, 173, 179)));
		textField.setBounds(139, 8, 262, 20);
		panel.add(textField);
		textField.setColumns(10);
		textField.setText(connection);
		
		textField_1 = new JTextField();
		textField_1.setBorder(new LineBorder(new Color(171, 173, 179)));
		textField_1.setFont(new Font("Arial", Font.BOLD, 13));
		textField_1.setDisabledTextColor(Color.BLACK);
		textField_1.setEnabled(false);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(139, 39, 262, 20);
		panel.add(textField_1);
		textField_1.setText(address);
		
		btnCancel.addActionListener(new CloseDialogListener(this));
		
		btnNewButton.addActionListener(new DeleteListener(this,connection,address,this.selectedRow,this.configTable));
				
	}
}


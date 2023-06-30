package applicationJDialog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.border.LineBorder;

import configurationConnect.Configs;
import listener.*;
import mainApp.*;
import upperPanel.*;
import java.awt.Color;

public class SettingsJDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private String connectionName;
	private String serverName;
	private int portNumber;

	public SettingsJDialog(Frame frame, String connectionName, int portNumber, String serverName) {
		super(frame,ModalityType.APPLICATION_MODAL);

		this.invalidate();
		this.validate();
		this.connectionName=connectionName;
		this.serverName=serverName;
		this.portNumber=portNumber;

		Configs config = new Configs();
		LimitDepth limitDept = config.readLimitAndDepth(connectionName, serverName, portNumber);
		int limit = limitDept.getLimit();
		int depth = limitDept.getDepth();

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(470, 300, 459, 266);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("settings.png");
			Image image = ImageIO.read(input);
			this.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.setTitle("Settings");

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 34, 423, 56);
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel lblLimit = new JLabel("Limit:");
		lblLimit.setBounds(10, 13, 54, 14);
		lblLimit.setFont(new Font("Arial", Font.PLAIN, 12));
		panel.add(lblLimit);

		textField = new JTextField();
		textField.setBounds(51, 11, 328, 20);
		textField.setText(String.valueOf(limit));
		panel.add(textField);
		textField.setColumns(10);

		JButton info = new JButton("");

		info.setBackground(SystemColor.menu);
		info.setBounds(385, 10, 30, 24);
		panel.add(info);
		info.setBorder(null);
		
		setIcon(info, "info2.png");
		
		UIManager.put("ToolTip.background", new Color(241,245,147));
		info.setToolTipText("Use this option for big collection. Leave it blank or enter 0 to use all documents.");

		JLabel lblNewLabel_1 = new JLabel("e.g. enter 1000 to analyze the first 1000 documents.");
		lblNewLabel_1.setBounds(51, 32, 288, 14);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		panel.add(lblNewLabel_1);

		info.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JButton button = (JButton) e.getSource();
				button.setBorder(null);


			}

			@Override
			public void mouseEntered(MouseEvent e) {
				JButton button = (JButton) e.getSource();
				button.setBorder(new LineBorder(new Color(1,1,1)));

			}

			@Override
			public void mouseClicked(MouseEvent e) {


			}
		});

		JTextArea txtrAnalyzeALimited = new JTextArea();
		txtrAnalyzeALimited.setDisabledTextColor(Color.BLACK);
		txtrAnalyzeALimited.setBackground(SystemColor.menu);
		txtrAnalyzeALimited.setEnabled(false);
		txtrAnalyzeALimited.setEditable(false);
		txtrAnalyzeALimited.setFont(new Font("Arial", Font.PLAIN, 12));
		txtrAnalyzeALimited.setText("Analyze a limited number of documents:");
		txtrAnalyzeALimited.setBounds(10, 11, 382, 19);
		contentPanel.add(txtrAnalyzeALimited);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setText("Analyze documents to a maximum depth:");
		textArea_1.setFont(new Font("Arial", Font.PLAIN, 12));
		textArea_1.setEnabled(false);
		textArea_1.setEditable(false);
		textArea_1.setDisabledTextColor(Color.BLACK);
		textArea_1.setBackground(SystemColor.menu);
		textArea_1.setBounds(10, 100, 382, 19);
		contentPanel.add(textArea_1);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 123, 423, 56);
		contentPanel.add(panel_1);

		JLabel label = new JLabel("Depth:");
		label.setFont(new Font("Arial", Font.PLAIN, 12));
		label.setBounds(10, 13, 54, 14);
		panel_1.add(label);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setText(String.valueOf(depth));
		textField_1.setBounds(51, 11, 328, 20);
		panel_1.add(textField_1);
		JButton info1 = new JButton("");
		info1.setBounds(385, 10, 30, 24);
		panel_1.add(info1);

		info1.setBackground(SystemColor.menu);
		info1.setBorder(null);		
		setIcon(info1, "info2.png");
		{
			UIManager.put("ToolTip.background", new Color(241,245,147));
			info1.setToolTipText("Use this option for very deep nested collections. Leave it blank or enter 0 to use all documents.");

			JLabel lblNewLabel = new JLabel("e.g. enter 3 to analyze untill doc1:{doc2:{doc3:{depth: 3}}}");
			lblNewLabel.setBounds(51, 32, 372, 14);
			lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
			panel_1.add(lblNewLabel);

			JPanel panel_2 = new JPanel();
			panel_2.setBounds(222, 182, 220, 36);
			contentPanel.add(panel_2);
			panel_2.setLayout(null);


			JButton btnSave = new JButton("Save");
			setIcon(btnSave, "saveButton1.png");
			
			btnSave.setBounds(10, 11, 97, 23);
			btnSave.setFont(new Font("Arial", Font.PLAIN, 12));
			btnSave.setBackground(new Color(225,225,225));
			panel_2.add(btnSave);


			JButton btnCancel = new JButton("Cancel");
			setIcon(btnCancel, "closeButton.png");
			btnCancel.setFont(new Font("Arial", Font.PLAIN, 12));
			btnCancel.setBounds(117, 11, 97, 23);
			btnCancel.setBackground(new Color(225,225,225));
			panel_2.add(btnCancel);



			btnSave.addActionListener(new SaveButtonOptionsListener(this,this.textField,this.textField_1,
					this.connectionName,this.serverName,
					this.portNumber));

			btnCancel.addActionListener(new CloseDialogListener(this));



			info1.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
					JButton button = (JButton) e.getSource();
					button.setBorder(null);


				}

				@Override
				public void mouseEntered(MouseEvent e) {
					JButton button = (JButton) e.getSource();
					button.setBorder(new LineBorder(new Color(1,1,1)));

				}

				@Override
				public void mouseClicked(MouseEvent e) {


				}
			});
		}
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
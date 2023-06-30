package configurationConnect;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import listener.CloseDialogListener;

import javax.swing.JLabel;

public class ErrorMessage extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public ErrorMessage(JDialog dialog, String title, String activity, String exceptionClass, String exMessage) {

		super(dialog,ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.setTitle(title);
		this.setResizable(false);
		this.setBounds(470, 300, 498, 225);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(10, 11, 40, 48);
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("error3.png");
			Image image = ImageIO.read(input);
			lblNewLabel.setIcon(new ImageIcon(image));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



		contentPanel.add(lblNewLabel);

		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 12));
		btnNewButton.setBackground(new Color(220,220,220));
		btnNewButton.setBounds(204, 154, 92, 23);
		contentPanel.add(btnNewButton);

		btnNewButton.addActionListener(new CloseDialogListener(this));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(49, 11, 397, 60);
		contentPanel.add(scrollPane);

		JTable table = new JTable(); {

			final String [] columnsName = {"Activity", "Exception"};

			DefaultTableModel model = new DefaultTableModel(columnsName,0) {

				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row,int column) {
					return false;
				}

			};
			model.addRow(new Object[] {activity,exceptionClass});
			table.setModel(model);
			table.setPreferredScrollableViewportSize(new Dimension(400, 400));
			table.setFillsViewportHeight(true);
			table.setRowSelectionAllowed(true);
			table.setColumnSelectionAllowed(false);

		}
		scrollPane.setViewportView(table);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(49, 98, 397, 45);
		contentPanel.add(scrollPane_1);

		JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		textArea.setText(exMessage);
		textArea.setFont(new Font("Arial", Font.BOLD, 12));


	}


}

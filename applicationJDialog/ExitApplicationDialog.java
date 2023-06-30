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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import listener.*;
import mongoDB.*;

public class ExitApplicationDialog extends JDialog{

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	
	public ExitApplicationDialog(JFrame frame, MongoDBClient client) {
		
		super(frame,ModalityType.APPLICATION_MODAL);
		Font f = new Font("Arial", Font.PLAIN, 12);
		this.setTitle("Confirm exit");
		setBounds(470, 300,523, 109);
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
		setIcon(lblNewLabel, "mark2.png");
		
		JLabel lblNewLabel_1 = new JLabel("Do you really want to exit?");
		lblNewLabel_1.setBounds(72, 6, 368, 20);
		contentPanel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(f);

		JButton btnOK = new JButton("OK");
		btnOK.setFont(f);
		btnOK.setBackground(new Color(220,220,220));
		btnOK.setBounds(283,37, 100, 23);
		contentPanel.add(btnOK);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(f);
		btnCancel.setBackground(new Color(220,220,220));
		btnCancel.setBounds(397,37, 100, 23);
		contentPanel.add(btnCancel);
		
		
		btnCancel.addActionListener(new CloseDialogListener(this));
		btnOK.addActionListener(new ExitApplicationListener(client));
	}
	
	public void setIcon(JLabel component, String imageString) {
		
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
package applicationJDialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import listener.CloseDialogListener;
import mainApp.Frame;

public class SelectConnection extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public SelectConnection(Frame frame) {
		
		super(frame,ModalityType.APPLICATION_MODAL);
		setTitle("Select a connection!");
		setBounds(470, 300,441, 112);
		
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("error3.png");
			Image image = ImageIO.read(input);
			this.setIconImage(image);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JTextArea txtrSelectAConnection = new JTextArea();
		txtrSelectAConnection.setDisabledTextColor(SystemColor.desktop);
		txtrSelectAConnection.setBackground(SystemColor.control);
		txtrSelectAConnection.setText("Select a connection to a MongoDB instance from Connection Manager");
		txtrSelectAConnection.setBounds(10, 11, 411, 23);
		txtrSelectAConnection.setEnabled(false);
		txtrSelectAConnection.setEditable(false);
		txtrSelectAConnection.setFont(new Font("Arial", Font.PLAIN, 13));
		contentPane.add(txtrSelectAConnection);
		
		JButton btnOK = new JButton("OK");
		btnOK.setFont(new Font("Arial", Font.PLAIN,12));
		btnOK.setBackground(new Color(220,220,220));
		btnOK.setBounds(180,41, 84, 23);
		contentPane.add(btnOK);
		
		
		btnOK.addActionListener(new CloseDialogListener(this));
	}

}


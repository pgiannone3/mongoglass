package mainApp;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class MainDemo {

	public static void main(String[] args) {
		
		try{
			Frame f = new Frame();	
			f.setVisible(true);		
		}
		catch(Exception e) 
		{
			String error = "Cannot connect to MongoDB (127.0.0.1:27017). \nException: \"" + e.getClass().getName() + "\" opening the socket";
			JDialog dialog = new JOptionPane(error,JOptionPane.ERROR_MESSAGE,JOptionPane.DEFAULT_OPTION).createDialog("Connection refused"); 
			dialog.setAlwaysOnTop(true);
			dialog.setVisible(true);
			dialog.dispose();	
		}
	}
}
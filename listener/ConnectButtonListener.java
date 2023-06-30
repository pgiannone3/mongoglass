package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import mongoDB.*;
import upperPanel.*;
import applicationJDialog.*;
import configurationConnect.ConfigTable;
import configurationConnect.ErrorMessage;
import listener.ConfigTableListener;
import listener.ProfilingOptionsListener;
import mainApp.*;

public class ConnectButtonListener implements ActionListener {

	private ConfigurationJDialog dialog;
	private ConfigTableListener listener;
	private MongoDBClient mongoClient;
	private NestingSplitPane splitPane;
	private SplitPane nestedSplitPane;
	private Frame frame;
	private ArrayList<String> list;
	private JMenuItem profilingOptions;
	private ConfigTable configTable;


	public ConnectButtonListener(ConfigTable configTable, ConfigurationJDialog dialog, ConfigTableListener listener, 
			Frame frame, JMenuItem profilingOption) {

		this.dialog = dialog;
		this.listener = listener;
		this.frame = frame;		 	
		this.profilingOptions = profilingOption;
		this.configTable = configTable;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		this.frame.getContentPane().removeAll();
		this.frame.validate();
		this.frame.repaint();
		
		if(this.configTable.getSelectedRow() == -1) {

			SelectConnection dialog = new SelectConnection(frame);
			dialog.setVisible(true);
		}

		else {

			this.list = listener.getConfigInformation();
			String connectionName = list.get(0);
			String serverName = list.get(1);
			int portNumber = Integer.parseInt(list.get(2));

			try{

				this.dialog.dispose();

				this.mongoClient = new MongoDBClient(serverName, portNumber, connectionName);
				this.frame.setMongoclient(this.mongoClient);

				ArrayList<Database> dbs = new ArrayList<>();


				for(String dbName:this.mongoClient.getDatabasesName()) {
					dbs.add(new Database(this.mongoClient, dbName));
				}

				ListMultimap<Database, Collection> multimap = ArrayListMultimap.create();

				for(Database db:dbs) {
					ArrayList<String> collsName = db.getCollectionsName();
					for(String s:collsName) {
						Collection coll = new Collection(db, s);
						if(s.indexOf("temp123456789") != -1) {
							coll.getColl().drop();
						}
						else {
							multimap.put(db,new Collection(db, s));
						}
					}
				}

				nestedSplitPane = new SplitPane(this.mongoClient,multimap,connectionName,frame);


				splitPane = new NestingSplitPane(nestedSplitPane);

				splitPane.revalidate();
				splitPane.setVisible(true);

				nestedSplitPane.revalidate();
				nestedSplitPane.setVisible(true);

				this.frame.getContentPane().add(splitPane);
				this.frame.revalidate();

				this.profilingOptions.setEnabled(true);

				this.profilingOptions.addActionListener(new ProfilingOptionsListener(
						this.frame,connectionName,serverName,portNumber));
			}

			catch(Exception ex) {

				String title = "Connection failed!";
				String activity = "Connection to MongoDB";
				String exeptionName = ex.getClass().toString().replace("class", "").trim();
				String exeptionMessage = ex.getMessage();

				ErrorMessage error = new ErrorMessage(this.dialog, title, activity, exeptionName, exeptionMessage);
				error.setVisible(true);
			}
		}
	}
}
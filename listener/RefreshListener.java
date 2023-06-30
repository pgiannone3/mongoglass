package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import mainApp.Frame;
import mongoDB.Collection;
import mongoDB.Database;
import mongoDB.MongoDBClient;
import upperPanel.NestingSplitPane;
import upperPanel.SplitPane;


public class RefreshListener implements ActionListener {

	private Frame frame;
	private String connectionName;
	private String serverName;
	private int portNumber;
	private MongoDBClient mongoClient; 
	private NestingSplitPane splitPane;
	private SplitPane nestedSplitPane;


	public RefreshListener(Frame frame, String connectionName, String serverName, int portNumber) {

		this.frame= frame;
		this.connectionName = connectionName;
		this.serverName = serverName;
		this.portNumber = portNumber;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		this.frame.getContentPane().removeAll();
		this.frame.validate();
		this.frame.repaint();
		

		try{

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
		}
		catch(Exception ex) {

			JOptionPane.showInternalMessageDialog(frame, ex.getClass().toString().replace("class", "").trim(),
					"Connection failed!", JOptionPane.INFORMATION_MESSAGE);


		}


	}
}

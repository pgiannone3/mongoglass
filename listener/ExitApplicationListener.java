package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import mongoDB.*;


public class ExitApplicationListener implements ActionListener{

	private MongoDBClient client;

	public ExitApplicationListener(MongoDBClient client) {
		this.client = client;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(this.client == null) {
			System.exit(0);
		}

		else {
			try{
				ArrayList<String> dbs = this.client.getDatabasesName();
				for(String db : dbs) {

					Database d = new Database(client, db);
					ArrayList<String> colls = d.getCollectionsName();
					for(String coll : colls) {
						if(coll.indexOf("temp123456789") != -1) {
							d.getDb().getCollection(coll).drop();
						}
					}
				}
				client.getClient().close();
				System.exit(0);
			}
			catch(Exception ex) {
				System.exit(0);
			}
		}
	}
}


package mongoDB;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.mongodb.MongoClient;

public class MongoDBClient {

	private MongoClient client;
	private String serverName;
	private int portNumber;
	private String connectionName;

	public MongoDBClient(String hostname, int port, String connectionName) {

		this.connectionName = connectionName;
		this.serverName = hostname;
		this.portNumber= port;
		
		try {
			this.client = new MongoClient(hostname, port);
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}

	public MongoClient getClient() {
		return client;
	}

	public ArrayList<String> getDatabasesName(){

		ArrayList<String> dbsName = new ArrayList<>();

		List<String> dbs = this.client.getDatabaseNames();
		Iterator<String> iterator = dbs.iterator();
		
		while(iterator.hasNext()) {
			String dbName = iterator.next();
			dbsName.add(dbName);
		}
		

		return dbsName;
	}

	public String getServerName() {
		return serverName;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public String getConnectionName() {
		return connectionName;
	}
	
}
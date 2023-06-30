package mongoDB;
import java.util.ArrayList;

import com.mongodb.DB;

public class Database {

	private DB db;
	private String dbName;

	public DB getDb() {
		return db;
	}
	
	public String getDbName() {
		return dbName;
	}

	public Database(MongoDBClient client, String dbName) {
		super();
		this.dbName = dbName;
		this.db = client.getClient().getDB(dbName);
	}

	public ArrayList<String> getCollectionsName() {

		ArrayList<String> collsName = new ArrayList<>();
		for(String s: this.db.getCollectionNames()) {
			collsName.add(s);
		}	
		
		return collsName;
	}
}


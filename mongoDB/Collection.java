package mongoDB;
import com.mongodb.DBCollection;

public class Collection {

	private String collName;
	private DBCollection coll;

	public Collection(Database db, String collName) {
		
		this.coll = db.getDb().getCollection(collName);
		this.collName = collName;	
	}
	
	public String getCollName() {
		return collName;
	}
	
	public DBCollection getColl() {
		return coll;
	}

	
}

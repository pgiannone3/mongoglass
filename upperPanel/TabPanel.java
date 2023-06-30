package upperPanel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import mongoDB.*;

public class TabPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String db;
	private String coll;
	private int limit;
	private int depth;
	
	public TabPanel(String db, String coll, MongoDBClient client, int limit, int depth) {

		this.limit = limit;
		this.depth = depth;
		this.db = db;
		this.coll = coll;
		String title = "<" + this.db + "," + this.coll + ">- lim/dep: " + limit + "/" + depth;
		this.setName(title);
		this.setLayout(new BorderLayout());
		this.invalidate();
		this.validate();
		this.repaint();
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coll == null) ? 0 : coll.hashCode());
		result = prime * result + ((db == null) ? 0 : db.hashCode());
		result = prime * result + depth;
		result = prime * result + limit;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabPanel other = (TabPanel) obj;
		if (coll == null) {
			if (other.coll != null)
				return false;
		} else if (!coll.equals(other.coll))
			return false;
		if (db == null) {
			if (other.db != null)
				return false;
		} else if (!db.equals(other.db))
			return false;
		if (depth != other.depth)
			return false;
		if (limit != other.limit)
			return false;
		return true;
	}

	public String getDb() {
		return db;
	}

	public String getColl() {
		return coll;
	}

}
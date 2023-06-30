package bottomPanel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import mongoDB.*;

public class TabbedBottomPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private Collection coll;
	private Database db;
	private String field;
	private int limit;
	private int depth;
	
	public TabbedBottomPane(Database db, Collection coll, String field, int limit, int depth ) {
		
		this.setLayout(new BorderLayout());
		this.coll = coll;
		this.db = db;
		this.field = field;
		this.setName("<" + this.db.getDbName() +","+ this.coll.getCollName() + ">" + field + " - lim/dep: " + limit + "/" + depth);
		this.depth=depth;
		this.limit=limit;
	}

	public Collection getColl() {
		return coll;
	}

	public String getField() {
		return field;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coll == null) ? 0 : coll.hashCode());
		result = prime * result + ((db == null) ? 0 : db.hashCode());
		result = prime * result + depth;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
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
		TabbedBottomPane other = (TabbedBottomPane) obj;
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
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (limit != other.limit)
			return false;
		return true;
	}
	
	
	
}

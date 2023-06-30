package upperPanel;

public class TypeOcc {

	private String _id;
	private String occurrences;
	
	public TypeOcc(String _id, String occurrences) {
	
		this._id = _id;
		occurrences = occurrences.substring(0,occurrences.indexOf("."));
		this.occurrences = occurrences;
		
	}

	public String get_id() {
		return _id;
	}

	public String getOccurrences() {
		return occurrences;
	}
	
}

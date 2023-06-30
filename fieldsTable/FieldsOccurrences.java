package fieldsTable;

public class FieldsOccurrences {

	private String fieldName;
	private int occurrences;
	private String type;
	
		
	public FieldsOccurrences(String fieldName, int occurrences, String type) {
		super();
		this.fieldName = fieldName;
		this.occurrences = occurrences;
		this.type = type;
		
	}
	
	public String getFieldName() {
		return fieldName;
	}

	

	public String getType() {
		return type;
	}

	public int getOccurrences() {
		return occurrences;
	}
	
	
}

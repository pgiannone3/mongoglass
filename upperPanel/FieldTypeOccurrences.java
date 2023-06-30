package upperPanel;

public class FieldTypeOccurrences {

	private String field;
	private String types;
	private int occurrences;
	private String occurrencesRate;
	
	
	public FieldTypeOccurrences(String field, String types, int occurrences, String occurrencesRate) {
		super();
		this.field = field;
		this.types = types;
		this.occurrences = occurrences;
		this.occurrencesRate = occurrencesRate;
		
	}
	
	public String getField() {
		return field;
	}


	public String getTypes() {
		return types;
	}


	public int getOccurrences() {
		return occurrences;
	}


	public String getOccurrencesRate() {
		return occurrencesRate;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

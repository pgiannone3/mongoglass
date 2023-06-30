package upperPanel;

public class KeyMap implements Comparable<KeyMap> {

	private String field;
	private String type;
	
	public KeyMap(String field, String type) {
		
		this.field = field;
		this.type = type;
	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		KeyMap other = (KeyMap) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	public String getField() {
		return field;
	}

	public String getType() {
		return type;
	}

	@Override
	public int compareTo(KeyMap o) {
		
		final int EQUAL = 0;
		
		if (this == o) return EQUAL;
		
		if(this.field.equals(o.getField())) {
			
			return this.type.compareTo(o.getType());
			
		}
		
		if(!this.field.equals(o.getField())) {
			
			return this.field.compareTo(o.getField());
			
		}
		
		return EQUAL;
		
		
		
				
	}	
	
	
}

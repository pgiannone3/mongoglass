package configurationConnect;

public class Option {

	private int limit;
	private int depth;
	
	
	public Option(int limit, int depth) {
		
		this.limit=limit;
		this.depth = depth;
	}

	public int getLimit() {
		return limit;
	}


	public int getDepth() {
		return depth;
	}

	
}

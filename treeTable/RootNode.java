package treeTable;

public class RootNode extends Node {

	private String key;
	
	public RootNode(String key) {
		super(new Object[] {key});
		this.key = key;
	}
	
	public String toString() {
		return key;
		
	
	
	}
	
	
	
}

package upperPanel;

import java.util.ArrayList;

public class TabbedList extends ArrayList<TabPanel> {

	private static final long serialVersionUID = 1L;
	
	public boolean addElement(TabPanel element) {
		
		if(existsYet(element)) {return false;}
		else {
			this.add(element);
			return true;
			}
	}
	
	public TabPanel getElement(TabPanel element) {

		for(int i = 0; i<this.size(); i++) {
			TabPanel tab = this.get(i);
			if(element.equals(tab)) 
				return tab;

		}

		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return super.remove(o);
		
	}

	public boolean existsYet(TabPanel element) {
		boolean exists=false;
		for(int i = 0; i<this.size(); i++) {
			if(this.get(i).equals(element)) {
				exists=true;
				break;
			}
		}
		return exists;

}
}
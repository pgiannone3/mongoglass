package treeTable;

import java.util.ArrayList;

import bottomPanel.TabbedBottomPane;


public class TabbedBottomList extends ArrayList<TabbedBottomPane>{

	private static final long serialVersionUID = 1L;

	public boolean addElement(TabbedBottomPane element) {

		if(existsYet(element)) {return false;}
		else {
			this.add(element);
			return true;
		}
	}

	public TabbedBottomPane getElement(TabbedBottomPane element) {

		for(int i = 0; i<this.size(); i++) {
			TabbedBottomPane tab = this.get(i);
			if(element.equals(tab)) 
				return tab;

		}
		return null;
	}

	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return super.remove(o);

	}
	public boolean existsYet(TabbedBottomPane element) {
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

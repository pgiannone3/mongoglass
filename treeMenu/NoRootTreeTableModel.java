package treeMenu;
import java.util.ArrayList;
import upperPanel.*;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

public class NoRootTreeTableModel extends AbstractTreeTableModel {


	private final static String[] COLUMN_NAMES = {"Field","Type","Number of occurrences","Rate of occurrences"};

	private ArrayList<FieldTypeOccurrences> fieldTypeOcc;

	public NoRootTreeTableModel(ArrayList<FieldTypeOccurrences> fieldTypeOcc) {
		super(new Object());
		this.fieldTypeOcc = fieldTypeOcc;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		return false;
	}

	@Override
	public Object getValueAt(Object node, int column) {

		for(FieldTypeOccurrences fto : fieldTypeOcc) {

			switch (column) {
			case 0: 
				return fto.getField();
			case 1:
				return fto.getTypes();
			case 2:	
				return fto.getOccurrences();
			case 3:	
				return fto.getOccurrencesRate();
			default:
				return null;
			}
		}
		return null;
	}

@Override
public Object getChild(Object arg0, int arg1) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int getChildCount(Object arg0) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int getIndexOfChild(Object arg0, Object arg1) {
	// TODO Auto-generated method stub
	return 0;
}

}

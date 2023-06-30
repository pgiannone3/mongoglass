package bottomPanel;
import javax.swing.JSplitPane;

public class SplitBottomPane extends JSplitPane{

	private static final long serialVersionUID = 1L;
	private static final int SPLIT_WEIGHT  = 940;
	private RightBottomPanel rightBottomPanel;
	private LeftBottomPanel leftBottomPanel;

	
	public RightBottomPanel getRightBottomPanel() {
		return rightBottomPanel;
	}
	public LeftBottomPanel getLeftBottomPanel() {
		return leftBottomPanel;
	}
	
	public SplitBottomPane(RightBottomPanel rightBottomPanel, LeftBottomPanel leftBottomPanel) {
		this.rightBottomPanel = rightBottomPanel;
		this.leftBottomPanel = leftBottomPanel;		
		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		this.setLeftComponent(leftBottomPanel);
		this.setRightComponent(rightBottomPanel);
		this.setDividerLocation(SPLIT_WEIGHT);		
	}
}

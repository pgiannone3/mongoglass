package upperPanel;

import javax.swing.JSplitPane;


public class NestingSplitPane extends JSplitPane{

	private static final long serialVersionUID = 1L;
	private SplitPane nestedSplitPane;
	private static final int SPLIT_WEIGHT  = 400;

	public NestingSplitPane(SplitPane splitPane) {

		this.nestedSplitPane = splitPane;
		this.setVisible(true);
		this.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.setTopComponent(this.nestedSplitPane);
		this.setDividerLocation(SPLIT_WEIGHT);
		this.setBottomComponent(this.nestedSplitPane.getRightPanel().getBottomPanel());
		
	}
}

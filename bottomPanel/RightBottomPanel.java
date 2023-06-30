package bottomPanel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import fieldsTable.Table;
import listener.*;

public class RightBottomPanel extends JScrollPane{

	private static final long serialVersionUID = 1L;
	private Table table;
	private LeftBottomPanel leftBottomPanel;

	public LeftBottomPanel getLeftBottomPanel() {
		return leftBottomPanel;
	}

	public RightBottomPanel(Table table, LeftBottomPanel leftBottomPanel) {
		this.table = table;
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(this.table.getModel());
		table.setRowSorter(rowSorter);

		JTextField search = new JTextField();

		search.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {

				String text = search.getText();

				if(text.trim().length()==0) {
					rowSorter.setRowFilter(null);
				}

				else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+text,0));

				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {

				String text = search.getText();
				if(text.trim().length()==0) {
					rowSorter.setRowFilter(null);
				}
				else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+text,0));

				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				String text = search.getText();
				if(text.trim().length()==0) {
					rowSorter.setRowFilter(null);
				}
				else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+text,0));

				}
			}
		});

		JPanel searchPanel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Search:");
		label.setFont(new Font("Arial", Font.PLAIN, 12));

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("search.png");
			Image image = ImageIO.read(input);
			label.setIcon(new ImageIcon(image));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JPanel tablePanel =new JPanel(new BorderLayout());
		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
		tablePanel.add(table);


		searchPanel.add(label,BorderLayout.NORTH);
		searchPanel.add(search,BorderLayout.CENTER);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(searchPanel,BorderLayout.NORTH);
		panel.add(tablePanel, BorderLayout.CENTER);
		this.setViewportView(panel);


		FieldsOccurrenceTableListener listener = new FieldsOccurrenceTableListener(this.table,leftBottomPanel);
		this.table.getSelectionModel().addListSelectionListener(listener);		
	}
}

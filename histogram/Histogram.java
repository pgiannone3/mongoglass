package histogram;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import fieldsTable.FieldsOccurrences;
import fieldsTable.Table;

public class Histogram{

	private ArrayList<FieldsOccurrences> fieldsOccs;
	private JPanel panel;
	private String s;
	private Table table;
	//number of bars per frame
	private static final int VISIBLE =15;

	public JPanel getPanel() {
		return panel;
	}

	public Histogram(ArrayList<FieldsOccurrences> fieldsOccs, String s,Table table ) {

		this.fieldsOccs = fieldsOccs;
		this.s = s;
		this.table=table;
		
		display();
		
	
	}

	private void display() {

		
		this.panel = new JPanel();
		ArrayList<DefaultCategoryDataset> list = new ArrayList<>();

		int index = 0;
		int start = 0;
		int end = 0;

		//definiamo una variabile end che stabilisce il numero massimo di frame:
		//infatti se la lunghezza della mappa è 8000 significa che avremo 8000/100 pannelli da scorrere

		if(this.fieldsOccs.size() % 15 == 0) {
			end = this.fieldsOccs.size() / 15;
		} 
		else
			end = (this.fieldsOccs.size() / 15) + 1; 

		//creiamo un ArrayList di Dataset ciascuno dei quali di 100 colonnine. Quindi creiamo 
		// un numero pari ad "end" di dataset ciascuno dei quali contiene 100 colonne
		//l'arrayList si chiama list

		while(index<end) {

			list.add(createDataset(start));
			start = start + 15;
			index++;
		}

		JFreeChart chart = createChart(list.get(0));
		CategoryPlot plot = (CategoryPlot) chart.getPlot();


		LegendTitle legend = (LegendTitle) chart.getSubtitle(0);
		legend.setPosition(RectangleEdge.RIGHT);
		
		//format the plot area:
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setOutlinePaint(Color.BLACK);

		//format the bar colour
		BarRenderer barRenderer = (BarRenderer)plot.getRenderer();
		barRenderer.setAutoPopulateSeriesFillPaint(true);
		BarRenderer.setDefaultShadowsVisible(true);
		

		//format the tile of the graph
		chart.setTitle(new TextTitle("Number of occurrences of: \"" + this.s + "\""));

		//format the tooltip label
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
				"\"value\":\"{1}\",Occurrences: {2}", NumberFormat.getInstance()));
		
		renderer.setAutoPopulateSeriesFillPaint(true);

		//format the x axis
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setVisible(true);
		domainAxis.configure();
		Font font = new Font("Arial", Font.PLAIN, 5);
		domainAxis.setLabelFont(font);
		plot.getRangeAxis().setLabelFont(font);        
		
		
		//format the y axis
		ValueAxis yAxis = plot.getRangeAxis();
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		ChartPanel chartPanel = new ChartPanel(chart){

			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(900, 370);
			}
		};

		this.panel.setLayout(new GridLayout(1, 0,0,0));
		this.panel.add(chartPanel);
		

		chartPanel.addChartMouseListener(new ChartMouseListener() {

			@Override
			public void chartMouseMoved(ChartMouseEvent arg0) {
			}

			@Override
			public void chartMouseClicked(ChartMouseEvent arg0) {

				try{
				TableModel model = table.getModel();	
				CategoryItemEntity entity = (CategoryItemEntity) arg0.getEntity();
				Comparable<?> row = entity.getRowKey();
				Comparable<?> col = entity.getColumnKey();
				String field = String.valueOf(col);
				String type = String.valueOf(row);
			
				
					for(int i = 0; i<model.getRowCount(); i++) {
						String f = (String)model.getValueAt(i, 0);
						String tp = (String)model.getValueAt(i, 1);
						if(f.equals(field) && tp.equals(type)) {
							
							int realRowNumber = table.convertRowIndexToView(i);
							table.changeSelection(realRowNumber, 0, false, false);
							break;

						}
					}
				}
				catch (Exception e) {
					System.out.println("No bar selected");
				}
			}
		});

		//creo lo slider

		final JSlider slider = new JSlider(0,end,0);
		slider.setSize(new Dimension(50, 50));
		// lo setto

		slider.setMinimum(0);
		slider.setMaximum(end-1);
		slider.setMinorTickSpacing(15);
		slider.setMajorTickSpacing(end-1);

		slider.setPaintTicks(false);

		slider.setLabelTable(slider.createStandardLabels(15));

		slider.setPaintLabels(false);
		slider.setOrientation(SwingConstants.VERTICAL);
		Font f = new Font("Arial", Font.PLAIN, 10);

		slider.setFont(f);
		slider.revalidate();
		

		//creo il listener sullo slider per fargli cambiare il pannello quando vado avanti

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				plot.setDataset(list.get(slider.getValue()));
			}

		});

		
		//creo un pannello Box p che contiene al suo interno lo slider in basso 

		Box p = new Box(BoxLayout.X_AXIS);
		p.setSize(300, 300);
		p.add(slider);
		this.panel.add(p);
		this.panel.setVisible(true);
	}
	

	private DefaultCategoryDataset createDataset(int start) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int end = start + VISIBLE;

		for(int i = 0; i<this.fieldsOccs.size() && i<end; i++) {
			if(i<start) {
				//do nothing
			}
			if(i >= start) {
				String name = this.fieldsOccs.get(i).getFieldName();
				String type = this.fieldsOccs.get(i).getType();
				int occurrences  = this.fieldsOccs.get(i).getOccurrences();

				dataset.setValue(occurrences,type, name);
				

			}	
		}
		return dataset;
	}

	private JFreeChart createChart(DefaultCategoryDataset dataset) {

		JFreeChart chart = ChartFactory.createStackedBarChart(
				"",  											//\
				"", 											//X-axis title
				"",						//Y-axis title	
				dataset, 										//dataset
				PlotOrientation.HORIZONTAL, 					//plot orientation
				true, 											//show legends		
				true, 											//use tooltips
				false											//generate URLs
				);

		return chart;

	}
}

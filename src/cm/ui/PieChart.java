package cm.ui;

 

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart implements Runnable {
	PiePlot pieplot;
	ChartPanel frame1;
	static Map<String,Vector<String>> map=new HashMap<String,Vector<String>>();
	Color[] colors=new Color[]{new Color(255,69,0),new Color(65,105,225),
	new Color(0,255,127),new Color(124,252,0),new Color(0,191,255),new Color(165,42,42),
	new Color(255,215,0),Color.DARK_GRAY};
	
	public void run(){
		DefaultPieDataset data = getDataSet();
	      JFreeChart chart = ChartFactory.createPieChart("题型比例",data,true,false,false);
	      //得到饼图的Plot对象 
	      PiePlot pieplot = (PiePlot) chart.getPlot();
	      //设置扇区标签颜色  
	      pieplot.setLabelBackgroundPaint(new Color(220, 220, 220));  
	      pieplot.setLabelFont((new Font("宋体", Font.PLAIN, 12)));
	      //设置扇区边框不可见  
	      pieplot.setSectionOutlinesVisible(false);
	      
	      Iterator<String> iter = map.keySet().iterator();
	      	int i=0;
	        String key;
	        while(iter.hasNext()){
	        	key=iter.next();
	        	if(i<colors.length){
	            pieplot.setSectionPaint(key, colors[i]);
	            i++;
	        	}
	        }
	      
	      DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题
	      NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象
	      StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//获得StandardPieSectionLabelGenerator对象
	      pieplot.setLabelGenerator(sp1);//设置饼图显示百分比
	  
	      //没有数据的时候显示的内容
	      pieplot.setNoDataMessage("无数据显示");
	      pieplot.setCircular(false);
	      pieplot.setLabelGap(0.02D);
	  
	      pieplot.setIgnoreNullValues(true);//设置不显示空值
	      pieplot.setIgnoreZeroValues(true);//设置不显示负值
	      frame1=new ChartPanel (chart,true);
	      chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
	      PiePlot piePlot= (PiePlot) chart.getPlot();//获取图表区域对象
	      piePlot.setLabelFont(new Font("宋体",Font.BOLD,10));//解决乱码
	      chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,10));
	}
	
	public PieChart(Map<String, Vector<String>> map){
		  this.map=map;
	}
	
    private static DefaultPieDataset getDataSet() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Iterator<String> iter = map.keySet().iterator();
        String key;
        //System.out.println(map.size());
        while(iter.hasNext()){
            key=iter.next();
            System.out.println(key);
        	dataset.setValue(key,map.get(key).size());
        }
        return dataset;
}
    public ChartPanel getChartPanel(){
    	return frame1;
    	
    }
}

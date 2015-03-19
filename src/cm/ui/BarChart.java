package cm.ui;

import java.awt.Color;
import java.awt.Font;  
  




import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.axis.CategoryAxis;  
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;  
import org.jfree.chart.plot.CategoryPlot;  
import org.jfree.chart.plot.PlotOrientation;  
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;  
import org.jfree.data.category.DefaultCategoryDataset;  
  
public class BarChart implements Runnable {  
    ChartPanel frame1;  
    static int startMonth;
    static int endMonth;
    static int[] monthpronum;
    
    public  BarChart(int[] monthpronum,int startMonth,int endMonth ){  
     this.startMonth=startMonth;
     this.endMonth=endMonth;
     this.monthpronum=monthpronum;
     System.out.println("startMonth的值:"+startMonth);
     System.out.println("endMonth的值:"+endMonth);
    }  
   
       private static CategoryDataset getDataSet() {  
           DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
           for(int i=startMonth;i<=endMonth;i++){
        	   dataset.addValue(monthpronum[i-1], "题量", i+"月"); 
           }
         
           return dataset;  
}  
       
public ChartPanel getChartPanel(){  
    return frame1;  
}

@Override
public void run() {
	// TODO Auto-generated method stub
	CategoryDataset dataset = getDataSet();  
    JFreeChart chart = ChartFactory.createBarChart(  
                         "按月统计", // 图表标题  
                        "月份", // 目录轴的显示标签  
                        "题数", // 数值轴的显示标签  
                        dataset, // 数据集  
                        PlotOrientation.VERTICAL, // 图表方向：水平、垂直  
                        true,           // 是否显示图例(对于简单的柱状图必须是false)  
                        false,          // 是否生成工具  
                        false           // 是否生成URL链接  
                        );  
      
    
    CategoryPlot plot=chart.getCategoryPlot();//获取图表区域对象  
    
    BarRenderer barrenderer = new BarRenderer();
    barrenderer.setMaximumBarWidth(0.1);
    barrenderer.setMinimumBarLength(0.1);
    plot.setRenderer(barrenderer);
    plot.getRenderer().setSeriesPaint(0, Color.blue);//设置柱体颜色
    
    CategoryAxis domainAxis=plot.getDomainAxis(); //水平底部列表  
     domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));         //水平底部标题  
     domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));  //垂直标题  
     ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状  
     rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));  
     chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));  
     chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体  
        
     frame1=new ChartPanel(chart,true);        //这里也可以用chartFrame,可以直接生成一个独立的Frame 
}  

}  
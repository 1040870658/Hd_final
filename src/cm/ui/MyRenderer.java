package cm.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

//自定义表格的渲染类,为在表格里添加按钮做准备
public class MyRenderer implements TableCellRenderer 
{
	
    private JPanel panel;
    private JButton button;
    
   
    public MyRenderer() 
    {
        initButton();
        initPanel();
        panel.add(button, BorderLayout.CENTER);
    }

    private void initButton() 
    {
        button = new JButton();
    }

    private void initPanel() 
    {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) 
    {
        button.setText("加入对比");
        return panel;
    }

}
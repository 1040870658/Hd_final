package cm.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import cm.ui.ClientInterface.cmpButtonListener;

//自定义表格单元的编辑器,使其能够在表格里加入按钮
public class MyEditor extends AbstractCellEditor implements
        TableCellEditor 
        {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6546334664166791132L;
    private JPanel panel;
    private JButton button;
    JTable table;
    cmpButtonListener listener;
    
    public MyEditor(cmpButtonListener listener,JTable table)
    {
    	this.table=table;
    	this.listener=listener;
        initButton();
        initPanel();
        panel.add(this.button, BorderLayout.CENTER);
    }

    private void initButton() 
    {
    	button = new JButton("加入对比");
        button.addActionListener(listener);
    }

    private void initPanel() 
    {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) 
    {
        button.setText("加入对比");
        return panel;
    }

    @Override
    public Object getCellEditorValue() 
    {
        return "加入对比";
    }

}
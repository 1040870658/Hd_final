/*编辑窗口
  作者：叶晨
 */
package cm.ui;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;

import cm.*;
import cm.service.Classify;
public class EditGraph extends JInternalFrame{
	private int index = 0;
	private JButton edit;
	private JButton lead_in;
	private JComboBox file;
	private JTextArea show;
	private Container container;
	private JScrollPane scroll;
	private String[] filename = new String[1005];
	public void initString(){
		MainFrame.sortVec = Classify.getClassifyList();
		System.out.println(MainFrame.sortVec.size());
		for(int i = 0;i != MainFrame.sortVec.size();i ++){
			filename[i] = new String(MainFrame.sortVec.elementAt(i));
			System.out.println(filename[i]);
		}
		index = MainFrame.sortVec.size();
	}
	public EditGraph(){
		super("编辑窗口",true,true,true,true);
		container = getContentPane();
		container.setLayout(new FlowLayout());
		
		initString();
		
		ButtonHandler handler = new ButtonHandler();
		comboHandler ihandler = new comboHandler();
		Box hbox = Box.createHorizontalBox();
		Box vbox = Box.createVerticalBox();
		
		file = new JComboBox(filename);
		file.setMaximumRowCount(3);
		file.addItemListener(ihandler);
		
		edit = new JButton("编辑");
		lead_in = new JButton("导入");
		show = new JTextArea(25,35);
		
		show.setEditable(false);
		show.setText(showText());
		show.setLineWrap(true);
		
		edit.addActionListener(handler);
		lead_in.addActionListener(handler);
		
		hbox.add(Box.createHorizontalStrut(100));
		
		hbox.add(file);
		hbox.add(edit);
		hbox.add(lead_in);
		
		vbox.add(hbox);
		vbox.add(new JScrollPane(show));
		hbox.add(Box.createHorizontalStrut(100));
		container.add(vbox);
		
		setLocation(200,60);
		setSize(500,500);
		setVisible(false);
		 try {
	            this.setSelected(true);
	        } catch (java.beans.PropertyVetoException ex) {
	          System.out.println("Exception while selecting");
	       }
	}
	
	public void updateCombo(){
		String tmp = MainFrame.sortVec.elementAt(MainFrame.sortVec.size()-1);
		file.insertItemAt(tmp,index-1);
	}
	//按钮响应类
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == lead_in){
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        ".txt", "txt");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(edit);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					Classify.importClassify(chooser.getSelectedFile().getAbsolutePath());
					JOptionPane.showMessageDialog(null, "导入成功");
					initString();
					String tmp = MainFrame.sortVec.elementAt(MainFrame.sortVec.size()-1);
					file.insertItemAt(tmp,index-1);
				}

			}
			if(e.getSource() == edit){
				file.setEnabled(true);
				if(show.isEditable()){
					if(edit.getText().equals("编辑完毕")){
						Classify.resetClassify(filename[file.getSelectedIndex()],show.getText());
					}
					edit.setText("编辑");
					show.setEditable(false);
				}
				else{
					edit.setText("编辑完毕");
					file.setEnabled(false);
					show.setText(Classify.getFileStrForEdit(filename[file.getSelectedIndex()]));
					show.setEditable(true);
				}
			}
		}
	}
	
	//组合框响应类
	private class comboHandler implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				show.setText(showText());
			}
		}
	}
	
	private String showText(){
		String data = new String();
		Map<String,Vector<String>> name =  Classify.getClassifyMap(filename[file.getSelectedIndex()]);
		Iterator<String> it = name.keySet().iterator();
		while(it.hasNext()){
			String key;
			Vector<String> vec;
			key = it.next().toString();
			data += key;
			data += "\n";
			
			vec = name.get(key);
			
			for(int i = 0;i != vec.size();i ++){
				data += vec.elementAt(i);
				data += " ";
			}
			data += "\n\n";
		}
		return data;
	}

}

/*	主框架
  	作者:叶晨
  	2014/5/27	
 */
package cm.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;

import cm.service.Classify;

import java.util.*;

public class MainFrame extends JFrame {
	public static int sortNum;
	public static Vector<String> sortVec = new Vector<String>();// 分类列表
	private EditGraph editgraph;
	private JMenu menu;
	private JMenuBar bar;
	private JMenuItem createNew;
	private JMenuItem edit;
	private JMenuItem setIn;
	private Container container;

	static JDesktopPane desktopPane = new JDesktopPane();

	private final String aboutString = new String("软件名: 谁与争锋\n制作者：\n陈锐\n陈仕兴\n邓作恒\n林长荣\n梁家来\n 叶晨");
	private JMenu help = new JMenu("帮助");
	private JMenuItem about = new JMenuItem("关于");
	


	public MainFrame() {
		/*
		 * super("谁与争锋"); container = getContentPane();
		 */
		super("谁与争锋");
		Container container = this.getContentPane();

		desktopPane = new JDesktopPane();
		container.add(desktopPane,BorderLayout.CENTER);

		setSize(350, 350);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});


		sortVec = new Vector<String>(Classify.getClassifyList());
		editgraph = new EditGraph();
		menu = new JMenu("系统功能 ");
		bar = new JMenuBar();
		createNew = new JMenuItem("新建用户");
		edit = new JMenuItem("编辑分类");
		setIn = new JMenuItem("导入分类");

		menu.add(createNew);
		menu.add(edit);
		menu.add(setIn);
		help.add(about);
		bar.add(menu);
		bar.add(help);

		ItemHandler handler = new ItemHandler();
		createNew.addActionListener(handler);
		edit.addActionListener(handler);
		setIn.addActionListener(handler);
		about.addActionListener(handler);
		container.add(bar,BorderLayout.NORTH);

		setSize(1000, 600);
		setLocation(260, 80);
		setVisible(true);
		desktopPane.add(new UserGraph());
		desktopPane.add(editgraph);
		editgraph.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	private class ItemHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == createNew) {
				UserGraph user = new UserGraph();
				desktopPane.add(user);
				user.setVisible(false);
				user.setVisible(true);
			}
			if (event.getSource() == edit) {
				if (editgraph.isVisible() == false) {
					editgraph.setVisible(true);
				}
			}
			if (event.getSource() == setIn) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        ".txt", "txt");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(edit);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					Classify.importClassify(chooser.getSelectedFile().getAbsolutePath());
					JOptionPane.showMessageDialog(null, "导入成功");
					editgraph.initString();
					editgraph.updateCombo();
				}
			}
			if(event.getSource() == about){
				JOptionPane.showMessageDialog(null, aboutString);
			}
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		// 设置按钮外观
		SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());

		MainFrame mainFrame = new MainFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

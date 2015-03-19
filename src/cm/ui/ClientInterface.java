package cm.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.jws.Oneway;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;








import org.apache.commons.httpclient.HttpException;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.*;
import org.jvnet.substance.skin.*;

import cm.service.DefSetting;
import cm.service.Function;
import cm.service.FunctionSet;

/*
 * 2014年6月6日下午更新
 * By陈锐
 */

public class ClientInterface extends JPanel{
	
		public UserGraph ug;
		//标识不同页面的标记
		public static int flag=0;
		public static int flag2=0;
		public int DISABLE=500;
		public int ENABLE=600;
		public int lastButtonStatus=DISABLE;
		public int nextButtonStatus=ENABLE;
		int SPEED=20;
		int cmpPageTime=0;
		//标识页面状态的标记
		int statusFlag=0;
		//标识两种页面状态,一种是保持当前页面,另外一种是改变当前页面.
		int STATUS_HOLD=100,STATUS_CHANGE=200;
		//记录加入对比的数目
		int cmp_count=0;
		//记录加入对比的ID的最大值
		int ID_largest=0;
		//定义默认排名从1到5
		public int from=1,to=5;
		//默认从注册到现在的第几个月份
		public int month=1;
		public int startMonth=1;
		public int endMonth=12;
		public int left=0;
		public int pageCount=1;
		public int pageNum=1;
		//定义用户已加入对比的数目(包括用户本人)
		int personNumber=1;
		int itemIndex;
		static int HONERNUM=15;
		boolean zeroPro=true;
		
		//一个用于储存表格的行索引值的映射表
		Map<Integer,Integer> IDs=new HashMap<Integer,Integer>();
		int monthpronum[]=new int[120];
		boolean honer[]=new boolean[HONERNUM];
		final String honerName[]=new String[]{"战5渣","小试牛刀","初出茅庐","放弃吧少年","二百五",
		"开始有点变态","脑子有病","呵呵","放下屠刀","有种刷到两点","有种刷到三点","无名小卒","正常水平",
		"这肯定有小号","超级赛亚人"};
		final String condition[]=new String[]{"条件:  AC5题","条件:  AC50题","条件:  AC100题",
		"条件:  AC200题","条件:  AC250题","条件:  AC400题","条件:  AC800题","条件:  AC1024题",
		"条件:  WA15次","条件:  凌晨1点在刷题","条件:  凌晨两点还在刷题","条件:  30%AC率",
		"条件:  60%AC率","条件:  90%AC率","条件:  排名前25"};
		
		 Vector<Vector<String>> NBers=new Vector<Vector<String>>();
		 Vector<Vector<String>> NBersCopy=new Vector<Vector<String>>();
		//初始化登录按钮的内容
		static String userName=null;
		String classifyName=null;
		//一个公用的GridBagLayout布局管理器
		GridBagLayout gbLayout;
		//GridBagLayout的约束对象GridBagConstraints
		GridBagConstraints constraints;
		
		//当前页面
		JPanel currentPanel;
		//"我的奋斗史","个人亮点","与牛人对比","按月统计","分类比例","题目推荐","加入对比"各种页面
		JPanel historyPanel,brightPanel,learnPanel,countPanel,sortPanel,recommendPanel;
		JPanel joinNowPanel;
		
		//暂时保存当前滚动栏的滚动栏
		JScrollPane tempScrollPanel=new JScrollPane();
		JScrollPane sc;
		
		//链接"确定"按钮和"立即更新","登录"按钮
		JButton OkButton,updateButton,lastPageButton,nextPageButton;
		
		//排名区间
		JTextField rankFrom,rankTo;
		
		UserInformation userInformation=null;
		Function c=null;
		
		BarChart bar;
		PieChart pie;
		
		Map<String,Vector<String>> map;
		
		private JMenuItem[] sortItem;
		private JPopupMenu popUpMenu = new JPopupMenu();
		private JMenuItem briefInfo = new JMenuItem("我的奋斗史");
		private JMenuItem achieve = new JMenuItem("我的亮点");
		private JMenuItem NB = new JMenuItem("与牛人对比");
		private JMenuItem count = new JMenuItem("按月统计");
		private JMenu sort = new JMenu("分类比例");
		private JMenuItem recommend = new JMenuItem("题目推荐");
		private JMenuItem submit=new JMenuItem("在线提交");
		
		
		public void setUserGraph(UserGraph ug){
			this.ug=ug;
		}
		
		//重绘界面的方法
		public void updatePanel(int flag,int statusFlag)
		{
			this.flag=flag;
			this.statusFlag=statusFlag;
			repaint();
		}
		
		//自定义登录按钮的监听器
		public class signInButtonListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				updatePanel(5,STATUS_CHANGE);
			}
			
		}
		
		//自定义"加入对比"按钮的监听器
		public class cmpButtonListener implements ActionListener
		{
			int ID;
			GridBagConstraints con;
			JTable table;//表格
			
			public cmpButtonListener(GridBagConstraints con,JTable table)
			{
				this.table=table;
				this.con=con;
			}
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				ID=table.getSelectedRow();
				//牛人已经添加超过3个的情况,输出警告
				if(personNumber>3)
				{
					JOptionPane.showMessageDialog(null, "本程序最多只支持3个牛人同时对比!");
				}else
				{
					//除去重复添加的情况
					if(!IDs.containsKey(ID))
					{
				cmp_count++;
				personNumber++;
				IDs.put(ID, ID);
				if(ID>ID_largest)
				{
					ID_largest=ID;
				}
				//重绘窗口
				updatePanel(flag,STATUS_CHANGE);
					}
				}
			}
		}
		
		//自定义"撤销对比"按钮的监听器
		public class cancelButtonListener implements ActionListener
		{
			GridBagConstraints con;
			int ID;
			public cancelButtonListener(GridBagConstraints con,int ID)
			{
				this.ID=ID;
				this.con=con;
			}
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				cmp_count--;
				personNumber--;
				IDs.remove(ID);
				//重绘窗口
				updatePanel(flag,STATUS_CHANGE);
			}
		}
		
		//一个在某个GridBagLayout布局的container里添加组件的公共方法
		public void add(Container container,Component c,GridBagConstraints constraints,int gridx,int gridy,
				int width,int height)
		{
			constraints.gridheight=height;
			constraints.gridwidth=width;
			constraints.gridx=gridx;
			constraints.gridy=gridy;
			container.add(c, constraints);
		}

		//在对比名单下显示牛人名单的方法
		public void joinCmp(GridBagConstraints con)
		{
				JPanel listPanel=new JPanel();
				listPanel.setLayout(new BorderLayout());
				JPanel listPanel2=new JPanel();
				listPanel2.setLayout(new GridLayout(IDs.size(),2));
				int id=0;
			for(int i=0;i<=ID_largest;i++)
			{
				if(IDs.containsKey(i))
				{
				id=IDs.get(i);
				if(!NBersCopy.isEmpty()){
				JLabel nam=new JLabel(NBersCopy.get(id).get(1));
				ButtonWithId cancel_Button=new ButtonWithId("撤销对比",id);
				cancel_Button.addActionListener(new cancelButtonListener(con,i));
				listPanel2.add(nam);listPanel2.add(cancel_Button);
				listPanel.add(listPanel2,BorderLayout.WEST);
				add(learnPanel,listPanel,con,0,2,1,1);
				}
				}
			}
			
		}
		
		//绘制有关牛人信息的表格的方法
		public void drawTable(final int from,final int to,final GridBagConstraints con,int gridx,
				              int gridy)
		{
				
				final Object[][] data=null;
				final String[] header=new String[]{"排名","ID","题数","最近1月AC题数","同期累计AC题数","加入对比"};
			 	final JTable table = new JTable();
			 	
			 	JScrollPane scrollPane=new JScrollPane();
		        scrollPane.setViewportView(table);

		        table.setModel(new DefaultTableModel(data,header)
		        {
		        	/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
		            public Object getValueAt(int row, int column)
					{
						if(column!=5){
							if(NBers.isEmpty()){
								return 0;
							}else{
		        		return NBers.get(row).get(column);
							}
						}else{
						return 1;
						}
		            }
		        	
		        	
		            @Override
		            public int getRowCount() 
		            {
		                return (to-from+1);
		            }

		            @Override
		            public int getColumnCount()
		            {
		                return 6;
		            }
		            @Override
		            public void setValueAt(Object aValue, int row, int column)
		            {
		                //data[row][column] = aValue;
		                fireTableCellUpdated(row, column);
		            }
		            
		            @Override
		            public boolean isCellEditable(int row, int column) 
		            {
		                if (column == 5) 
		                {
		                    return true;
		                } else 
		                {
		                    return false;
		                }
		            }
		        });
		       
		        table.getColumnModel().getColumn(5).setCellEditor(
		                new MyEditor(new cmpButtonListener(con,table),table));

		        table.getColumnModel().getColumn(5).setCellRenderer(
		                new MyRenderer());
		        
		        //不允许选中一行
		        table.setRowSelectionAllowed(false);
				table.invalidate();
				
				add(learnPanel,scrollPane,con,gridx,gridy,1,1);
				validate();
			}
		
		
		public void paintComponent(Graphics g)
		{
			//我的奋斗史
			if(flag==0)
			{	
				final int labelX=100;
				int fieldX=280;
				if(statusFlag==STATUS_HOLD){
					tempScrollPanel.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
					tempScrollPanel.updateUI();
					validate();
				}else{
				tempScrollPanel.setVisible(false);
				historyPanel=new JPanel();
				historyPanel.setLayout(null);
			
		
				updateButton=new JButton("立即更新");
				updateButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						flag=0;
						flag2=0;
						JWindowDemo jd3=new JWindowDemo();
						jd3.setUserGraph(ug);
						jd3.progress.setBounds(labelX-5, 350, 200, 22);
						historyPanel.add(jd3.progress);
						jd3.start();
					}
					
				});
				
				
				
				JLabel infoLabel=new JLabel("基本信息");
				infoLabel.setFont(new Font(null,Font.BOLD,19));
				JLabel name=new JLabel("username:");
				name.setFont(new Font(null,Font.BOLD,16));
				JLabel regisTime=new JLabel("registered on:");
				regisTime.setFont(new Font(null,Font.BOLD,16));
				JLabel rank=new JLabel("rank:");
				rank.setFont(new Font(null,Font.BOLD,16));
				JLabel submitted=new JLabel("problems submitted:");
				submitted.setFont(new Font(null,Font.BOLD,16));
				JLabel solved=new JLabel("problems solved:");
				solved.setFont(new Font(null,Font.BOLD,16));
				JLabel submissions=new JLabel("submissions:");
				submissions.setFont(new Font(null,Font.BOLD,16));
				JLabel ac=new JLabel("accepted:");
				ac.setFont(new Font(null,Font.BOLD,16));
				
				infoLabel.setBounds(labelX, 15, 150, 50);
				name.setBounds(labelX, 70, 100, 30);
				userInformation.nameField.setBounds(fieldX, 70, 100, 30);
				regisTime.setBounds(labelX, 100, 130, 30);
				userInformation.timeField.setBounds(fieldX, 100, 100, 30);
				rank.setBounds(labelX, 130, 100, 30);
				userInformation.rankField.setBounds(fieldX, 130,100, 30);
				submitted.setBounds(labelX, 160, 200, 30);
				userInformation.pb_SubmittedField.setBounds(fieldX, 160, 100, 30);
				solved.setBounds(labelX, 190,200, 30);
				userInformation.pb_SolvedField.setBounds(fieldX, 190, 100, 30);
				submissions.setBounds(labelX, 220, 130, 30);
				userInformation.submissionsField.setBounds(fieldX, 220, 100, 30);
				ac.setBounds(labelX, 250, 100, 30);
				userInformation.ac_Field.setBounds(fieldX, 250, 100, 30);
				
				
				updateButton.setBounds(labelX-5, 300, 72, 22);
				
				historyPanel.add(infoLabel);
				historyPanel.add(updateButton);
				historyPanel.add(name);historyPanel.add(userInformation.nameField);historyPanel.add(regisTime);
				historyPanel.add(userInformation.timeField);historyPanel.add(rank);historyPanel.add(userInformation.rankField);
				historyPanel.add(submitted);historyPanel.add(userInformation.pb_SubmittedField);historyPanel.add(solved);
				historyPanel.add(userInformation.pb_SolvedField);historyPanel.add(submissions);historyPanel.add(userInformation.submissionsField);
				historyPanel.add(ac);historyPanel.add(userInformation.ac_Field);		
				
				historyPanel.setVisible(true);
				sc=new JScrollPane(historyPanel);
				sc.getVerticalScrollBar().setUnitIncrement(SPEED);
				sc.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
				sc.updateUI();
				//使tempscrollPane暂时保存当前的滚动栏
				tempScrollPanel=sc;
				currentPanel.add(sc);
				currentPanel.setVisible(true);
				
				historyPanel.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent event){
						sort = new JMenu("分类比例");
						//动态的分类列表
						sortItem = new JMenuItem[MainFrame.sortVec.size()];
						for( int i = 0;i != MainFrame.sortVec.size();i ++)
						{
							
							sortItem[i] = new JMenuItem(MainFrame.sortVec.elementAt(i));
							sortItem[i].addActionListener(new ActionListener(){
							
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									map=c.getClassifyMap(((JMenuItem)e.getSource()).getText());
									System.out.println(map.size());
									System.out.println(((JMenuItem)e.getSource()).getText());
									updatePanel(4,STATUS_CHANGE);
								}
								
							});
							sort.add(sortItem[i]);
							popUpMenu.removeAll();
							popUpMenu.add(briefInfo);
							popUpMenu.add(achieve);
							popUpMenu.add(count);
							popUpMenu.add(NB);
							popUpMenu.add(sort);
							popUpMenu.add(recommend);
							popUpMenu.add(submit);
						}
						if(event.isPopupTrigger())
							popUpMenu.show(event.getComponent(), event.getX(), event.getY());
					}
					public void mouseReleased(MouseEvent event){
						if(event.isPopupTrigger()){
							popUpMenu.show(event.getComponent(),event.getX(), event.getY());
						}
					}
					
				});//弹出菜单的右键响应;
				
				statusFlag=STATUS_HOLD;
				validate();
			}
			}
			
			//个人亮点
			if(flag==1)
			{
				//当用户只是执行拉大或缩小窗口操作时,滚动栏可以调整大小
				if(statusFlag==STATUS_HOLD)
				{
					tempScrollPanel.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
					tempScrollPanel.updateUI();
					validate();
				}else
				{
				//隐藏之前的页面
				tempScrollPanel.setVisible(false);
				brightPanel=new JPanel();
				brightPanel.setLayout(new BorderLayout());
				
				JLabel bright=new JLabel("个人亮点");
				bright.setFont(new Font(null,Font.BOLD,20));
				
				JPanel updatePanel2=new JPanel();
				updatePanel2.setLayout(new BorderLayout());
				updatePanel2.add(bright,BorderLayout.WEST);
				
				//加载图标
				//URL url=getClass().getResource("Image\\xunzhang.jpg");
				ImageIcon img=new ImageIcon("Image\\xunzhang.jpg");
				img.setImage(img.getImage().getScaledInstance(40, 40,Image.SCALE_DEFAULT));
				
				JPanel contriPanel=new JPanel();
				GridLayout gl=new GridLayout(15,4);
				contriPanel.setLayout(gl);
				
				for(int i=0;i<14;i++)
				{
					JLabel brightLabel=new JLabel(honerName[i]);
					brightLabel.setFont(new Font(null,Font.BOLD,15));
					brightLabel.setIcon(img);//设置上述图标
					JLabel brightTextField=new JLabel(condition[i]);
					brightTextField.setFont(new Font(null,Font.BOLD,13));
					
					JLabel statusLabel=new JLabel();
					if(honer[i]==true){
						statusLabel.setText("恭喜,成就达到!");
						statusLabel.setForeground(new Color(0,128,0));
						}else{
						statusLabel.setText("成就未达成,仍需努力!");
						statusLabel.setForeground(Color.RED);
						}
					statusLabel.setFont(new Font(null,Font.BOLD,15));
					statusLabel.setIcon(img);//设置上述图标
					
					
					contriPanel.add(brightLabel);contriPanel.add(brightTextField);
					contriPanel.add(statusLabel);
				}
				//最后一个成就和状态单独添加
				JLabel brightLabel=new JLabel(honerName[14]);
				brightLabel.setFont(new Font(null,Font.BOLD,15));
				brightLabel.setIcon(img);
				JLabel brightTextField=new JLabel(condition[14]);
				brightTextField.setFont(new Font(null,Font.BOLD,13));
				
				JLabel statusLabel=new JLabel();
				if(honer[14]==true){
					statusLabel.setText("恭喜,成就达到!");
					statusLabel.setForeground(new Color(0,128,0));
					}else{
					statusLabel.setText("成就未达成,仍需努力!");
					statusLabel.setForeground(Color.RED);
					}
				statusLabel.setFont(new Font(null,Font.BOLD,15));
				statusLabel.setIcon(img);//设置上述图标
				
				contriPanel.add(brightLabel);contriPanel.add(brightTextField);
				contriPanel.add(statusLabel);
				
				brightPanel.add(updatePanel2,BorderLayout.NORTH);
				brightPanel.add(contriPanel,BorderLayout.CENTER);
				
				//把brightPanel封装到滚动栏里
				sc=new JScrollPane(brightPanel);
				sc.getVerticalScrollBar().setUnitIncrement(SPEED);
				sc.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
				sc.updateUI();
				
				tempScrollPanel=sc;
				currentPanel.add(sc);
				currentPanel.setVisible(true);
				
				brightPanel.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent event){
						sort = new JMenu("分类比例");
						//动态的分类列表
						sortItem = new JMenuItem[MainFrame.sortVec.size()];
						for( int i = 0;i != MainFrame.sortVec.size();i ++)
						{
							
							sortItem[i] = new JMenuItem(MainFrame.sortVec.elementAt(i));
							sortItem[i].addActionListener(new ActionListener(){
							
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									map=c.getClassifyMap(((JMenuItem)e.getSource()).getText());
									System.out.println(((JMenuItem)e.getSource()).getText());
									updatePanel(4,STATUS_CHANGE);
								}
								
							});
							sort.add(sortItem[i]);
							popUpMenu.removeAll();
							popUpMenu.add(briefInfo);
							popUpMenu.add(achieve);
							popUpMenu.add(count);
							popUpMenu.add(NB);
							popUpMenu.add(sort);
							popUpMenu.add(recommend);
							popUpMenu.add(submit);
						}
						if(event.isPopupTrigger())
							popUpMenu.show(event.getComponent(), event.getX(), event.getY());
					}
					public void mouseReleased(MouseEvent event){
						if(event.isPopupTrigger()){
							popUpMenu.show(event.getComponent(),event.getX(), event.getY());
						}
					}
					
				});//弹出菜单的右键响应;
				
				statusFlag=STATUS_HOLD;
				validate();
				}
			}
			if(flag==2)
			{
				//当用户只是执行拉大或缩小窗口操作时,滚动栏可以调整大小
				if(statusFlag==STATUS_HOLD)
				{
					tempScrollPanel.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
					tempScrollPanel.updateUI();
					validate();
				}else
				{
				//隐藏之前的页面
				tempScrollPanel.setVisible(false);
				countPanel=new JPanel();
				
				countPanel.setLayout(null);
				bar=new BarChart(monthpronum,startMonth,endMonth);
				bar.run();
				
				lastPageButton=new JButton("上一页");
				if(lastButtonStatus==ENABLE){
					lastPageButton.setEnabled(true);
				}else{
					lastPageButton.setEnabled(false);
				}
				
				nextPageButton=new JButton("下一页");
				if(nextButtonStatus==DISABLE){
					nextPageButton.setEnabled(false);
				}else{
					nextPageButton.setEnabled(true);
				}
				
				lastPageButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(pageNum==2){
							lastButtonStatus=DISABLE;
						}
					
							nextButtonStatus=ENABLE;
							pageNum--;
							endMonth=pageNum*12;
							startMonth=endMonth-11;
							statusFlag=STATUS_CHANGE;
							flag=2;
							repaint();
					}
					
				});
				nextPageButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						System.out.println("当前pageNum:"+pageNum);
						System.out.println("当前pageCount:"+pageCount);
						lastButtonStatus=ENABLE;
						if(pageNum==(pageCount-1)){
							nextButtonStatus=DISABLE;
							if(left!=0){
								endMonth=pageNum*12+left;
								startMonth=endMonth-left+1;
							}else{
								endMonth=(pageNum+1)*12;
								startMonth=endMonth-11;
							}
							
						}else{
							endMonth=(pageNum+1)*12;
							startMonth=endMonth-11;	
						}
						flag=2;
						statusFlag=STATUS_CHANGE;
						pageNum++;
						repaint();
					}
					
				});
				
				bar.getChartPanel().setBounds(0, 0, 600, 360);
				lastPageButton.setBounds(230, 380, 60, 20);
				nextPageButton.setBounds(300, 380, 60, 20);
				countPanel.add(bar.getChartPanel());
				countPanel.add(lastPageButton);
				countPanel.add(nextPageButton);
				
				//把countPanel封装到滚动栏里
				sc=new JScrollPane(countPanel);
				sc.getVerticalScrollBar().setUnitIncrement(SPEED);
				sc.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
				sc.updateUI();
				
				tempScrollPanel=sc;
				currentPanel.add(sc);
				currentPanel.setVisible(true);
				
				countPanel.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent event){
						sort = new JMenu("分类比例");
						//动态的分类列表
						sortItem = new JMenuItem[MainFrame.sortVec.size()];
						for( int i = 0;i != MainFrame.sortVec.size();i ++)
						{
							
							sortItem[i] = new JMenuItem(MainFrame.sortVec.elementAt(i));
							sortItem[i].addActionListener(new ActionListener(){
							
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									map=c.getClassifyMap(((JMenuItem)e.getSource()).getText());
									System.out.println(((JMenuItem)e.getSource()).getText());
									updatePanel(4,STATUS_CHANGE);
								}
								
							});
							sort.add(sortItem[i]);
							popUpMenu.removeAll();
							popUpMenu.add(briefInfo);
							popUpMenu.add(achieve);
							popUpMenu.add(count);
							popUpMenu.add(NB);
							popUpMenu.add(sort);
							popUpMenu.add(recommend);
							popUpMenu.add(submit);
						}
						if(event.isPopupTrigger())
							popUpMenu.show(event.getComponent(), event.getX(), event.getY());
					}
					public void mouseReleased(MouseEvent event){
						if(event.isPopupTrigger()){
							popUpMenu.show(event.getComponent(),event.getX(), event.getY());
						}
					}
					
				});//弹出菜单的右键响应;
				
				statusFlag=STATUS_HOLD;
				validate();
				}
			}
			//向牛人学习
			if(flag==3)
			{
				if(statusFlag==STATUS_HOLD)
				{
					tempScrollPanel.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
					tempScrollPanel.updateUI();
					validate();
				}else
				{
					if(cmpPageTime==0){
						try {
							NBers=FunctionSet.getFunctionSet(1, 5);
							NBersCopy=NBers;
						} catch (HttpException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				cmpPageTime++;
				//隐藏之前的页面
				tempScrollPanel.setVisible(false);
				learnPanel=new JPanel();
				
				
				gbLayout=new GridBagLayout();
				learnPanel.setLayout(gbLayout);
				
				constraints=new GridBagConstraints();
				constraints.fill=GridBagConstraints.BOTH;
				constraints.weightx=1.0;
				constraints.weighty=1.0;
				
				//添加登录按钮
				JPanel updatePanel2=new JPanel();
				updatePanel2.setLayout(new BorderLayout());
				
				JLabel cmpLabel=new JLabel("对比名单");
				cmpLabel.setFont(new Font(null,Font.BOLD,22));
				JLabel joinLabel=new JLabel("加入对比");
				joinLabel.setFont(new Font(null,Font.BOLD,22));
				JLabel titleLabel=new JLabel("从牛人中选取对比对象");
				titleLabel.setFont(new Font(null,Font.BOLD,22));
				JLabel rangeLabel=new JLabel("选择牛人排名区间(1-1000)");
				rangeLabel.setFont(new Font(null,Font.BOLD,18));
				final JTextField joinField=new JTextField(10);
				
				JButton joinButton=new JButton("加入对比");
				joinButton.addActionListener(new ActionListener() 
				{
					
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						// TODO Auto-generated method stub
						//限制最多添加3个牛人进行对比
						if(personNumber>3)
						{
							JOptionPane.showMessageDialog(null, "本程序最多只支持3个牛人同时对比!");
						}else
						{
							boolean found=false;
							String ID=joinField.getText().toString();
							
							for(int i=0;i<NBersCopy.size();i++){
								if(ID.equals(NBersCopy.get(i).get(5))){
									found=true;
									if(IDs.containsKey(i)){
									JOptionPane.showMessageDialog(null, "该牛人已在对比名单中!");
									break;
									}else{
									IDs.put(i, i);
									flag=2;
									cmp_count++;
									personNumber++;
									updatePanel(flag,STATUS_CHANGE);
									break;
									}
								}
							}
							if(!found){
								Vector<String> NB=null;
								try {
									NB = FunctionSet.GetVectorByUserCount(ID);
								} catch (HttpException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								NBersCopy.add(NB);
								int IdTemp=(to-from+1);
								IDs.put(IdTemp,IdTemp);
								ID_largest=IdTemp;
								flag=2;
								cmp_count++;
								personNumber++;
								updatePanel(flag,STATUS_CHANGE);
							}
						}	
					}
				});
				
				
				JButton join_now=new JButton("立即对比");
				join_now.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) 
					{
						// TODO Auto-generated method stub
						//转向专门用来跟牛人对比的页面
						flag=6;
						updatePanel(flag,STATUS_CHANGE);
					}
					
				});
				
				
				//把一个用于输入用户ID的输入框和"加入对比"按钮添加到cmpPanel2面板中
				JPanel cmpPanel2=new JPanel();
				cmpPanel2.add(joinField);cmpPanel2.add(joinButton);

				JPanel cmpPanel=new JPanel();
				cmpPanel.setLayout(new BorderLayout());
				cmpPanel.add(cmpPanel2,BorderLayout.WEST);
				//设置"立即对比"按钮置于最西面
				JPanel joinPanel=new JPanel();
				joinPanel.setLayout(new BorderLayout());
				joinPanel.add(join_now,BorderLayout.WEST);
				
				
				
				rankFrom=new JTextField(6);
				rankTo=new JTextField(6);
				rankFrom.setText(String.valueOf(from));
				rankTo.setText(String.valueOf(to));
				JLabel line=new JLabel("——");
				OkButton=new JButton("确定");
				
				JPanel rankPanel2=new JPanel();
				rankPanel2.add(rankFrom);rankPanel2.add(line);
				rankPanel2.add(rankTo);rankPanel2.add(OkButton);
				
				JPanel rankPanel=new JPanel();
				rankPanel.setLayout(new BorderLayout());
				rankPanel.add(rankPanel2,BorderLayout.WEST);
				//加入各个组件到"向牛人学习"面板中
				add(learnPanel,cmpLabel,constraints,0,0,1,1);
				add(learnPanel,updatePanel2,constraints,1,0,1,1);
				add(learnPanel,joinLabel,constraints,0,cmp_count+3,1,1);
				add(learnPanel,cmpPanel,constraints,0,cmp_count+7,1,1);
				add(learnPanel,joinPanel,constraints,0,cmp_count+8,1,1);
				add(learnPanel,titleLabel,constraints,0,cmp_count+9,1,1);
				add(learnPanel,rangeLabel,constraints,0,cmp_count+11,1,1);
				add(learnPanel,rankPanel,constraints,0,cmp_count+13,1,1);
				//绘制关于牛人信息的表格
				drawTable(from, to, constraints, 0, cmp_count+14);
				//使对比名单中出现已加入对比的牛人ID
				joinCmp(constraints);
				//为"确定"按钮添加监听器
				OkButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						// TODO Auto-generated method stub
						//如果排名输入框中有一个或两个为空
						if(rankFrom.getText().isEmpty()||
						   rankTo.getText().isEmpty())
						{
							JOptionPane.showMessageDialog(null, "请在输入框中输入数字!");
						}else
						{
						from=Integer.parseInt(rankFrom.getText().toString());
						to=Integer.parseInt(rankTo.getText().toString());
						try {
							NBers=FunctionSet.getFunctionSet(from, to);
							NBersCopy=NBers;
						} catch (HttpException e1){
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1){
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						updatePanel(3,STATUS_CHANGE);
						}
					}
					
					
				});
				
				sc=new JScrollPane(learnPanel);
				sc.getVerticalScrollBar().setUnitIncrement(SPEED);
				sc.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
				sc.updateUI();
				
				tempScrollPanel=sc;
				currentPanel.add(sc);
				currentPanel.setVisible(true);
				
				learnPanel.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent event){
						sort = new JMenu("分类比例");
						//动态的分类列表
						sortItem = new JMenuItem[MainFrame.sortVec.size()];
						for( int i = 0;i != MainFrame.sortVec.size();i ++)
						{
							
							sortItem[i] = new JMenuItem(MainFrame.sortVec.elementAt(i));
							sortItem[i].addActionListener(new ActionListener(){
							
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									map=c.getClassifyMap(((JMenuItem)e.getSource()).getText());
									System.out.println(((JMenuItem)e.getSource()).getText());
									updatePanel(4,STATUS_CHANGE);
								}
								
							});
							sort.add(sortItem[i]);
							popUpMenu.removeAll();
							popUpMenu.add(briefInfo);
							popUpMenu.add(achieve);
							popUpMenu.add(count);
							popUpMenu.add(NB);
							popUpMenu.add(sort);
							popUpMenu.add(recommend);
							popUpMenu.add(submit);
						}
						if(event.isPopupTrigger())
							popUpMenu.show(event.getComponent(), event.getX(), event.getY());
					}
					public void mouseReleased(MouseEvent event){
						if(event.isPopupTrigger()){
							popUpMenu.show(event.getComponent(),event.getX(), event.getY());
						}
					}
					
				});//弹出菜单的右键响应;
				
				statusFlag=STATUS_HOLD;
				validate(); 
				}
			}
			
			
			if(flag==4)
			{
				if(statusFlag==STATUS_HOLD)
				{
					tempScrollPanel.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
					tempScrollPanel.updateUI();
					validate();
				}else
				{
				sortPanel=new JPanel();
				//隐藏之前的页面
				tempScrollPanel.setVisible(false);
				sortPanel.setLayout(null);
				
				
				PieChart pie=new PieChart(map);
				pie.run();
				pie.getChartPanel().setBounds(0, 0, 600, 360);
				
				
				sortPanel.add(pie.getChartPanel());
				sc=new JScrollPane(sortPanel);
				sc.getVerticalScrollBar().setUnitIncrement(SPEED);
				sc.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
				sc.updateUI();
				
				tempScrollPanel=sc;
				currentPanel.add(sc);
				currentPanel.setVisible(true);
				
				sortPanel.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent event){
						sort = new JMenu("分类比例");
						//动态的分类列表
						sortItem = new JMenuItem[MainFrame.sortVec.size()];
						for( int i = 0;i != MainFrame.sortVec.size();i ++)
						{
							
							sortItem[i] = new JMenuItem(MainFrame.sortVec.elementAt(i));
							sortItem[i].addActionListener(new ActionListener(){
							
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									map=c.getClassifyMap(((JMenuItem)e.getSource()).getText());
									System.out.println(((JMenuItem)e.getSource()).getText());
									updatePanel(4,STATUS_CHANGE);
								}
								
							});
							sort.add(sortItem[i]);
							popUpMenu.removeAll();
							popUpMenu.add(briefInfo);
							popUpMenu.add(achieve);
							popUpMenu.add(count);
							popUpMenu.add(NB);
							popUpMenu.add(sort);
							popUpMenu.add(recommend);
							popUpMenu.add(submit);
						}
						if(event.isPopupTrigger())
							popUpMenu.show(event.getComponent(), event.getX(), event.getY());
					}
					public void mouseReleased(MouseEvent event){
						if(event.isPopupTrigger()){
							popUpMenu.show(event.getComponent(),event.getX(), event.getY());
						}
					}
					
				});//弹出菜单的右键响应;
				
				statusFlag=STATUS_HOLD;
				validate(); 
				
				}
			}
			//题目推荐
			if(flag==5)
			{
				if(statusFlag==STATUS_HOLD)
				{
					tempScrollPanel.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
					tempScrollPanel.updateUI();
					validate();
				}else
				{
					
				Vector<String> goodPro=null;
				int row=10;
				try {
					goodPro=c.getGoodPro();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recommendPanel=new JPanel();
				//隐藏之前的页面
				tempScrollPanel.setVisible(false);
				recommendPanel.setLayout(null);
				
				
				
				JLabel recommendLabel=new JLabel("您接下来应该攻克的10道题是:");
				recommendLabel.setFont(new Font(null,Font.BOLD,18));
				recommendLabel.setBounds(10, 10, 280, 30);
				//定义10个能够链接到相应网页的标签
				for(int i=0;i<goodPro.size();i++){
					LinkLabel pro=new LinkLabel(goodPro.get(i),
					"http://acm.hdu.edu.cn/showproblem.php?pid="+goodPro.get(i));
					row+=35;
					pro.setBounds(40, row,  pro.length(), 30);
					recommendPanel.add(pro);
				}
				
				
				//添加上述各种组件到"题目推荐"面板中
				recommendPanel.add(recommendLabel);
				sc=new JScrollPane(recommendPanel);
				sc.getVerticalScrollBar().setUnitIncrement(SPEED);
				sc.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
				sc.updateUI();
				
				tempScrollPanel=sc;
				currentPanel.add(sc);
				currentPanel.setVisible(true);
				
				recommendPanel.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent event){
						sort = new JMenu("分类比例");
						//动态的分类列表
						sortItem = new JMenuItem[MainFrame.sortVec.size()];
						for( int i = 0;i != MainFrame.sortVec.size();i ++)
						{
							
							sortItem[i] = new JMenuItem(MainFrame.sortVec.elementAt(i));
							sortItem[i].addActionListener(new ActionListener(){
							
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									map=c.getClassifyMap(((JMenuItem)e.getSource()).getText());
									System.out.println(((JMenuItem)e.getSource()).getText());
									updatePanel(4,STATUS_CHANGE);
								}
								
							});
							sort.add(sortItem[i]);
							popUpMenu.removeAll();
							popUpMenu.add(briefInfo);
							popUpMenu.add(achieve);
							popUpMenu.add(count);
							popUpMenu.add(NB);
							popUpMenu.add(sort);
							popUpMenu.add(recommend);
							popUpMenu.add(submit);
						}
						if(event.isPopupTrigger())
							popUpMenu.show(event.getComponent(), event.getX(), event.getY());
					}
					public void mouseReleased(MouseEvent event){
						if(event.isPopupTrigger()){
							popUpMenu.show(event.getComponent(),event.getX(), event.getY());
						}
					}
					
				});//弹出菜单的右键响应;
				
				statusFlag=STATUS_HOLD;
				validate(); 
				
				}
			}
			
			
			//"立即对比"按钮触发的对比页面
			if(flag==6)
			{
				if(statusFlag==STATUS_HOLD)
				{
					tempScrollPanel.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
					tempScrollPanel.updateUI();
					validate();
				}else
				{
				tempScrollPanel.setVisible(false);
				joinNowPanel=new JPanel();
				
				gbLayout=new GridBagLayout();
				joinNowPanel.setLayout(gbLayout);
				
				constraints=new GridBagConstraints();
				constraints.fill=GridBagConstraints.BOTH;
				constraints.weightx=1.0;
				constraints.weighty=0;
				
				//定义各种用户数据
				JLabel foundCmp=new JLabel("基本对比");
				foundCmp.setFont(new Font(null,Font.BOLD,19));
				JLabel name=new JLabel("username:");
				name.setFont(new Font(null,Font.BOLD,15));
				JLabel regisTime=new JLabel("registered on:");
				regisTime.setFont(new Font(null,Font.BOLD,15));
				JLabel rank=new JLabel("rank:");
				rank.setFont(new Font(null,Font.BOLD,15));
				JLabel submitted=new JLabel("problems submitted:");
				submitted.setFont(new Font(null,Font.BOLD,15));
				JLabel solved=new JLabel("problems solved:");
				solved.setFont(new Font(null,Font.BOLD,15));
				JLabel submissions=new JLabel("submissions:");
				submissions.setFont(new Font(null,Font.BOLD,15));
				JLabel ac=new JLabel("accepted:");
				ac.setFont(new Font(null,Font.BOLD,15));
				
				Vector<JLabel> labelVector=new Vector<JLabel>();
				labelVector.add(name);labelVector.add(regisTime);
				labelVector.add(rank);labelVector.add(submitted);
				labelVector.add(solved);labelVector.add(submissions);
				labelVector.add(ac);
				Vector<Function> comparers=new Vector<Function>();
				Vector<UserInformation> infos=new Vector<UserInformation>();
				comparers.add(c);
				infos.add(userInformation);
				for(int i=0;i<=ID_largest;i++){
					if(IDs.containsKey(i)){
					try {
						
						Function fc=new Function(NBersCopy.get(IDs.get(i)).get(5));
						comparers.add(fc);
						UserInformation inf=new UserInformation(fc.personinfo.getUsername(), 
						fc.personinfo.getRegistertime(), fc.personinfo.getRank(), 
						fc.personinfo.getTotalprosubmitted(), fc.personinfo.getTotalprosolved(), 
						fc.personinfo.getTotalsubmissions(), fc.personinfo.getTotalaccepted());
						infos.add(inf);
					} catch (HttpException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					
				}
				JPanel partPanel2=new JPanel();
				partPanel2.setLayout(new GridLayout(7,personNumber+1));
				//添加几个TextField用于显示用户与牛人相应信息项的数据
				for(int i=0;i<7;i++)
				{
					partPanel2.add(labelVector.get(i));
					
					for(int j=0;j<personNumber;j++){
					System.out.println("personNumber:"+personNumber);
					System.out.println("i的值为:"+i);
					System.out.println("j的值为:"+j);
					JLabel info=infos.get(j).getField(i);
					partPanel2.add(info);
					}
				}
				
				JPanel partPanel=new JPanel();
				partPanel.add(partPanel2);
				
				//添加上述组件到立即对比的面板中
				add(joinNowPanel,foundCmp,constraints,0,0,1,1);
				add(joinNowPanel,partPanel,constraints,0,1,1,1);
				
				//启动绘制条形图线程并把条形图添加到立即对比的面板中
				BarChart2 bar=new BarChart2(comparers,personNumber);
				bar.run();
				add(joinNowPanel,bar.getChartPanel(),constraints,0,10,3,3);
				
				sc=new JScrollPane(joinNowPanel);
				sc.getVerticalScrollBar().setUnitIncrement(SPEED);
				sc.setPreferredSize(new Dimension(currentPanel.getWidth(),currentPanel.getHeight()));
				sc.updateUI();
				tempScrollPanel=sc;
				currentPanel.add(sc);
				currentPanel.setVisible(true);
				
				joinNowPanel.addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent event){
						sort = new JMenu("分类比例");
						//动态的分类列表
						sortItem = new JMenuItem[MainFrame.sortVec.size()];
						for( int i = 0;i != MainFrame.sortVec.size();i ++)
						{
							
							sortItem[i] = new JMenuItem(MainFrame.sortVec.elementAt(i));
							sortItem[i].addActionListener(new ActionListener(){
							
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									map=c.getClassifyMap(((JMenuItem)e.getSource()).getText());
									System.out.println(((JMenuItem)e.getSource()).getText());
									updatePanel(4,STATUS_CHANGE);
								}
								
							});
							sort.add(sortItem[i]);
							popUpMenu.removeAll();
							popUpMenu.add(briefInfo);
							popUpMenu.add(achieve);
							popUpMenu.add(count);
							popUpMenu.add(NB);
							popUpMenu.add(sort);
							popUpMenu.add(recommend);
							popUpMenu.add(submit);
						}
						if(event.isPopupTrigger())
							popUpMenu.show(event.getComponent(), event.getX(), event.getY());
					}
					public void mouseReleased(MouseEvent event){
						if(event.isPopupTrigger()){
							popUpMenu.show(event.getComponent(),event.getX(), event.getY());
						}
					}
					
				});//弹出菜单的右键响应;
				
				statusFlag=STATUS_HOLD;
				validate(); 
			}
		  }
			
		}
		
	public ClientInterface( final String userName) 
	{
		// TODO Auto-generated constructor stub
		this.userName=userName;
		this.setLayout(new BorderLayout());
		popUpMenu.add(briefInfo);
		popUpMenu.add(achieve);
		popUpMenu.add(count);
		popUpMenu.add(NB);
		popUpMenu.add(sort);
		popUpMenu.add(recommend);
		popUpMenu.add(submit);
		
		briefInfo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updatePanel(0,STATUS_CHANGE);
			}
			
		});
		achieve.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updatePanel(1,STATUS_CHANGE);
			}
			
		});
		count.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//updatePanel(2,STATUS_CHANGE);
				//updatePanel(4,STATUS_CHANGE);
				updatePanel(2,STATUS_CHANGE);
			}
			
		});
		NB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updatePanel(3,STATUS_CHANGE);
			}
			
		});
		
		recommend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updatePanel(5,STATUS_CHANGE);
			}
		});
		
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PostCode pc=new PostCode(userName,null,"1000");
			}
		});
		
		//动态的分类列表
		sortItem = new JMenuItem[MainFrame.sortVec.size()];
		for( int i = 0;i != MainFrame.sortVec.size();i ++)
		{
			
			sortItem[i] = new JMenuItem(MainFrame.sortVec.elementAt(i));
			sortItem[i].addActionListener(new ActionListener(){
			
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					map=c.getClassifyMap(((JMenuItem)e.getSource()).getText());
					System.out.println(((JMenuItem)e.getSource()).getText());
					updatePanel(4,STATUS_CHANGE);
				}
				
			});
			sort.add(sortItem[i]);
		}
		
		//当前面板
		currentPanel=new JPanel();
		
		//导航面板
		JPanel guidePanel=new JPanel(new GridLayout(4,1));
		
		JLabel shui=new JLabel("谁");
		JLabel yu=new JLabel("与");
		JLabel zheng=new JLabel("争");
		JLabel feng=new JLabel("锋");
		
		shui.setFont(new Font(null,Font.BOLD,20));
		yu.setFont(new Font(null,Font.BOLD,20));
		zheng.setFont(new Font(null,Font.BOLD,20));
		feng.setFont(new Font(null,Font.BOLD,20));
		
		guidePanel.add(shui);guidePanel.add(yu);guidePanel.add(zheng);
		guidePanel.add(feng);
		
		add(guidePanel,BorderLayout.WEST);
		add(currentPanel,BorderLayout.CENTER);
		
		
		try {
			this.c=new Function(this.userName);
		} catch (HttpException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	    this.userInformation=new UserInformation(this.c.personinfo.getUsername(),
	    this.c.personinfo.getRegistertime(), 
	    this.c.personinfo.getRank(), this.c.personinfo.getTotalprosubmitted(), 
	    this.c.personinfo.getTotalprosolved(), this.c.personinfo.getTotalsubmissions(),
	    this.c.personinfo.getTotalaccepted());
	    this.month=this.c.getMonth();
	    this.monthpronum=this.c.getMonthsProNum();
	    this.left=((this.month)%12);
	    this.honer=this.c.getHoner();
	    
	    if(this.left!=0){
	    	this.pageCount=(this.month/12)+1;
	    }else{
	    	this.pageCount=(this.month/12);
	    }
		
	      this.pageNum=1;
		  this.startMonth=1;
		  this.endMonth=12;
		  for(int i=0;i<monthpronum.length;i++){
			  if(monthpronum[i]!=0)
				  zeroPro=false;
		  }
		  if(zeroPro){
			  this.lastButtonStatus=this.DISABLE;
			  this.nextButtonStatus=this.DISABLE;
		  }else{
		  this.lastButtonStatus=this.DISABLE;
		  this.nextButtonStatus=this.ENABLE;
		  }
		  this.statusFlag=this.STATUS_CHANGE;
		  repaint();
	}

}



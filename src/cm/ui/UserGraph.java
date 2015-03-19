
package cm.ui;
import java.util.*;

import javax.swing.*;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;

import cm.service.DefSetting;

import java.awt.*;
import java.awt.event.*;

public class UserGraph extends JInternalFrame
{
	public DefSetting def=new DefSetting();
	public  JPanel currentPanel;
	public  ClientInterface c;
	public  UserGraph ug;
	public  String userName;
	public  int width=640,height=440;
	public static JWindowDemo jd;
	
	/*
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		
		
	}
	*/
	public UserGraph()
	{
		super(" ",true,true,true,true);
		ug=this;
		currentPanel=new JPanel();
		currentPanel.setLayout(null);
		this.setLayout(new BorderLayout());
		this.setLocation(180,60);
		this.setSize(width, height);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//添加头像
				JLabel logoLabel=new JLabel();
				ImageIcon img=new ImageIcon("Image\\d.png");
				img.setImage(img.getImage().getScaledInstance(99, 109,Image.SCALE_DEFAULT));
				logoLabel.setIcon(img);
				logoLabel.setBounds((int)(0.5*width-55), (int)(0.5*height-140), 99, 109);
				//添加"用户名"标签
				JLabel userNameLabel=new JLabel("用户名");
				userNameLabel.setFont(new Font(null,Font.PLAIN,15));
				userNameLabel.setBounds((int)(0.5*width-127), (int)(0.5*height-3), 60, 20);
				//添加输入用户名的输入框
				final JTextField user_nameField=new JTextField(10);
				user_nameField.setBounds((int)(0.5*width-70),(int)(0.5*height-5),140,22);
				user_nameField.setText(def.getDefUserAccount());
						
				//添加登录确定按钮
				JButton confirmButton=new JButton();
				//URL url2=getClass().getResource("Image\\sign.jpg");
				confirmButton.setIcon(new ImageIcon("Image\\sign.png"));
				confirmButton.setBounds((int)(0.5*width+15),(int)(0.5*height+30),55,20);
						
					
				try {
				      this.setSelected(true);
				     } catch (java.beans.PropertyVetoException ex) {
				          System.out.println("Exception while selecting");
				      }
						
						//为输入用户名完成后按登录按钮添加监听器
					confirmButton.addActionListener(new ActionListener() 
					{
							
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							// TODO Auto-generated method stub
							//设置"登录"按钮的内容为用户ID
							userName=user_nameField.getText().toString();
							if(userName.isEmpty()){
								JOptionPane.showMessageDialog(null, "用户名不能为空!");
							}
							else{
							ug.setTitle(userName);
							ClientInterface.flag=0;
							ClientInterface.flag2=1;
							jd=new JWindowDemo();
					        jd.setUserGraph(ug);
					        jd.progress.setBounds((int)(0.5*width-150), (int)(0.5*height+80), 290, 25);
					        currentPanel.add(jd.progress);
					        jd.start();
				
							}
						}
				});
					
					currentPanel.add(logoLabel);currentPanel.add(userNameLabel);
					currentPanel.add(user_nameField);currentPanel.add(confirmButton);
					this.add(currentPanel,BorderLayout.CENTER);
	}

}

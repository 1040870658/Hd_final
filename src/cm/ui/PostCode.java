//林长荣

package cm.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.httpclient.HttpException;

import cm.service.Submit;

public class PostCode
{
	private JFrame postFrame=new JFrame();
	private JPanel panel=new JPanel(new FlowLayout());
	private JLabel usercountLabel=new JLabel("账号:"),passwordLabel=new JLabel("密码:"),
			pronumLabel=new JLabel("题号:"),languageLabel=new JLabel("语言:"),codeLabel=new JLabel("代码  ");
	private JPasswordField passwordField=new JPasswordField(10);
	private JTextField usercountField=new JTextField(10),pronumField=new JTextField(10);		
	private String language[]={"G++","GCC","C++","C","Pascal","Java"};
	private JComboBox<String> langComboBox=new JComboBox<String>(language);
	private JTextArea codeArea=new JTextArea(15, 30);
	private JButton okButton=new JButton("确认");
	private JLabel statuLabel=new JLabel("未提交");
	private JScrollPane codeScrollPane=new JScrollPane(codeArea);
	
	public PostCode(String usercount,String password,String pronum)
	{
		// TODO Auto-generated constructor stub
		Box vBox=Box.createVerticalBox();
		
		Box hBox1=Box.createHorizontalBox();
		hBox1.add(usercountLabel);
		hBox1.add(usercountField);
		usercountField.setText(usercount);
		hBox1.add(Box.createHorizontalStrut(10));
		hBox1.add(passwordLabel);
		hBox1.add(passwordField);
		passwordField.setText(password);
		vBox.add(hBox1);
		
		Box hBox2=Box.createHorizontalBox();
		hBox2.add(pronumLabel);
		hBox2.add(pronumField);
		pronumField.setText(pronum);
		hBox2.add(Box.createHorizontalStrut(75));
		hBox2.add(languageLabel);
		hBox2.add(langComboBox);
		vBox.add(hBox2);
		
		Box hBox3=Box.createHorizontalBox();
		hBox3.add(codeLabel);
		vBox.add(hBox3);
		
		Box hBox4=Box.createHorizontalBox();
		hBox4.add(codeScrollPane);
		vBox.add(hBox4);
		
		Box hBox5=Box.createHorizontalBox();
		hBox5.add(okButton);
		hBox5.add(Box.createHorizontalStrut(10));
		hBox5.add(statuLabel);
		vBox.add(hBox5);
		
		panel.add(vBox);
		
		okButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				// TODO Auto-generated method stub
				String usercountString=usercountField.getText();
				String passwordString=passwordField.getText();
				String pronumString=pronumField.getText();
				int langIndex=langComboBox.getSelectedIndex();
				String codeString=codeArea.getText();
				
				//System.out.println(usercountString+" "+passwordString+" "+pronumString+" "+langIndex);
				//System.out.println(codeString);
				Submit submit =null;
				try
				{
					submit = new Submit(usercountString,passwordString);
				} catch (HttpException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try
				{
					submit.submitACode(codeString, langIndex, Integer.parseInt(pronumString));
				} catch (NumberFormatException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HttpException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int flag=0;
				while(flag==0)
				{
					try
					{
						statuLabel.setText(submit.getStatus());
					} catch (HttpException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					flag=submit.getFlag();
				}
				
			}
		});
		
		postFrame.setContentPane(panel);
		postFrame.setSize(400,420);
		postFrame.setLocation(420,120);
		postFrame.setVisible(true);
		postFrame.setResizable(false);
		postFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

}

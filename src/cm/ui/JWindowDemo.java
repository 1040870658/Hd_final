package cm.ui;
import javax.swing.*;

import org.apache.commons.httpclient.HttpException;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;

import cm.service.Function;

import java.awt.*;
import java.io.IOException;
import java.net.*;


public  class JWindowDemo extends JWindow implements Runnable {
	private UserGraph ug;
	Thread splashThread;  
	JProgressBar progress; 
  
  public JWindowDemo() {
  	Container container=getContentPane(); 
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  
    progress = new JProgressBar(1,100); 
    progress.setStringPainted(true); 
    progress.setString("正在初始化数据,请稍候......");  
    progress.setBackground(Color.BLACK); 
    progress.setFocusable(false); 
    pack(); 
  }
  
  public void setUserGraph(UserGraph ug){
	  this.ug=ug;
  }
  
  public void start(){
    this.toFront();  
    splashThread=new Thread(this); 
    splashThread.start(); 
  }

  public void run(){
    setVisible(true); 
 
    if(ClientInterface.flag2==0){
    try {
      for (int i=0;i<40;i++){
    		  Thread.sleep(20); 
    		  progress.setValue(progress.getValue()+3);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  ug.c.updatePanel(0, ug.c.STATUS_CHANGE);
  dispose();
  
  } else{
	 
	  for (int i=0;i<100;i++){
		  try {
			 Thread.sleep(30);
			 progress.setValue(progress.getValue()+2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
  }		
	  
			  try {
					if(new Function(ug.userName).personinfo==null){
						JOptionPane.showMessageDialog(null, "用户不存在!");
						ug.dispose();
						UserGraph newUG=new UserGraph();
						MainFrame.desktopPane.add(newUG);
						newUG.setVisible(false);
						newUG.setVisible(true);
					}else{
						ug.c=new ClientInterface(ug.userName);
						ug.c.setUserGraph(ug);
						ug.getContentPane().add(ug.c,BorderLayout.CENTER);
						ug.c.repaint();
						ug.currentPanel.setVisible(false);
					}
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  }

	  try 
		{
			//UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
			//UIManager.setLookAndFeel(new SubstanceSaharaLookAndFeel());
			UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());
			//UIManager.setLookAndFeel(new SubstanceMistAquaLookAndFeel());
			//UIManager.setLookAndFeel(new SubstanceModerateLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		
	    JFrame.setDefaultLookAndFeelDecorated(true);  
	    
	    //设置按钮外观  
	    SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());
	 
  	}
  }




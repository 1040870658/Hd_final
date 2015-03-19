package cm.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//能够链接到具体网页的自定义标签类
public class LinkLabel extends JLabel
{
	private Desktop desktop;
	URI urL;
	int strLength;
	
	public int length(){
		return strLength*13+30;
	}
	public LinkLabel(String questionInfo,String url) 
	{		
			this.strLength=questionInfo.length();
			this.setFont(new Font(null,Font.BOLD,17));
			this.setForeground(Color.GREEN);
			//为标签设置图标
			//URL url3=getClass().getResource("/Image/f.jpg");
			ImageIcon img=new ImageIcon("Image\\f.jpg");
			img.setImage(img.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));
			this.setIcon(img);
		try 
		{
			urL=new URI(url);
			desktop=Desktop.getDesktop();
			setText(questionInfo);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					// TODO Auto-generated method stub
					runBrowser();
				}
			});
			} catch (URISyntaxException e) 
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	
		}
	
	public boolean checkBrowser()
	{
		return (Desktop.isDesktopSupported() && desktop
			   .isSupported(Desktop.Action.BROWSE));
	}
	public void runBrowser()
	{
		if(checkBrowser())
		{
			try 
			{
				desktop.browse(urL);
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

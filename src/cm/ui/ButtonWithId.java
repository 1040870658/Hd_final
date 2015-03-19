package cm.ui;

import javax.swing.JButton;

//带有ID标识的自定义按钮类,使能够定位到具体按钮
public class ButtonWithId extends JButton
{
	String text;
	int ID;
	
	public ButtonWithId(String text,int ID) 
	{
		// TODO Auto-generated constructor stub
		this.text=text;
		this.ID=ID;
		this.setText(text);
	}
	
	public int getID()
	{
		return ID;
	}

}

package cm.ui;

import javax.swing.JLabel;
import javax.swing.JTextField;

//用户信息类,提供各种用户数据的显示
public class UserInformation 
{
	JLabel nameField,timeField,rankField,
	pb_SubmittedField,pb_SolvedField,submissionsField,ac_Field;
	JLabel tempField;
	JLabel tempLabel;
	
	public UserInformation(String name,String time,String rank,String submittedCount,
		   String solvedCount,String submissionCount,String acCount) 
	{
		// TODO Auto-generated constructor stub
		nameField=new JLabel(name);
		timeField=new JLabel(time);
		rankField=new JLabel(rank);
		pb_SubmittedField=new JLabel(submittedCount);
		pb_SolvedField=new JLabel(solvedCount);
		submissionsField=new JLabel(submissionCount);
		ac_Field=new JLabel(acCount);
	}
	
	public JLabel getField(int index){
		switch(index){
		case 0:
			tempField=nameField;
			break;
		case 1:
			tempField=timeField;
			break;
		case 2:
			tempField=rankField;
			break;
		case 3:
			tempField=pb_SubmittedField;
			break;
		case 4:
			tempField=pb_SolvedField;
			break;
		case 5:
			tempField=submissionsField;
			break;
		case 6:
			tempField=ac_Field;
			break;
		}
		return tempField;
	}

}

package cm.service;

import java.io.IOException;
import java.util.*;

import org.apache.commons.httpclient.HttpException;

import cm.model.*;


public class FunctionSet 
{
	public static Vector<Vector<String>> getFunctionSet(int from,int to) throws HttpException, IOException
	{
		Vector<Vector<String>> v=new Vector<Vector<String>>();
		for(int i=from;i<=to;i++)
		{
			PersonInfo personinfo=PersonService.getPerson(i);
			Function f=new Function(personinfo.getUsercount());
			Vector<String> vv=new Vector<String>();
			vv.addElement(personinfo.getRank());
			vv.addElement(personinfo.getUsername());
			vv.addElement(personinfo.getTotalprosolved());
			int num=f.getMonth();
			int a[]=f.getMonthsProNum();
			
			vv.addElement(new String(""+a[num]));
			a=f.getAccumpronum();
			vv.addElement(new String(""+a[num]));
			vv.addElement(personinfo.getUsercount());
			v.add(vv);
		}
		return v;
	}
	
	public static Vector<String> changeVectorElement(Vector<String> v)
	{
		Vector<String> ret=new Vector<String>();
		int k=0;
		String str=new String();
		for(int i=0;i<v.size();i++)
		{
			str+=v.elementAt(i)+" ";
			k++;
			if(k==12)
			{
				ret.add(str);
				str="";
				k=0;
			}
			if(i==v.size()-1)
			{
				ret.add(str);
			}
			
		}
		return ret;
		
	}
	public static Vector<String> GetVectorByUserCount(String usercount) throws HttpException, IOException
	{
		Vector<String> ret=new Vector<String>();
		Function f=new Function(usercount);
		ret.addElement(f.personinfo.getRank());
		ret.addElement(f.personinfo.getUsername());
		ret.addElement(f.personinfo.getTotalprosolved());
		
		int num=f.getMonth();
		int a[]=f.getMonthsProNum();
		ret.addElement(new String(""+a[num]));
		a=f.getAccumpronum();
		ret.addElement(new String(""+a[num]));
		ret.addElement(f.personinfo.getUsercount());
		return ret;
	}
}

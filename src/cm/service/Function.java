package cm.service;
import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import cm.utils.SpiderUtils;
import cm.model.PersonInfo;
import cm.model.ProStruct;
import cm.dao.*;
import java.util.*;
import java.io.*;

public class Function 
{
	public PersonInfo personinfo;
	private final int HONERNUM=15;
	private boolean honer[] = new boolean [HONERNUM];
	private int monthpronum[] = new int[120] ;
	private int accumpronum[] = new int[120];
	private int monthnum = 0;
	
	private int getMonth(String ptime)
	{
		//截取月份,并转化为int
		String protime = new String(ptime);
		String arr[] = protime.split("-");
		protime = arr[1];
		int month = Integer.parseInt(protime);
		return month;
	}
	private int getYear(String ptime)
	{
		//截取年份，并转化为int
		String protime = new String(ptime);
		String arr[] = protime.split("-");
		protime = arr[0];
		int year = Integer.parseInt(protime);
		return year;
	}
	private int getHour(String ptime)
	{
		String protime = new String(ptime);
		protime = protime.substring(11, 13);
		int hour = Integer.parseInt(protime);
		return hour;
	}
	public void setAccumpronum()
	{
		accumpronum[0] = monthpronum[0];
		for(int i = 1;i < 120;i++)
		{
			accumpronum[i] = monthpronum[i];
			accumpronum[i] += accumpronum[i-1];
		}
	}
	public void setMonthsProNum()
	{

		for(int i=0;i<120;i++)
			monthpronum[i]=0;
		int num=personinfo.getNum();
		ProStruct prostruct;
		int month,year,index= 0,premonth = -1,k=0;
		int beginmonth,beginyear;
		
		//假设题数为0，将做题月数设为0，第一题时间设为注册时间
		if(num == 0){
			monthnum = 0;
			beginmonth = getMonth(personinfo.getRegistertime());
			beginyear = getYear(personinfo.getRegistertime());
		}
		else{
			prostruct=personinfo.getProStruct(0);
			beginmonth=getMonth(prostruct.protime);
			beginyear = getYear(prostruct.protime);
		}
		for(int i=0;i<num;i++)
		{
			prostruct = personinfo.getProStruct(i);
			month = getMonth(prostruct.protime)-beginmonth;
			year = getYear(prostruct.protime)-beginyear;
			index = year*12+month;
			monthpronum[index]++;
		}
		monthnum = index;
	}
	//有多少个月份做题
	public int getMonth()
	{
		return monthnum;
	}
	//构造函数，仕兴提供返回PersonInfo对象的函数，该构造函数为此类构造一个PersonInfo对象
	//这里一定得注意传进来的一定要是账号
	//账号->username     昵称->nickname,
	//只有穿username才能爬到数据的，例如vjudge1是username，关云长是该账号的昵称，登陆的时候填写的要是账号，新用户的数据才能爬得到
	public Function(String user) throws HttpException, IOException
	{
		personinfo = PersonService.getPerson(user);//仕兴的函数
		
		//若为空，则爬虫抓取数据
		if(personinfo == null)
		{
			//尝试爬取数据，若账号不存在，将personinfo设为null
			personinfo=SpiderUtils.getUsertInfo(user);
			if(personinfo!=null)
			{
				PersonService.addPerson(personinfo);
			}
		}
		for(int i=0;i<HONERNUM;i++)
		{
			honer[i]=false;
		}
		if(personinfo != null){
			setMonthsProNum();
			setAccumpronum();
		}
	}
	//返回第index个月题量
	public int[] getMonthsProNum()
	{
		return monthpronum;
	}
	//返回第index个月的累计题量
	public int[] getAccumpronum()
	{
		return accumpronum;
	}
	//返回一个honer数组，按那个讨论的顺序定义下来的
	public boolean[] getHoner()
	{
		int num=personinfo.getNum();
		if(num>=5)honer[0]=true;
		if(num>=50)honer[1]=true;
		if(num>=100)honer[2]=true;
		if(num>=200)honer[3]=true;
		if(num>=250)honer[4]=true;
		if(num>=400)honer[5]=true;
		if(num>=800)honer[6]=true;
		if(num>=1024)honer[7]=true;
		for(int i=0;i<num;i++)
		{
			ProStruct prostruct=personinfo.getProStruct(i);
			if(Integer.parseInt(prostruct.prosub)-1>=15)
				honer[8]=true;
			int hour=getHour(prostruct.protime);
			if(hour==1) honer[9]=true;
			if(hour==2) honer[10]=true;		
		}
		double rate=Double.parseDouble(personinfo.getTotalaccepted())/Double.parseDouble(personinfo.getTotalsubmissions());
		if(rate>0.3)
			honer[11]=true;
		if(rate>0.6)
			honer[12]=true;
		if(rate>0.9)
			honer[13]=true;
		if(Integer.parseInt(personinfo.getRank())<=25)
			honer[14]=true;
		return honer;
	}
	//返回Map<题型,vector<String>>的分类
	public Map<String,Vector<String>> getClassifyMap(String strClassifyName)
	{
		Map<String,String> allProMap=ClassifyDao.getMap(strClassifyName);
		Map<String,Vector<String>> ret=new HashMap<String,Vector<String>>();
		//ret.put("未分类", new Vector<String>());
		for(int i=0;i<personinfo.getNum();i++)
		{
			ProStruct prostruct=personinfo.getProStruct(i);
			String proType=allProMap.get(prostruct.pronum);
			
			Vector<String> v;
			if(proType==null)
			{
				//v=ret.get("未分类");
				//v.addElement(prostruct.pronum);
				continue;
			}
			if(ret.get(proType)==null)
			{
				ret.put(proType, new Vector<String>());
			}
			v=ret.get(proType);
			v.addElement(prostruct.pronum);
		}
		return ret;
	}
	//返回该用户的推荐题目Vector<String>
		public Vector<String> getGoodPro() throws IOException
		{
			Vector<String> ret=new Vector<String>();
			File f=new File("goodpro.txt");
			FileReader freader=new FileReader(f);
			BufferedReader bufferreader=new BufferedReader(freader);
			String str=bufferreader.readLine();
			bufferreader.close();
			String array[]=str.split(" ");
			int i,j,k=0;
			for(i=0;i<array.length;i++)
			{
				if(k==10)
					break;
				for(j=0;j<personinfo.getNum();j++)
				{
					if(array[i].equals(personinfo.getProStruct(j).pronum))
						break;
				}
				if(j<personinfo.getNum())//用户已做该题
					continue;
				else
				{
					ret.addElement(array[i]);
					k++;
				}
					
			}
			return ret;
		}
}

package cm.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import cm.model.PersonInfo;
import cm.model.ProStruct;


public class SpiderUtils 
{
	//3大主要公共接口，第一个获取参数username的所有数据，返回一个PersonInfo类的对象
	public static PersonInfo getUsertInfo(String username) throws HttpException, IOException
	{
		PersonInfo personinfo=new PersonInfo();
		
		String personalurl=url+username;
		String html=getHtml(personalurl);
		
		//System.out.println(html);
		if(html.indexOf("No such user.") != -1)
			return null;
		personinfo.setUsername(getUserName(html));
		personinfo.setRank(getRank(html));
		personinfo.setRegistertime(getRegisterTime(html));
		personinfo.setTotalprosubmitted(getTotalprosubmitted(html));
		personinfo.setTotalprosolved(getTotalprosolved(html));
		personinfo.setTotalsubmissions(getTotalsubmissions(html));
		personinfo.setTotalaccepted(getTotalaccepted(html));
		personinfo.setUsercount(username);
		if(!personinfo.getTotalprosolved().equals("0"))
		{
			personinfo.num=getProStructList(username,personinfo.arrayprostruct);
		}
		return personinfo;
	}
	
	
	public static String[] getProfessionalList()throws HttpException,IOException
	{
		int pos,k = 0,I_form = 1;
		final int cas = 1001;
		final int num = 1025;
		String S_form,html;
		char ch;
		
		String[] url = new String[num];
		while(I_form <= cas)
		{
			
			S_form ="";
			S_form += I_form;
			String S_url = "http://acm.hdu.edu.cn/ranklist.php?from="+S_form;//通过form翻页
			html = getHtml(S_url);
			
			//截取账户信息，并用getUser截取账户名
			for(int i = 0;i != 25;i ++)
			{
				String tmp = new String();
				pos = html.indexOf("userstatus.php?user=");
				ch  = html.charAt(pos);
				while(ch != '"')
				{
					tmp += ch;
					pos ++;
					ch = html.charAt(pos);
				}
				
				url[k] = getUser(tmp);
				
				html = html.substring(pos);//一个页面中有多个账号，截完一个后舍弃前面所有字符串继续往下找
				k++;
			}
			I_form += 25;//一页有25个账号，通过+25翻页
		}
		return url;//返回一个包含有1025个账号名的数组
	}
	
	public static String[] getNeibours(String userName)throws HttpException,IOException
	{
		String[] user = new String[7];
		String html = getHtml("http://acm.hdu.edu.cn/userstatus.php?user="+userName);//用Spider类中的html爬去html源代码
		int beg = html.indexOf("Neighbours");
		int k = 0;
		char ch;
		while(beg != -1){
			beg = html.indexOf("userstatus.php?user=");
			String tmp = new String();
			if(beg == -1)
				return user;
			ch  = html.charAt(beg);
			while(ch != '"')
			{
				tmp += ch;
				beg ++;
				ch = html.charAt(beg);
			}
			html = html.substring(beg);
			user[k] = getUser(tmp);
			k++;
		}
		return user;//返回一个包含有7个邻居的user数组
	}
	
	//类的私有方法，用于实现上面的3大接口
	private final static String url="http://acm.hdu.edu.cn/userstatus.php?user=";
	
	private static String getHtml(String url) throws HttpException, IOException
	{
		HttpClient httpclient=new HttpClient();
		httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gb2312");
		
		//httpclient.getHostConfiguration().setProxy("58.215.52.141",8080);
		httpclient.getHostConfiguration().setProxy("61.163.106.38",8080);
//		httpclient.getHostConfiguration().setProxy("42.121.105.191",80);
		PostMethod postMethod = new PostMethod(url);
		
		NameValuePair[] postData = new NameValuePair[2];
		postData[0] = new NameValuePair("name","ainsophaur000"); 
		postData[1] = new NameValuePair("password","299792458");
		
		postMethod.addParameters(postData);
		int iState=httpclient.executeMethod(postMethod);
		

		String html=postMethod.getResponseBodyAsString();
		return html;
	}
	
	private static int getProStructList(String usercount,ProStruct prolist[]) throws HttpException, IOException
	{
		
		String pronum=new String();
		String protime=new String();
		String nexturl="http://acm.hdu.edu.cn/status.php?user="+usercount+"&status=5";
		String htmlString=new String();
		Map<String, String> map=new HashMap<String,String>();
		int k=0;
		while ((htmlString = getHtml(nexturl))!=null)
		{

			int nexturlstart, nexturlend;
			nexturlstart = htmlString.indexOf("Prev Page");
			nexturlstart = htmlString.indexOf("href", nexturlstart);
			if ((nexturlend = htmlString.indexOf("Next Page")) != -1)//如果有下一页
			{
				nexturl = "http://acm.hdu.edu.cn/"+ htmlString
						.substring(nexturlstart + 6, nexturlend - 2);//获取下一页的url

				int startindex = htmlString
						.indexOf("<tr align=center ><td height=22px>");
				int endindex = htmlString.indexOf(
						"</table><p align=center class=footer_link>",
						startindex);
				htmlString = htmlString.substring(startindex, endindex);
				while ((startindex = htmlString.indexOf("<td height=22px>")) != -1)
				{
					startindex=htmlString.indexOf("<td>", startindex);
					protime = htmlString.substring(startindex + 4,
							startindex + 23);
					startindex = htmlString.indexOf("</a>", startindex);
					pronum = htmlString.substring(startindex - 4, startindex);
					htmlString = htmlString.substring(startindex);
					if(map.get(pronum)!=null)
					{
						continue;
					}
					else 
					{
						map.put(pronum, protime);
						prolist[k].pronum=pronum;
						prolist[k++].protime=protime;
					}
				}
			}
			else 
			{
				int startindex = htmlString
						.indexOf("<tr align=center ><td height=22px>");
				if(startindex==-1)
				{
					System.out.println("zero num");
					break;
				}
				int endindex = htmlString.indexOf(
						"</table><p align=center class=footer_link>",
						startindex);
				htmlString = htmlString.substring(startindex, endindex);
				while ((startindex = htmlString.indexOf("<td height=22px>")) != -1)
				{
					startindex=htmlString.indexOf("<td>", startindex);
					protime = htmlString.substring(startindex + 4,
							startindex + 23);
					startindex = htmlString.indexOf("</a>", startindex);
					pronum = htmlString.substring(startindex - 4, startindex);
					htmlString = htmlString.substring(startindex);
					if(map.get(pronum)!=null)
					{
						continue;
					}
					else 
					{
						map.put(pronum, protime);
						prolist[k].pronum=pronum;
						prolist[k++].protime=protime;
					}
				}
				break;
			}
		}
		for(int i=0,j=k-1;i<=j;i++,j--)
		{
			String protimetemp="";
			String pronumtemp="";
			pronumtemp=prolist[i].pronum;
			protimetemp=prolist[i].protime;
			
			prolist[i].pronum=prolist[j].pronum;
			prolist[i].protime=prolist[j].protime;
			
			prolist[j].pronum=pronumtemp;
			prolist[j].protime=protimetemp;
			
		}
		
	
		
		String personalurl=url+usercount;
		String html=getHtml(personalurl);
		int substart=html.indexOf(">p(");
		int subend=html.indexOf(";<",substart);
		String str=html.substring(substart, subend);
		int len=0,j;
		while(str.length()!=1)
		{
			int index1,index2=0,index3=0,index4;
			index1=str.indexOf("(");
			index4=str.indexOf(")",index1);
			j=0;
			for(int i=index1+1;i<index4;i++)
			{
				if(str.charAt(i)==',' && j==0)
				{
					index2=i;j++;
				}
				else if(str.charAt(i)==',' && j==1)
				{
					index3=i;break;
				}
			}
			String pronumEx=str.substring(index1+1,index2);
			String proac=str.substring(index2+1,index3);
			String prosub=str.substring(index3+1,index4);
			
			for(int z=0;z<k;z++)
			{
				if(prolist[z].pronum.equals(pronumEx))
				{
					prolist[z].proac=proac;
					prolist[z].prosub=prosub;
				}
			}
			
			
			str=str.substring(index4);
		}
		return k;
	}
		
	
	
	private static String getProtime(String html)
	//html:http://acm.hdu.edu.cn/status.php?user=???&pid=题号tatus=5
	{
		int substart=html.indexOf("<td height=22px>");
		int subend=html.indexOf("</td><td><font color=red>Accepted");
		String str=html.substring(substart, subend);
		substart=str.indexOf("<td>")+4;
		str=str.substring(substart);
		return str;
	}
	
	private static String getUserName(String html)
	{
		if(html.indexOf("No such user." ) != -1)
			return null;
		int substart=html.indexOf("<h1 ");
		int subend=html.indexOf("</h1>");
		String str=html.substring(substart, subend);
		substart=str.indexOf("'>")+2;
		subend=str.indexOf("</a>");
		str=str.substring(substart, subend);
		return str;
	}
	
	private static String getRank(String html)
	{
		if(html.indexOf("No such user") != -1)
			return null;
		int substart=html.indexOf("<tr><td>Rank");
		int subend=html.indexOf("</td></tr>",substart);
		String str=html.substring(substart, subend);
		substart=str.indexOf("center>")+7;
		str=str.substring(substart);
		return str;
	}
	
	private static String getRegisterTime(String html)
	{
		int substart=html.indexOf(";registered on ");
		int subend=html.indexOf("</i>",substart);
		String str=html.substring(substart, subend);
		substart=str.indexOf("on ")+3;
		str=str.substring(substart);
		return str;
	}
	
	private static String getTotalprosubmitted(String html)
	{
		int substart=html.indexOf("Problems Submitted</td><td align=center>");
		int subend=html.indexOf("</td></tr>",substart);
		String str=html.substring(substart, subend);
		substart=str.indexOf("center>")+7;
		str=str.substring(substart);
		return str;
	}
	
	private static String getTotalprosolved(String html)
	{
		int substart=html.indexOf("Problems Solved</td><td align=center>");
		int subend=html.indexOf("</td></tr>",substart);
		String str=html.substring(substart, subend);
		substart=str.indexOf("center>")+7;
		str=str.substring(substart);
		return str;
	}
	
	private static String getTotalsubmissions(String html)
	{
		int substart=html.indexOf("Submissions</td><td align=center>");
		int subend=html.indexOf("</td></tr>",substart);
		String str=html.substring(substart, subend);
		substart=str.indexOf("center>")+7;
		str=str.substring(substart);
		return str;
	}
	
	private static String getTotalaccepted(String html)
	{
		int substart=html.indexOf("Accepted</td><td align=center>");
		int subend=html.indexOf("</td></tr>",substart);
		String str=html.substring(substart, subend);
		substart=str.indexOf("center>")+7;
		str=str.substring(substart);
		return str;
	}
	
	private static String getUser(String url)
	{
		int pos;
		String res = new String();
		pos = url.indexOf("=");
		pos ++;
		for(int i = pos;url.charAt(i) != '&';i ++)
		{
			res += url.charAt(i);
		}
		return res;
	}
	
	
	
	
}

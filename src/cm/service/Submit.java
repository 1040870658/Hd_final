/*******************************/
/*    Author:邓作恒                 */
/*    Date:2014/5/58                 */
/*    Version:1                         */
/******************************/

package cm.service;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


/*
0=G++
1=GCC
2=C++
3=C
4=Pascal
5=Java
 */

/*使用方法*/
/*先用用户名和密码构造一个submit对象*/
/*然后调用submitACode提交代码*/
/*然后getStatus()返回状态字符串*/
/*因为判题可能很慢所以需要设个循环，用getFlag()返回1作为跳出条件*/
/*参看Testsubmit.java*/

public class Submit 
{
	//数据成员
	private HttpClient httpclient;
	private String userName;
	private String userPw;
	private String status;
	private String errno;
	private int flag;
	private String PHPSESSID;
	
	private String submitLang;
	private String submitPid;
	private String submitCode;
	
	public Submit()
	{
		httpclient=new HttpClient();
		userName=new String();
		userPw=new String();
		status="登陆中";
		errno=new String();
		
		submitLang="0";
		flag=0;
		submitPid="1000";
		submitCode=new String();
	}
	
	public Submit(String _userName,String _userPw) throws HttpException, IOException
	{
		httpclient=new HttpClient();
		userName=_userName;
		userPw=_userPw;
		status="登陆中";
		errno=new String();
		
		submitLang="0";
		flag=0;
		submitPid="1000";
		submitCode=new String();
		
		login();
	}
	
	public void  login() throws HttpException, IOException
	{
		httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gb2312");
		
		//获取sessionid
		GetMethod getMethod=new GetMethod("http://acm.hdu.edu.cn/");
		httpclient.executeMethod(getMethod);
		Header hdr=getMethod.getResponseHeader("Set-Cookie");
	
		String strSesId=hdr.getValue().substring(0,36);
	
		PHPSESSID=strSesId.substring(10,36);
		PostMethod postMethod=new PostMethod("http://acm.hdu.edu.cn/userloginex.php?action=login");
		
		//设置各种header
		Header hdrPost=new Header();
		hdrPost.setName("Cookie");
		hdrPost.setValue(strSesId+"; bdshare_firstime=1400310554691; exesubmitlang=0");
		
		postMethod.addRequestHeader(hdrPost);
		postMethod.addRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
		postMethod.addRequestHeader("Referer","http://acm.hdu.edu.cn/");
		postMethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded");
		postMethod.addRequestHeader("Host","acm.hdu.edu.cn");
		//System.out.println(postMethod.getRequestHeader("Host").getValue());
		
		//设置要发送的表单信息
		NameValuePair[] postData = new NameValuePair[4];
		postData[0] = new NameValuePair("login","Sign In");
		postData[1] = new NameValuePair("username",userName);
		postData[2] = new NameValuePair("userpass",userPw);
		postData[3] = new NameValuePair("PHPSESSID",PHPSESSID);
		
		postMethod.addParameters(postData);
		
		//执行登录请求
		int iState=httpclient.executeMethod(postMethod);
		if(iState==302)
		{
			
			httpclient.executeMethod(getMethod);
			
			String strCheck=getMethod.getResponseBodyAsString();
			if(strCheck.indexOf(userName)==-1)
			{
				//System.out.println("登录失败");
				status="登录失败";
				errno=String.valueOf(iState);
			}
		}
		else
		{
			status="准备就绪";
		}
	
	}
	
	public void submitACode(String _code,int _lang,int _pid) throws HttpException, IOException
	{
		submitLang=String.valueOf(_lang);
		submitPid=String.valueOf(_pid);
		PostMethod postMethod2 = new PostMethod("http://acm.hdu.edu.cn/submit.php?action=submit");
		NameValuePair[] submitCode = new NameValuePair[4];
		submitCode[0]=new NameValuePair("check","0");
		submitCode[1]=new NameValuePair("language",submitLang);
		submitCode[2]=new NameValuePair("problemid",submitPid);
		submitCode[3]=new NameValuePair("usercode",_code);
		postMethod2.addParameters(submitCode);
		int pess=httpclient.executeMethod(postMethod2);
		if(302==pess)
		{
			status="提交中";
			errno=String.valueOf(pess);
		}
	}
	
	public String getStatus() throws HttpException, IOException
	{
		int slang=Integer.parseInt(submitLang);
		slang++;
		String statusLang=String.valueOf(slang);
		//submitLang.
		String strURL=new String("http://acm.hdu.edu.cn/status.php?first=&pid="+submitPid+"&user="+userName+"&lang="+statusLang+"&status=0");
		
		//GetMethod getMethod3 = new GetMethod("http://acm.hdu.edu.cn/status.php?first=&pid=1000&user=ain0&lang=0&status=0");
		GetMethod getMethod3 = new GetMethod(strURL);
		
		int iState=httpclient.executeMethod(getMethod3);
		if(iState!=200)
		{
			status="未知错误";
			flag=1;
			return status;
		}
		String rp3=getMethod3.getResponseBodyAsString();
		String mark="<option value='11'>Output Limit Exceeded</option></select></span> <input type=submit value=Go class=button40 style=\"height:22px;margin-top:-3px\"></center></form>";
		int bg3=rp3.indexOf(mark);
		String rp4=rp3.substring(bg3+mark.length(), bg3+mark.length()+230);
		if(rp4.indexOf("Queuing")!=-1)
		{
			status="Queuing";
			
		}
		else if(rp4.indexOf("Compiling")!=-1)
		{
			status="Compiling";
		}
		else if(rp4.indexOf("Running")!=-1)
		{
			status="Running";
		}
		else
		{
			if(rp4.indexOf("Compilation Error")!=-1)
			{
				status="Compilation Error";
				flag=1;
				
			}
			else if(rp4.indexOf("Runtime Error")!=-1)
			{
				
				status="Runtime Error";
				flag=1;
				
			}
			else if(rp4.indexOf("Wrong Answer")!=-1)
			{
				status="Wrong Answer";
				flag=1;
				
			}
			else if(rp4.indexOf("Accepted")!=-1)
			{
				status="Accepted";
				flag=1;
				
			}
			else if(rp4.indexOf("Memory Limit Exceeded")!=-1)
			{
				status="Memory Limit Exceeded";
				flag=1;
			}
			else if(rp4.indexOf("Time Limit Exceeded")!=-1)
			{
				status="Time Limit Exceeded";
				flag=1;
			}
			else if(rp4.indexOf("Output Limit Exceeded")!=-1)
			{
				status="Output Limit Exceeded";
				flag=1;
			}
			else if(rp4.indexOf("Output Limit Exceeded")!=-1)
			{
				status="Output Limit Exceeded";
				flag=1;
			}
			else if(rp4.indexOf("Presentation Error")!=-1)
			{
				status="Presentation Error";
				flag=1;
			}
		}
		return status;
	}
	
	public int getFlag()
	{
		return flag;
	}
	
	public String getErrno()
	{
		return errno;
	}
}

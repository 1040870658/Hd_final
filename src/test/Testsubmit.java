package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Test;

import cm.service.Submit;

public class Testsubmit {

	@Test
	public void test() throws HttpException, IOException {
		Submit  submit=new Submit("ain0","299792458");
		String strab="#include<iostream> \n using namespace std;int main(){int a,b;while(cin>>a>>b){cout<<a+b<<endl;}return 0;}";
		submit.submitACode(strab, 0,1000);
		int flag=submit.getFlag();
		while(flag==0)
		{
			System.out.println(submit.getStatus());
			flag=submit.getFlag();
		}
	}

}

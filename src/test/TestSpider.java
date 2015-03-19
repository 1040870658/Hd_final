package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.httpclient.HttpException;
import org.junit.Test;

import cm.utils.SpiderUtils;

public class TestSpider {

	@Test
	public void test() throws Exception {
		Scanner in = new Scanner(System.in);
		SpiderUtils spider = new SpiderUtils();
		String[] namelist = new String[1025];
		/*//牛人名单测试
		System.out.println("OxName Test:");
		namelist = spider.getProfessionalList();
		for(int i = 0;i != 1025;i ++)
		{
			System.out.println(namelist[i]);
		}
		*/
		String user;
		String neig[] = new String[7];
		while(in.hasNext())
		{
			user = in.next();
			neig = SpiderUtils.getNeibours(user);
			//相邻账号测试
			System.out.println("neigbours of "+ user +" are : ");
			for(int i = 0;i != 7;i ++)
			{
				System.out.println(neig[i]);
			}
			//获取person信息测试
			
		}	
	}

}

package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.httpclient.HttpException;
import org.junit.Test;

import cm.service.Function;

public class TestFunction {

	@Test
	public void test() throws Exception {
		Scanner in = new Scanner(System.in);
		String user;
		boolean honor[] = new boolean[15];
		int monthnum[] = new int[120];
		int accumnum[] = new int[120];
		String bool[] = new String[2];
		while(in.hasNext())
		{
			user = in.next();
			Function func = new Function(user);
			honor = func.getHoner();
			System.out.println("State of Honor:");
				//	成就测试
			for(int i = 0;i != 15;i ++)
			{
				System.out.println("Honor " + i +" :" + honor[i]);
			}
			int month = func.getMonth();
			monthnum = func.getMonthsProNum();
			accumnum = func.getAccumpronum();
			System.out.println("month record:");
			//每月做题测试
			for(int i = 0;i <= month;i ++)
			{
				System.out.println(i + " " + monthnum[i]);
			}
			//累计月做题测试
			System.out.println("accumulate record:");
			for(int i = 0;i <= month;i ++)
			{
				System.out.println(i + " " + accumnum[i]);
			}
		}
	}

}

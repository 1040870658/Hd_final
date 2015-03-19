package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.httpclient.HttpException;
import org.junit.Test;

import cm.model.PersonInfo;
import cm.model.ProStruct;
import cm.service.Function;

public class TestPersonInfo {

	@Test
	public void test() throws Exception {
		String user;
		PersonInfo person = new PersonInfo();
		Scanner in = new Scanner(System.in);
		while(in.hasNext())
		{
			user = in.next();
			Function func = new Function(user);
			person = func.personinfo;
			System.out.println("PersonInfo Test:");
			System.out.println("Rank:" + person.getRank());
			System.out.println("Name:" + person.getUsername());
			System.out.println("ACNUM:"+person.getNum());
			System.out.println("Registertime:" + person.getRegistertime());
			System.out.println("Totalsubmission:" + person.getTotalsubmissions());
			System.out.println("Totalaccepted:" + person.getTotalaccepted());
			System.out.println("Totalproblemsolved:" + person.getTotalprosolved());
			System.out.println("Problem num & time :");
			int num = person.getNum();
			for(int i = 0;i != num;i ++)
			{
				ProStruct pro = person.getProStruct(i);
				System.out.println(pro.pronum +" " +pro.protime);
			}
		}
	}

}

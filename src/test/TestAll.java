package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import cm.dao.ClassifyDao;
import cm.model.PersonInfo;
import cm.model.ProStruct;
import cm.service.Function;
import cm.utils.SpiderUtils;

public class TestAll {

	@Test
	public void test() throws Exception {

		String[] usertest = new String[4];
		usertest[0] = "1271001437";
		usertest[1] = "iceycoco";
		usertest[2] = "yc";
		usertest[3] = "bluebird";
		System.out.println("Classify Test:");
		Map<String, String> map = new HashMap<String, String>();
		map = ClassifyDao.getMap("默认分类方案");
		Iterator<String> iter = map.keySet().iterator();
		// 题目分类测试
		System.out.println("Problem sorted");
		String key;
		while (iter.hasNext()) {
			key = iter.next();
			System.out.print(map.get(key) + " : " + key);
		}

		System.out.println();

		for (int k = 0; k != 4; k++) {
			System.out.println("测试账号: " + usertest[k]);
			System.out.println("Function Test:");

			String user = new String(usertest[k]);
			boolean honor[] = new boolean[15];
			int monthnum[] = new int[120];
			int accumnum[] = new int[120];
			String bool[] = new String[2];
			// user = in.next();
			Function func = new Function(user);
			honor = func.getHoner();
			System.out.println("State of Honor:");
			// 成就测试
			for (int i = 0; i != 15; i++) {
				System.out.println("Honor " + i + " :" + honor[i]);
			}
			int month = func.getMonth();
			monthnum = func.getMonthsProNum();
			accumnum = func.getAccumpronum();
			System.out.println("month record:");
			// 每月做题测试
			for (int i = 0; i <= month; i++) {
				System.out.println(i + " " + monthnum[i]);
			}
			// 累计月做题测试
			System.out.println("accumulate record:");
			for (int i = 0; i <= month; i++) {
				System.out.println(i + " " + accumnum[i]);
			}

			PersonInfo person = new PersonInfo();
			person = func.personinfo;
			System.out.println("PersonInfo Test:");
			System.out.println("Rank:" + person.getRank());
			System.out.println("Name:" + person.getUsername());
			System.out.println("ACNUM:" + person.getNum());
			System.out.println("Registertime:" + person.getRegistertime());
			System.out.println("Totalsubmission:"
					+ person.getTotalsubmissions());
			System.out.println("Totalaccepted:" + person.getTotalaccepted());
			System.out.println("Totalproblemsolved:"
					+ person.getTotalprosolved());
			System.out.println("Problem num & time :");
			int num = person.getNum();
			for (int i = 0; i != num; i++) {
				ProStruct pro = person.getProStruct(i);
				System.out.println(pro.pronum + " " + pro.protime);
			}

			System.out.println("Spider Test:");
			SpiderUtils spider = new SpiderUtils();
			String neig[] = new String[7];

			neig = SpiderUtils.getNeibours(user);
			// 相邻账号测试
			System.out.println("neigbours of " + user + " are : ");
			for (int i = 0; i != 7; i++) {
				System.out.println(neig[i]);
			}

			System.out.println();
		}
	}

}

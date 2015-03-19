package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.junit.Test;

import cm.dao.ClassifyDao;

public class TestClassify {

	@Test
	public void test() {
		Map<String,String> map = new HashMap<String, String>();
		map = ClassifyDao.getMap("My分类");
		Iterator<String>iter = map.keySet().iterator();
		//题目分类测试
		System.out.println("Problem sorted");
		String key;
		while(iter.hasNext())
		{
			key = iter.next();
			System.out.println(key);
			System.out.println(map.get(key));
			
		}
	}/*
	@Test
	public void test2() {
		ClassifyDao.importClassify("C:\\Users\\Administrator\\Desktop\\5.txt");
		
	}*/

}

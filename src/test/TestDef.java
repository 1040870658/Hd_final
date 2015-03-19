package test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import cm.service.DefSetting;

public class TestDef {

	@Test
	public void test() {
		Vector<String> vec=DefSetting.getClassifyList();
		System.out.println(vec.firstElement());
		System.out.println(DefSetting.getDefUserAccount());
		System.out.println(DefSetting.getDefUserPw());
	
	}

}

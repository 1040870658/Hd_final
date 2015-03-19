package test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import cm.dao.ClassifyDao;
import cm.dao.PersonDao;
import cm.model.PersonInfo;

public class TestDao {

	@Test
	public void test() {
		PersonInfo p;
		PersonDao dao = new PersonDao();
		p = dao.search(2);
		if(p != null) {
			System.out.println(p.getUsercount() + " " + p.getUsername());
		}
	}

}

package test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.junit.Test;

import cm.service.Classify;

public class ClassifyEditTest {

	@Test
	public void test() {
		Vector<String> vec=Classify.getClassifyList();
		for(int i=0;i<vec.size();i++)
		{
			System.out.println(vec.elementAt(i));
		}
		Map<String,Vector<String> > map=Classify.getClassifyMap(vec.firstElement());
		Iterator<String> it=map.keySet().iterator();
		while(it.hasNext())
		{
			String key=it.next().toString();
			Vector<String> vecstr=map.get(key);
			System.out.println(key);
			for(int i=0;i<vecstr.size();i++)
			{
				System.out.print(vecstr.elementAt(i)+" ");
			}
		}
		
		System.out.println(Classify.getFileStrForEdit(vec.firstElement()));
		String strE="测试分类\n测试题型\n1002 2222 3333 4444";
		Classify.resetClassify(vec.elementAt(1),strE);
		System.out.println(Classify.getFileStrForEdit(vec.elementAt(1)));

	}

}

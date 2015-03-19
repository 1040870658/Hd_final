package cm.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import cm.dao.ClassifyDao;
import cm.model.ProStruct;


public class Classify 
{
	//获取分类方案列表，返回值是存着分类方案名的vector，分类方案名用于编辑和查找分类方案内容
	public static Vector<String> getClassifyList()
	{
		return ClassifyDao.getClassifyList();
	}
	//获取分类的数据结构，要传入一个分类方案名
	public static Map<String,Vector<String> > getClassifyMap(String clsName)
	{
		Map<String,String> allProMap=ClassifyDao.getMap(clsName);
		Map<String,Vector<String>> ret=new HashMap<String,Vector<String>>();
		
		Iterator it=allProMap.keySet().iterator();
		while(it.hasNext())
		{
			String key;
			String value;
			key=it.next().toString();
			value=allProMap.get(key);
			Vector<String> vec;
			vec=ret.get(value);
			if(vec==null)
			{
				ret.put(value,new Vector<String>());
				vec=ret.get(value);
			}
			vec.addElement(key);
			
		}
		return ret;
	
	}
	//获取用于编辑的字符串，编辑时直接把这字符串放到textarea就行，要传入一个分类方案名
	public static String getFileStrForEdit(String clsName)
	{
		return ClassifyDao.getFileStrForEdit(clsName);
	}
	//储存编辑结果，直接传入textarea拿到的字符串就行
	public static  void resetClassify(String clsName,String strClassify)
	{
		ClassifyDao.resetClassify(clsName,strClassify);
	}
	//导入分类，传入一个文件名即可
	public static void importClassify(String URL)
	{
		ClassifyDao.importClassify(URL);
	}
		
}

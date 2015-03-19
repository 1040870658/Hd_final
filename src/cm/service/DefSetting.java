package cm.service;

import java.util.Vector;

import cm.dao.ClassifyDao;
import cm.dao.DefaultDao;

public class DefSetting
{
	//获取默认账号
	public static String getDefUserAccount()
	{
		return DefaultDao.getDefUserAccount();
	}
	//获取默认账号的密码
	public static String getDefUserPw()
	{
		return DefaultDao.getDefUserPw();
	}
	//获取分类方案列表
	public static Vector<String> getClassifyList()
	{
		return ClassifyDao.getClassifyList();
	}
	//修改默认用户名和密码
	public static void setCount(String name, String password) {
		DefaultDao.write(name, password);
	}

}

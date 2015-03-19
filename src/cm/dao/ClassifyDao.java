package cm.dao;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import cm.utils.ClassifyUtils;

public class ClassifyDao {

	private static Map<String,String> map = new HashMap<String, String>();
	
	public static Map getMap() {
		ClassifyUtils.search(map);
		return map;
	}

	public static Map<String, String> getMap(String strClassifyName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("file/" + strClassifyName + ".txt"));
			if(br == null)
				return null;
			if(br.readLine() == null)
				return null;
			String key;
			Map<String, String> map = new HashMap<String, String>();
			while((key = br.readLine()) != null) {
				if(key.equals("")) {
					continue;
				}
				String value = br.readLine();
				if(value.equals("")) {
					continue;
				}
				String  strs[] = value.split(" " );
				for(String s : strs) {
					map.put(s, key); 
				}
			}
			br.close();
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Vector<String> getClassifyList() {
		File file = new File("file/default.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			br.readLine();
			br.readLine();
			Vector<String> vector = new Vector<String>();
			String str;
			while((str = br.readLine()) != null) {
				if(str.equals("")) {
					continue;
				}
				vector.add(str);
			}
			br.close();
			for(int i=0;i<vector.size();i++)
			{
				System.out.println(vector.elementAt(i));
			}
			return vector;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getFileStrForEdit(String clsName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("file/" + clsName + ".txt"));
			String str = "";
			String key;
			while((key = br.readLine()) != null) {
				if(key.equals("")) {
					continue;
				}
				str+= key;
				str+= "\n";
			}
			br.close();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void resetClassify(String clsName, String strClassify) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("file/" + clsName + ".txt"));
			bw.write(strClassify);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void importClassify(String uRL) {
		File file = new File(uRL);
		BufferedReader br;
		BufferedWriter bw;
		String str;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
			String filename = br.readLine();
			
			bw = new BufferedWriter(new FileWriter("file/" + filename + ".txt"));
			bw.write(filename);
			bw.newLine();
			while((str = br.readLine()) != null) {
				if(str.equals("")) {
					continue;
				}
				bw.write(str);
				bw.newLine();
			}
			bw.flush();
			bw.close();
			br.close();
			//更新default文件
			br = new BufferedReader(new FileReader("file/default.txt"));
			br.readLine();
			br.readLine();
			
			while((str = br.readLine()) != null) {
				if(str.equals(filename)){
					br.close();
					return;
				}
			}
			bw = new BufferedWriter(new FileWriter("file/default.txt",true));
			bw.newLine();
			bw.write(filename);
			System.out.println(filename);
			bw.flush();
			br.close();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

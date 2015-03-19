package cm.utils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ClassifyUtils {

	public static void search(Map<String, String> map) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("file/tile.txt"));
			String s,s2;
			while((s = br.readLine()) != null) {
				s2 = br.readLine();
				String[] str = s2.split(" ");
				for(String ss : str) {
					map.put(ss, s);
				}
			}
			br.close(); // 最后记得关闭文件 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

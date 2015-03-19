package cm.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class DefaultDao {

	public static void write(String name, String password) {
		File file = new File("file/default.txt");
		BufferedWriter bw;
		BufferedReader br;
		String str,temp;
		try {
			
			br = new BufferedReader(new FileReader("file/default.txt"));
			br.readLine();
			br.readLine();
			str = name + "\n";
			
			str += password;
			str+= "\n";
			while((temp = br.readLine()) != null) {
				str += temp;
				str += "\n";
			}
			
			bw = new BufferedWriter(new FileWriter("file/default.txt"));
			bw.write(str);
			bw.flush();
			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String getDefUserAccount() {
		BufferedReader br;
		String str;
		try {
			br = new BufferedReader(new FileReader("file/default.txt"));
			str = br.readLine();
			br.close();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getDefUserPw() {
		BufferedReader br;
		String str;
		try {
			br = new BufferedReader(new FileReader("file/default.txt"));
			br.readLine();
			str = br.readLine();
			br.close();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

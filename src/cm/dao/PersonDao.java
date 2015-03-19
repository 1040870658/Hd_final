package cm.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import cm.model.PersonInfo;
import cm.model.ProStruct;
import cm.utils.RankUtils;



public class PersonDao {
	public void add(PersonInfo p) {
		RankUtils.addRank(p);
		File writename = new File("file/" + p.getUsercount() + ".txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
		try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			
			out.write(p.getUsercount() + "," + p.getUsername() + "," + p.getRegistertime() + "," + p.getNum() + "," + p.getRank()); // \r\n即为换行  
			out.write("," + p.getTotalaccepted() + "," + p.getTotalprosolved() + "," + p.getTotalprosubmitted() +","+ p.getTotalsubmissions());
			out.newLine();
			for(int i = 0; i < p.getNum(); ++i) {
				ProStruct pro = p.getProStruct(i);
				out.write(pro.proac + "," + pro.pronum + "," + pro.prosub + "," + pro.protime + ",");
			}
			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件 
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public PersonInfo search(String userCount) {
		File f = new File("file/" + userCount + ".txt"); 
		if(!f.exists()) {
			return null;
		}
        BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			String line = "";  
			line = br.readLine();  
			PersonInfo per = new PersonInfo();
			String [] str1 = line.split(",");
			per.setUsercount(str1[0]);
			per.setUsername(str1[1]);
			per.setRegistertime(str1[2]);
			per.setNum(Integer.parseInt(str1[3]));
			per.setRank(str1[4]);
			per.setTotalaccepted(str1[5]);
			per.setTotalprosolved(str1[6]);
			per.setTotalprosubmitted(str1[7]);
			per.setTotalsubmissions(str1[8]);
			
			line = br.readLine(); // 一次读入一行数据  
			str1 = line.split(",");
			for(int i = 0, j = 0; i < str1.length; i += 4, j++) {
				ProStruct p = per.getProStruct(j);
				p.proac = str1[i];
				p.pronum = str1[i + 1];
				p.prosub = str1[i + 2];
				p.protime = str1[i + 3];
			}
			
			br.close();
			return per;
		} catch (Exception e) {
			return null;
		} 
	}
	public PersonInfo search(int rank) {
		 try {
	    	 File f = new File("file/RANK.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
	    	 BufferedReader br = new BufferedReader(new FileReader(f)); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
	    	 String line = "";  
			 while((line = br.readLine()) != null) {
				String str[] = line.split(",");
				if(rank == Integer.parseInt(str[0])) {
					return search(str[1]);
				}
			 }
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;  
	}
}

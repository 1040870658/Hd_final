package cm.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import cm.model.PersonInfo;

public class RankUtils {

	public static void addRank(PersonInfo p) {  
        BufferedWriter out = null;  
        try {  
            out = new BufferedWriter(new OutputStreamWriter(  
                    new FileOutputStream("file/RANK.txt", true)));  
            out.write(p.getRank() + "," + p.getUsercount());  
            out.newLine();
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                out.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}

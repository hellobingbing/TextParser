package cn.free.textparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
public class total {

	/**
	 * @param args 有空行的情况  readLine
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
		String dir = ".\\test\\Sampless\\train_400\\5000features\\CHI5000.txt";
		String aa = "";
		String str = "";
		String str1[];
		FileInputStream fis = new FileInputStream(dir);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		Vector <String> vector = new Vector <String>();
		
		while((aa = br.readLine()) != null){
			 str += aa;

		}
		br.close();
		isr.close();
		fis.close();
		//System.out.println(str);
		str1 = str.split("\\s+");
		for(int i = 0;i < str1.length;i++){
			vector.add(str1[i]);
		}
		System.out.println(str1.length);
		System.out.println(vector.size());
		
		//System.gc();
		
		} catch(Exception ex) {
			System.out.println("hehe");
		}

	}

}

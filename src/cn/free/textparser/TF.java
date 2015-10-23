package cn.free.textparser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;


public class TF {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		String path = ".\\text\\terms.txt";
		String dirname = ".\\text\\texts";
		File dir = new File(dirname);
		File file[] = dir.listFiles();
		
		Vector<String> vector = new Vector<String>();
		//Vector<Integer> wordCount = new Vector<Integer>();
		
		FileReader reader = new FileReader(new File(path));
		BufferedReader bufferReader = new BufferedReader(reader);
		String hasRead = "";
		while ((hasRead = bufferReader.readLine()) != null) {
				String info[] = hasRead.split("\\s+");
				//Vector<String> vector = new Vector<String>();
				for (int i = 0; i < info.length; i++) {
					vector.add(info[i]);
				}
				System.out.print(vector);
		}
		
		for(int k = 0;k<file.length;k++){
			FileInputStream fis2 = new FileInputStream(file[k]);
			InputStreamReader isr2 = new InputStreamReader(fis2);
		    BufferedReader br2 = new BufferedReader(isr2);
		    String hasread = "";
		    String filestr[] = null;
		    while ((hasread = br2.readLine()) != null){
		    	filestr = hasread.split(" "); 
		    	}
		    Vector<Integer> wordCount = new Vector<Integer>();//
		    
		    for (int i = 0; i < vector.size(); i++) {
		    	// 存放每个样本中的词语，在该对比文本中出现的次数
		    	String word = vector.get(i);
			    int w = 0;
			    //String str = content.replaceAll(word, null);
			
			    //String str = filestr.replaceAll(word, null);
			    //int count = content.length() - str.length();
			    //wordCount.add(i, count);
			    //System.out.println(str);
			    for(int j = 0;j<filestr.length;j++){
			    	if(filestr[j].equals(word)){
			    		w++;
			    		//
			    		}
			    	//
			    	}
			    wordCount.add(i, w);//
			    }
		    System.out.print("\n");
		    System.out.print(wordCount);
		    }
		
		
	
	} catch (Exception ex) {
		
	}
		
	}
    /*
	public static int getCharInStringCount(String content, String word) {
		String str = content.replaceAll(word, "");
		return (content.length() - str.length()) / word.length();
		
	}
	*/
}

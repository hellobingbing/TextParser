package cn.free.textparser.segandfilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReadFiles {

    private static List<String> fileList = new ArrayList<String>();
    private static HashMap<String, HashMap<String, Float>> allTheTf = new HashMap<String, HashMap<String, Float>>();
    private static HashMap<String, HashMap<String, Integer>> allTheNormalTF = new HashMap<String, HashMap<String, Integer>>();
    private static HashSet<String> stop_words_set=new HashSet<String>();
    
    static {

    	try{
    		/*
    		InputStreamReader is = new InputStreamReader(new FileInputStream("stop_words/stop_words.txt"), "UTF-8");
    		BufferedReader br = new BufferedReader(is);
    		String line = br.readLine();
    		while (line != null) {
    			stop_words_set.add(line);
    			line = br.readLine();
    		}
    		br.close();
    		*/
    		File file = new File("." + File.separator + "stop_words" + File.separator +
    				"stopwordslist.txt");
    		InputStream is = new FileInputStream(file);
    		Scanner scan = new Scanner(is,"utf8");
    		while(scan.hasNextLine()){
    			stop_words_set.add(scan.nextLine());
    		}
    		scan.close();
    		is.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    public static void main(String args[]) throws IOException{
    	
    	for(String word : stop_words_set){
    		System.out.println(word);
    		
    	}
    	
    }
    
    public static List<String> readDirs(String filepath) throws FileNotFoundException, IOException {
        try {
            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("filepath: " + file.getAbsolutePath());
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        //System.out.println("filepath: " + readfile.getAbsolutePath());
                    	if(readfile.getName().contains("all.txt")){
                    		fileList.add(readfile.getAbsolutePath());
                    	}
                        
                    } else if (readfile.isDirectory()) {
                        readDirs(filepath + "\\" + filelist[i]);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return fileList;
    }

    public static String readFiles(String file) throws FileNotFoundException, IOException {
        StringBuffer sb = new StringBuffer();
        InputStreamReader is = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader br = new BufferedReader(is);
        String line = br.readLine();
        while (line != null) {
            sb.append(line).append("\r\n");
            line = br.readLine();
        }
        br.close();
        return sb.toString();
    }

    public static String[] cutWord(String file) throws IOException {
        String[] cutWordResult = null;
        String text = ReadFiles.readFiles(file);
        //MMAnalyzer analyzer = new MMAnalyzer();
        //System.out.println("file content: "+text);
        //System.out.println("cutWordResult: "+analyzer.segment(text, " "));
        //String tempCutWordResult = analyzer.segment(text, " ");
        cutWordResult = text.split(" ");
        LinkedList<String> main_words=new LinkedList<String>();
        for(String word:cutWordResult){
        	if(word.length()>1&&!stop_words_set.contains(word)){
        		main_words.add(word);
        	}
        }
        return main_words.toArray(new String[0]);
    }
    
    public static String[] filterWord(List<String> cutWordResult) throws IOException {
        LinkedList<String> main_words=new LinkedList<String>();
        for(String word:cutWordResult){
        	if(word.length()>1&&!stop_words_set.contains(word)){
        		main_words.add(word);
        	}
        }
        return main_words.toArray(new String[0]);
    }
    
    public static String[] filterWord(String[] cutWordResult) {
        LinkedList<String> main_words=new LinkedList<String>();
        for(String word:cutWordResult){
        	if(word.length()>1&&!stop_words_set.contains(word)){
        		main_words.add(word);
        	}
        }
        return main_words.toArray(new String[0]);
    }

    public static HashMap<String, Float> tf(String[] cutWordResult) {
        HashMap<String, Float> tf = new HashMap<String, Float>();
        int wordNum = cutWordResult.length;
        int wordtf = 0;
        for (int i = 0; i < wordNum; i++) {
            wordtf = 0;
            for (int j = 0; j < wordNum; j++) {
                if (cutWordResult[i] != " " && i != j) {
                    if (cutWordResult[i].equals(cutWordResult[j])) {
                        cutWordResult[j] = " ";
                        wordtf++;
                    }
                }
            }
            if (cutWordResult[i] != " ") {
                tf.put(cutWordResult[i], (new Float(++wordtf)) / wordNum);
                cutWordResult[i] = " ";
            }
        }
        return tf;
    }

    public static HashMap<String, Integer> normalTF(String[] cutWordResult) {
        HashMap<String, Integer> tfNormal = new HashMap<String, Integer>();
        int wordNum = cutWordResult.length;
        int wordtf = 0;
        for (int i = 0; i < wordNum; i++) {
            wordtf = 0;
            if (cutWordResult[i] != " ") {
                for (int j = 0; j < wordNum; j++) {
                    if (i != j) {
                        if (cutWordResult[i].equals(cutWordResult[j])) {
                            cutWordResult[j] = " ";
                            wordtf++;

                        }
                    }
                }
                tfNormal.put(cutWordResult[i], ++wordtf);
                cutWordResult[i] = " ";
            }
        }
        return tfNormal;
    }

    public static Map<String, HashMap<String, Float>> tfOfAll(String dir) throws IOException {
        List<String> fileList = ReadFiles.readDirs(dir);
        for (String file : fileList) {
            HashMap<String, Float> dict = new HashMap<String, Float>();
            dict = ReadFiles.tf(ReadFiles.cutWord(file));
            allTheTf.put(file, dict);
        }
        return allTheTf;
    }

    public static Map<String, HashMap<String, Integer>> NormalTFOfAll(String dir) throws IOException {
        List<String> fileList = ReadFiles.readDirs(dir);
        for (int i = 0; i < fileList.size(); i++) {
            HashMap<String, Integer> dict = new HashMap<String, Integer>();
            dict = ReadFiles.normalTF(ReadFiles.cutWord(fileList.get(i)));
            allTheNormalTF.put(fileList.get(i), dict);
        }
        return allTheNormalTF;
    }

    public static Map<String, Float> idf(String dir) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        Map<String, Float> idf = new HashMap<String, Float>();
        List<String> located = new ArrayList<String>();

        float Dt = 1;
        float D = allTheNormalTF.size();
        List<String> key = fileList;
        Map<String, HashMap<String, Integer>> tfInIdf = allTheNormalTF;

        for (int i = 0; i < D; i++) {
            HashMap<String, Integer> temp = tfInIdf.get(key.get(i));
            for (String word : temp.keySet()) {
                Dt = 1;
                if (!(located.contains(word))) {
                    for (int k = 0; k < D; k++) {
                        if (k != i) {
                            HashMap<String, Integer> temp2 = tfInIdf.get(key.get(k));
                            if (temp2.keySet().contains(word)) {
                                located.add(word);
                                Dt = Dt + 1;
                                continue;
                            }
                        }
                    }
                    idf.put(word, Log.log((1 + D) / Dt, 10));
                }
            }
        }
        return idf;
    }

    public static Map<String, ArrayList<Map.Entry<String, Float>>> tfidf(String dir) throws IOException {

        Map<String, Float> idf = ReadFiles.idf(dir);
        Map<String, HashMap<String, Float>> tf = ReadFiles.tfOfAll(dir);
        Map<String, ArrayList<Map.Entry<String, Float>>> tfidf_result=new HashMap<String, ArrayList<Map.Entry<String,Float>>>(); 

        for (String file : tf.keySet()) {
            HashMap<String, Float> singelFile = tf.get(file);
            for (String word : singelFile.keySet()) {
                singelFile.put(word, (idf.get(word)) * singelFile.get(word));
            }
       
            ArrayList<Map.Entry<String, Float>> infoIds = new ArrayList<Map.Entry<String, Float>>(singelFile.entrySet());
    		Collections.sort(infoIds, new Comparator<Map.Entry<String, Float>>() {
    			public int compare(Map.Entry<String, Float> o1,
    					Map.Entry<String, Float> o2) {
    				float t= o1.getValue()-o2.getValue();
    				if(t>0){
    					return -1;
    				}else if(t<0){
    					return 1;
    				}else{
    					return 0;
    				}
    			}
    		});
    		tfidf_result.put(file, infoIds);
        }
        
        return tfidf_result;
    }

	public static HashSet<String> getStop_words_set() {
		return stop_words_set;
	}
    
    
}

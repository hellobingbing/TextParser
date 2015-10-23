package cn.free.textparser.features;

//import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class AllWords {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*
		List<String> allWords = new ArrayList<String>();
		allWords = getAllWords();
		for(String word : allWords){
			System.out.println(word);
			
		}
		outputAllWords(allWords);
		*/
		long start = System.currentTimeMillis();
		Map<String, Double> allWordsAndDF = new HashMap<String, Double>();
		allWordsAndDF = getAllWordsAndDF();
		outputAllWordsAndDF(allWordsAndDF);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		
	}
	/**
	 * ���ں����ؼ��ʵķ���
	 * @return ���¼����в��ظ��Ĵ���
	 * @throws IOException
	 */
	public static List<String> getAllWords() throws IOException{
		List<String> allWordsList = null;
		allWordsList = new ArrayList<String>();
		
		File dirFiles = new File("." + File.separator + "description" + File.separator +
				"seged" + File.separator + "exhibition");
		File[] files = dirFiles.listFiles();
		InputStream inputStream = null;
		//InputStreamReader isr = null;
		//BufferedReader br = null;
		Scanner scan = null;
		
		for(int i = 0;i<files.length;i++){
			
			String lines = null;
			StringBuffer sb = new StringBuffer();
			inputStream = new FileInputStream(files[i]);
			scan = new Scanner(inputStream,"utf8");
			//isr = new InputStreamReader(inputStream);
			//br = new BufferedReader(isr);
			while(scan.hasNextLine()){
				sb.append(scan.nextLine());
				//sb.append(" ");	
			}
			lines = sb.toString();
			String words[] = lines.split("\\s+");
			
			for(int j = 0;j<words.length;j++){
				//allWordList��һ����
				if(i == 0 && j == 0){
					allWordsList.add(words[j]);
					continue;
				}
				
				for(int k = 0;k < allWordsList.size();k++){
					//if�´ʲ���ԭ������
					if((!allWordsList.get(k).equals(words[j])) && (k == allWordsList.size() - 1)){
						allWordsList.add(words[j]);
						break;
					//if�´���ԭ������
					}else if(allWordsList.get(k).equals(words[j])){
						break;
					}
				}
			}		
		}
		//br.close();
		//isr.close();
		scan.close();
		inputStream.close();
		
		return allWordsList;
	}
	
	public static Map<String,Double> getAllWordsAndDF() throws IOException{
		Map<String,Double> allWordsAndDF = null;
		allWordsAndDF = new HashMap<String,Double>();
		
		File dirFiles = new File("." + File.separator + "description" + File.separator +
				"seged" + File.separator + "exhibition");
		File[] files = dirFiles.listFiles();
		InputStream inputStream = null;
		//InputStreamReader isr = null;
		//BufferedReader br = null;
		Scanner scan = null;
		
		for(int i = 0;i<files.length;i++){
			
			String lines = null;
			StringBuffer sb = new StringBuffer();
			Map.Entry<String, Double> me = null;
			Set<String> partWords = new HashSet<String>();
			Iterator<String> iterator = null;
			inputStream = new FileInputStream(files[i]);
			scan = new Scanner(inputStream,"utf8");
			//isr = new InputStreamReader(inputStream);
			//br = new BufferedReader(isr);
			while(scan.hasNextLine()){
				sb.append(scan.nextLine());
				//sb.append(" ");	
			}
			lines = sb.toString();
			String words[] = lines.split("\\s+");
			for(String word : words){
				partWords.add(word);
			}
			iterator = partWords.iterator();
			
			int j = 0;// ���ĵ��в��ظ��ĵڼ�����
			while(iterator.hasNext()){
				String temp = iterator.next();
				//��һ����
				if(i == 0 && j == 0){
					allWordsAndDF.put(temp, 1.0d);
					j++;
					continue;
				}
				int k = 0;// allWordsAndDF�еڼ�����
				while(allWordsAndDF.entrySet().iterator().hasNext()){
					me = allWordsAndDF.entrySet().iterator().next();
					if((!me.getKey().equals(temp)) && (k == allWordsAndDF.size() - 1)){
						allWordsAndDF.put(temp, 1.0d);
					}else if(me.getKey().equals(temp)){
						me.setValue(me.getValue() + 1);
						
					}
					k++;
				}
				j++;
			}
		}
		//br.close();
		//isr.close();
		scan.close();
		inputStream.close();
		
		return allWordsAndDF;
	}
	
	public static void outputAllWords(List<String> allWords) throws IOException{
		File file = new File("." + File.separator + "description" + File.separator +
				"seged" + File.separator + "allwords_exhibition.txt");
		OutputStream output = new FileOutputStream(file);
		for(String word : allWords){
			output.write(word.getBytes());
			output.write("\r\n".getBytes());
		}
		output.close();		
	}
	
	public static void outputAllWordsAndDF(Map<String, Double> allWordsAndDF)throws IOException{
		File file = new File("." + File.separator + "description" + File.separator +
				"seged" + File.separator + "allwordsDF_exhibition.txt");
		OutputStream outputStream = new FileOutputStream(file);
		while(allWordsAndDF.entrySet().iterator().hasNext()){
			Map.Entry<String, Double> me = allWordsAndDF.entrySet().iterator().next();
			outputStream.write(me.toString().getBytes());
			outputStream.write("\r\n".getBytes());
		}
		outputStream.close();
	}

}

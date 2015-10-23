package cn.free.textparser.features;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Statistics {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub

		long start = System.currentTimeMillis();
		/*
		List<String> allWords = new ArrayList<String>();
		Map<String, Double> dfMap = new HashMap<String, Double>();
		Map<String, Double> tfMap = new HashMap<String, Double>();
		Map<String, Double> tfdfMap = new HashMap<String, Double>();
		allWords = inputAllWords();

		dfMap = getDFMap(allWords);
		tfMap = getTFMap(allWords);
		tfdfMap = getTFDFMap(tfMap, dfMap);
		
		List<Map.Entry<String, Double>> sortedList_DF = new ArrayList<Map.Entry<String,Double>>();
		List<Map.Entry<String, Double>> sortedList_TFDF = new ArrayList<Map.Entry<String,Double>>();
		List<Map.Entry<String, Double>> sortedList_TF = new ArrayList<Map.Entry<String,Double>>();
		sortedList_DF = Sort.sortMap(dfMap);
		sortedList_TFDF = Sort.sortMap(tfdfMap);
		sortedList_TF = Sort.sortMap(tfMap);
		File file0 = new File("." + File.separator + "description" + File.separator +
				"seged" + File.separator + "DF.txt");
		File file1 = new File("." + File.separator + "description" + File.separator +
				"seged" + File.separator + "TFDF.txt");
		File file2 = new File("." + File.separator + "description" + File.separator +
				"seged" + File.separator + "TF.txt");
		OutputStream outputStream0 = new FileOutputStream(file0);
		OutputStream outputStream1 = new FileOutputStream(file1);
		OutputStream outputStream2 = new FileOutputStream(file2);	
		for(Map.Entry<String, Double> me : sortedList_DF){
			//System.out.println(me.toString());
			outputStream0.write(me.toString().getBytes());
			outputStream0.write("\r\n".getBytes());
		}
		
		for(Map.Entry<String, Double> me : sortedList_TFDF){
			//System.out.println(me.toString());
			outputStream1.write(me.toString().getBytes());
			outputStream1.write("\r\n".getBytes());
		}
		for(Map.Entry<String, Double> me : sortedList_TF){
			//System.out.println(me.toString());
			outputStream2.write(me.toString().getBytes());
			outputStream2.write("\r\n".getBytes());
		}
		
		outputStream0.close();
		outputStream1.close();
		outputStream2.close();
		*/
		String srcPath = "." + File.separator + "description" + File.separator +
				"seged" + File.separator + "science";
		String desPath = "." + File.separator + "description" + File.separator +
				"seged";
		getTCMapFromSingleFile(srcPath, desPath);
		
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		
		
	}
	/**
	 * 
	 * @param allWords
	 * @return 词频
	 */
	public static Map<String, Double> getTFMap(List<String> allWords) throws IOException{
		Map<String, Double> tfMap = new HashMap<String, Double>();
		File dirFiles = new File("." + File.separator + "description" + File.separator +
				"seged" + File.separator + "exhibition");
		File[] files = dirFiles.listFiles();
		Scanner scan = null;
		
		for(int i = 0;i<allWords.size();i++){
			double sumTF = 0.0f;
			for(int j = 0;j<files.length;j++){
				double partTF = 0.0f;
				int count = 0;
				StringBuffer buf = new StringBuffer();
				String lines = null;
				scan = new Scanner(files[j],"utf8");
				while(scan.hasNextLine()){
					buf.append(scan.nextLine());
				}
				lines = buf.toString();
				String[] words = lines.split("\\s+");
				for(int k = 0;k<words.length;k++){
					if(allWords.get(i).equals(words[k])){
						count++;
					}
				}
				partTF = count*1.0/words.length;
				sumTF += partTF;
			}
			tfMap.put(allWords.get(i), sumTF);
		}
		
		scan.close();
		return tfMap;
	}
	
	public static void getTCMapFromSingleFile(String srcPath,String desPath) throws IOException{
		List<File> filesList = new NonRepeatWords().getFileSortByName(srcPath);
		List<String> allWords = null;
		Scanner scan = null;
		File fileTop = new File(desPath + File.separator + "Top5TC_science.txt");
		OutputStream output = new FileOutputStream(fileTop);
		int index = 1;
		for(int i = 0;i<filesList.size();i++){
			File file = new File(desPath + File.separator + 
					"science_TC" + File.separator + index + ".txt");
			OutputStream outputStream = new FileOutputStream(file);
			Map<String, Double> tcMap = new HashMap<String, Double>();
			List<Map.Entry<String, Double>> tcSorted = new ArrayList<Map.Entry<String,Double>>();
			allWords = new NonRepeatWords().getWordsFromOneFile(filesList.get(i));
			for(int j = 0;j<allWords.size();j++){
				double TC = 0.0f;
				StringBuffer buf = new StringBuffer();
				String lines = null;
				scan = new Scanner(filesList.get(i),"GBK");
				while(scan.hasNextLine()){
					buf.append(scan.nextLine());
				}
				lines = buf.toString();
				String[] words = lines.split("\\s+");
				for(int k = 0;k<words.length;k++){
					if(allWords.get(j).equals(words[k])){
						TC++;
					}
				}
				tcMap.put(allWords.get(j), TC);
			}
			tcSorted = Sort.sortMap(tcMap);
			for(Map.Entry<String, Double> me : tcSorted){
				outputStream.write(me.toString().getBytes());
				outputStream.write("\r\n".getBytes());
			}
			outputStream.close();
			// top5 or 0-4
			if(tcSorted.isEmpty()){
				output.write("null".getBytes());
				output.write("\r\n".getBytes());
				
			}else if(tcSorted.size() < 5 && tcSorted.size() > 0){
				for(Map.Entry<String, Double> me : tcSorted){
					output.write(me.getKey().getBytes());
					output.write(",".getBytes());
				}
				output.write("\r\n".getBytes());
			}else{
				for(int z = 0;z<5;z++){
					output.write(tcSorted.get(z).getKey().getBytes());
					output.write(",".getBytes());
				}
				output.write("\r\n".getBytes());
			}
			index++;
		}
		output.close();
		scan.close();
		
	}
	
	/**
	 * 
	 * @param allWords
	 * @return 文档频率
	 */
	public static Map<String, Double> getDFMap(List<String> allWords) throws IOException{
		Map<String, Double> dfMap = new HashMap<String, Double>();
		File dirFiles = new File("." + File.separator + "description" + File.separator +
				"seged" + File.separator + "exhibition");
		File[] files = dirFiles.listFiles();
		InputStream inputStream = null;
		Scanner scan = null;
		for(int i = 0;i<allWords.size();i++){
			double count = 0.0d;
			for(int j = 0;j<files.length;j++){
				String lines = null;
				StringBuffer buf = new StringBuffer();
				inputStream = new FileInputStream(files[j]);
				scan = new Scanner(inputStream,"utf8");
				while(scan.hasNextLine()){
					buf.append(scan.nextLine());
				}
				lines = buf.toString();
				String[] words = lines.split("\\s+");
				for(int k = 0;k<words.length;k++){
					if(allWords.get(i).equals(words[k])){
						count++;
						break;
					}
				}
				
			}
			dfMap.put(allWords.get(i), count);
		}
		inputStream.close();
		scan.close();
		return dfMap;
	}
	/**
	 * 
	 * @param allWords
	 * @return tf*df
	 */
	public static Map<String, Double> getTFDFMap(Map<String, Double> tfMap,Map<String, Double> dfMap){
		Map<String, Double> tfdfMap = new HashMap<String, Double>();
		Set<Map.Entry<String, Double>> tfSet = tfMap.entrySet();
		Set<Map.Entry<String, Double>> dfSet = dfMap.entrySet();
		Iterator<Map.Entry<String, Double>> tfIter = tfSet.iterator();
		Iterator<Map.Entry<String, Double>> dfIter = dfSet.iterator();
		while(tfIter.hasNext() && dfIter.hasNext()){
			Map.Entry<String, Double> me1 = tfIter.next();
			Map.Entry<String, Double> me2 = dfIter.next();
			if(me1.getKey().equals(me2.getKey())){
				tfdfMap.put(me1.getKey(), me1.getValue()*me2.getValue());
			}
		}
		
		return tfdfMap;
	}
	/**
	 * 
	 * @param allWords
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Double> getDFMapMoreThanN(List<String> allWords) throws IOException{
		Map<String, Double> dfMap = new HashMap<String, Double>();
		File dirFiles = new File("." + File.separator + "description" + File.separator +
				"seged" + File.separator + "exhibition");
		File[] files = dirFiles.listFiles();
		InputStream inputStream = null;
		Scanner scan = null;
		for(int i = 0;i<allWords.size();i++){
			double count = 0.0d;
			for(int j = 0;j<files.length;j++){
				String lines = null;
				StringBuffer buf = new StringBuffer();
				inputStream = new FileInputStream(files[j]);
				scan = new Scanner(inputStream,"utf8");
				while(scan.hasNextLine()){
					buf.append(scan.nextLine());
				}
				lines = buf.toString();
				String[] words = lines.split("\\s+");
				for(int k = 0;k<words.length;k++){
					if(allWords.get(i).equals(words[k])){
						count++;
						break;
					}
				}
				// 判断文档频率DF是否>3
				if(count > 1000){
					break;
				}else{
					continue;
				}
			}
			// 只取DF大于3的词
			if(count > 1000){
				dfMap.put(allWords.get(i), count);
			}		
		}
		inputStream.close();
		scan.close();
		return dfMap;
	}
	
	public static List<String> inputAllWords() throws IOException{
		List<String> allWords = new ArrayList<String>();
		File file = new File("." + File.separator + "description" + File.separator +
				"seged" + File.separator + "partwords_exhibition.txt");
		Scanner scan = new Scanner(file,"GBK");
		while(scan.hasNextLine()){
			allWords.add(scan.nextLine());
		}
		scan.close();
		return allWords;
	}

}

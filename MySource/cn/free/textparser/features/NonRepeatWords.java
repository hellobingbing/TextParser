package cn.free.textparser.features;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class NonRepeatWords {
	
	private List<String> allwords = null;
	private List<File> allFiles = null;

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub

		
		List<File> files = new ArrayList<File>();
		String filePath = "." + File.separator + "description" + File.separator +
				"seged" + File.separator + "sports";
		files = new NonRepeatWords().getFileSortByName(filePath);
		for(File file : files){
			System.out.println(file.getName());
		}
		
		
	}

	/**
	 * all words of a single file
	 * @return all words list
	 * @throws IOException
	 */
	public List<String> getWordsFromOneFile() throws IOException{
		this.allwords = new ArrayList<String>();
		File file = new File("." + File.separator + "stopwordslist.txt");
		Scanner scan = new Scanner(file,"utf8");
		String word = null;
		this.allwords.add(scan.nextLine());
		while(scan.hasNextLine()){
			word = scan.nextLine();
			for(int i = 0;i<this.allwords.size();i++){
				if(!(this.allwords.get(i).equals(word)) && (i == this.allwords.size() - 1)){
					this.allwords.add(word);
				}else if(this.allwords.get(i).equals(word)){
					break;
				}
			}
		}
		scan.close();
		
		return this.allwords;
	}
	public List<String> getWordsFromOneFile(File file) throws IOException{
		this.allwords = new ArrayList<String>();
		Scanner scan = new Scanner(file,"GBK");
		StringBuffer buf = new StringBuffer();
		String lines = null;
		
		while(scan.hasNextLine()){
			buf.append(scan.nextLine());
		}
		lines = buf.toString();
		String words[] = lines.split("\\s+");
		this.allwords.add(words[0]);
		
		for(String word : words){
			
			for(int i = 0;i<this.allwords.size();i++){
				if(!(this.allwords.get(i).equals(word)) && (i == this.allwords.size() - 1)){
					this.allwords.add(word);
				}else if(this.allwords.get(i).equals(word)){
					break;
				}
			}
		}
		scan.close();
		
		return this.allwords;
	}
	/**
	 * get all files of a directory
	 * @param filePath
	 * @return files list
	 * @throws IOException
	 */
	public List<File> getFiles(String filePath)throws IOException{
		this.allFiles = new ArrayList<File>();
		File[] files = null;
		File dirFiles = new File(filePath);
		if(dirFiles.isDirectory()){
			files = dirFiles.listFiles();
			for(File file : files){
				if(file.isDirectory()){
					getFiles(file.getAbsolutePath());
				} else{
					this.allFiles.add(file);
				}
			}
		}
		
		return this.allFiles;
	}
	
	public List<File> getFileSortByName(String filePath) throws IOException{
		this.allFiles = new ArrayList<File>();
		this.allFiles = getFiles(filePath);

		if(this.allFiles != null && this.allFiles.size() > 0){
			Collections.sort(this.allFiles, new Comparator<File>() {
				public int compare(File file,File newFile){
					int index1 = file.getName().indexOf(".");
					int index2 = newFile.getName().indexOf(".");
					if(Integer.parseInt(file.getName().substring(0, index1)) > 
							Integer.parseInt(newFile.getName().substring(0, index2))){
						return 1;
					} else if(Integer.parseInt(file.getName().substring(0, index1)) == 
							Integer.parseInt(newFile.getName().substring(0, index2))){
						return 0;
					} else{
						return -1;
					}
				}
			});
		}
		
		return this.allFiles;
	}
	
}

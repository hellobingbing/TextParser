package cn.free.textparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class merge {

	/**
	 * 合并几个文本文件中的不同词语
	 */
	public static void main(String[] args) {
		
		String dirName = ".\\test\\Sampless\\train_400\\5000features\\";
		
		//把语料集全部列出来
		File dir = new File(dirName);  
		File files[] = dir.listFiles();
		try{
			//char[] tempFile = new char[1024];
			String tempStr = null;
			String tempString = "";
			
			FileInputStream fis = new FileInputStream(files[0].toString());//读入一个文本
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			//br.read(tempFile);
			while((tempStr = br.readLine())!=null){
				tempString += tempStr;
				
			}
			
			br.close();
			isr.close();
			fis.close();
			System.out.println(tempString);
			String tempFile[] = tempString.split("\\s+");
			//System.out.print(tempFile[0]);
			
			for(int i = 0;i<tempFile.length;i++){
				
				for(int j = 0;j<tempFile.length;j++){
					
					if((i != j) && (tempFile[j].equals(tempFile[i]))){
						
						tempFile[j] = ""; //null #
					}
				}
			}
			//System.out.print(tempFile[7]);
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0;i<tempFile.length;i++){
				
				sb.append(tempFile[i]+" ");
			}
			tempString = sb.toString();
			
			//tempString= new String(tempFile);   //char
			System.out.println(tempString);       //System.out.println(tempFile);
			
			tempFile = tempString.split("\\s+");
						
			for(int i = 1;i < files.length;i++){
				
				
				
				//char newFile[] = new char[1024];
				String newString = "";
				
				//int num = 1000;
				FileInputStream fis1 = new FileInputStream(files[i].toString());//读入下一个文本
				InputStreamReader isr1 = new InputStreamReader(fis1);
				BufferedReader br1 = new BufferedReader(isr1);
				while((tempStr = br1.readLine()) != null){
					newString += tempStr;
				}
				
				
				//newFile = new String[num];
				br1.close();
				isr1.close();
				fis1.close();
				
				String newFile[] = newString.split("\\s+");      //s+
				
				for (int j = 0;j<newFile.length;j++){
					
					tempFile = tempString.split("\\s+");
					for(int k = 0;k<tempFile.length;k++){
					
					boolean isContain=false;
					//int Contains = -1;
					//isContain = tempString.contains(newFile[j]);
					isContain = tempFile[k].equals(newFile[j]);
					if(!isContain && k == tempFile.length - 1){
						
						tempString += newFile[j]+" ";
						
						break;
					}
					
					else if(isContain)
						break;
					
				}
				
			}
				
			}
			
			String destFile = ".\\test\\Sampless\\train_400\\5000features\\CHI5000.txt";
			BufferedWriter destFileBw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(
                    destFile))));
			destFileBw.write(tempString);
			destFileBw.newLine();
			destFileBw.close();
			System.out.println(tempString);
			
		}catch (Exception ex){
			
		}

	}

}

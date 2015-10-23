package cn.free.textparser;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
//import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
public class tfidf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			
			double idf;
			double w_tfidf;
			double tf;
			int word_DF;
			int f_length;
			double fstr_length;
			String path = ".\\test\\Sampless\\test_100\\CHI5000.txt";
			String dirname = ".\\test\\Sampless\\train_400\\400total\\";
			File dir = new File(dirname);
			File file[] = dir.listFiles();
			
			Vector<String> vector = new Vector<String>();
			Vector<Integer> wordDF = new Vector<Integer>();
			
			FileReader reader = new FileReader(new File(path));
			BufferedReader bufferReader = new BufferedReader(reader);
			String hasRead = "";
			String tempString = "";
			String tempString1 = "";
			
			//FileWriter filewriter = new FileWriter(".\\test\\Sampless\\test_100\\testCHIr_4618_5.arff");
			//int w[];
			boolean isContain = false;
			while ((hasRead = bufferReader.readLine()) != null) {
				tempString1 += hasRead;
				    /*
					String info[] = hasRead.split("\\s+");
					//Vector<String> vector = new Vector<String>();
					for (int i = 0; i < info.length; i++) {
						vector.add(info[i]);
					}
					*/
			}
			String str[] = tempString1.split("\\s+");
			for(int i = 0;i<str.length;i++){
				vector.add(str[i]);
			}
			System.out.println("Features:"+vector);
			System.out.println("特征词总数："+vector.size());
			
			//////  DF
				for(int i=0;i<vector.size();i++){
					int w1 = 0;
					for(int j=0;j<file.length;j++){
						//char[] tempFile = new char[1024] ;
						String tempString2 = "";
						FileInputStream fis = new FileInputStream(file[j].toString());
						InputStreamReader isr = new InputStreamReader(fis);
						BufferedReader br = new BufferedReader(isr);
						//br.read(tempFile);
						while((hasRead = br.readLine()) != null){
							tempString2 += hasRead;
						}
						br.close();
						isr.close();
						fis.close();
						
						//tempString= new String(tempFile);
						String tempFile[] = tempString2.split("\\s+");
						//isContain=tempString.contains(features[i]);
						for(int k = 0;k<tempFile.length;k++){
							
							isContain = tempFile[k].equals(vector.get(i));
							if(isContain){
							//w[i]++;
							w1++;
							break;
							}
						}
						
					}
					wordDF.add(i, w1);
					//System.out.println(i);
				}
				System.out.println("DF:"+wordDF);
				//System.out.println("Texts:"+file.length);
			
				/*
				filewriter.write("@relation testCHIr_4618_5 \n\n");
				for(int i = 0;i<vector.size();i++){
					
					filewriter.write("@attribute input"+i+" numeric \n");
				}
				filewriter.write("@attribute class {1,2,3,4,5} \n\n");
				filewriter.write("@data \n\n");
				*/
				
			////// TF and TF-IDF
				double sum1[];
				double sum2[];
				double sum3[];
				double sum4[];
				double sum5[];
				sum1 = new double[vector.size()];
				sum2 = new double[vector.size()];
				sum3 = new double[vector.size()];
				sum4 = new double[vector.size()];
				sum5 = new double[vector.size()];
				Vector <Double> meanvector1 = new Vector <Double>();
				Vector <Double> meanvector2 = new Vector <Double>();
				Vector <Double> meanvector3 = new Vector <Double>();
				Vector <Double> meanvector4 = new Vector <Double>();
				Vector <Double> meanvector5 = new Vector <Double>();
				
			for(int k = 0;k<file.length;k++){
				FileInputStream fis2 = new FileInputStream(file[k]);
				InputStreamReader isr2 = new InputStreamReader(fis2);
			    BufferedReader br2 = new BufferedReader(isr2);
			    String hasread = "";
			    String str1 = "";
			    String filestr[] = null;
			    while ((hasread = br2.readLine()) != null){
			    	str1 += hasread; 
			    	}
			    filestr = str1.split("\\s+");
			    Vector<Integer> wordCount = new Vector<Integer>();//
			    Vector<Double> wordFre = new Vector<Double>();
			    Vector<Double> TFIDF = new Vector<Double>();
			    
			    for (int i = 0; i < vector.size(); i++) {
			    	// 存放每个样本中的词语，在该对比文本中出现的次数
			    	String word = vector.get(i);
				    int w = 0;

				    for(int j = 0;j<filestr.length;j++){
				    	if(filestr[j].equals(word)){
				    		w++;
				    		//
				    		}
				    	//
				    	}
				    fstr_length = filestr.length;
				    tf = w/fstr_length;   // TF

				    wordCount.add(i, w);   // term count
				    wordFre.add(i, tf);   // term frequency
				    f_length = file.length;
				    word_DF = wordDF.get(i);
				    idf = Math.log10(f_length*1.0/(word_DF));   // IDF  分母不能为0 (word_DF + 1)
				    //w_tfidf = w*Math.log10(file.length/wordDF.get(i));
				    w_tfidf = tf*idf;
				    TFIDF.add(i, w_tfidf);   // TFIDF

				    }

			    String wordC = wordCount.toString();
			    String wordF = wordFre.toString();
			    String TFIDF1 = TFIDF.toString();
			    System.out.println("Text"+k);
			    System.out.println("TC:"+wordC.substring(1, wordC.length() - 1));
			    System.out.println("TF:"+wordF.substring(1, wordF.length() - 1));
			    System.out.println("TF-IDF:"+TFIDF1.substring(1, TFIDF1.length() - 1));
			    
			    // 全部文本的特征向量 写成  arff 
			    if(k >= 0 && k <= 79){
			    	//filewriter.write(TFIDF1.substring(1, TFIDF1.length() - 1)+",1"+"\n");			    	
			    	for(int i = 0;i<TFIDF.size();i++){
			    		sum1[i] += TFIDF.get(i);			    		
			    	}			    				    	
			    }			    
			    else if(k >= 80 && k <= 159){
			    	//filewriter.write(TFIDF1.substring(1, TFIDF1.length() - 1)+",2"+"\n");
			    	for(int i = 0;i<TFIDF.size();i++){
			    		sum2[i] += TFIDF.get(i);
			    	}
			    	}
			    else if(k >= 160 && k <= 239){
			    	//filewriter.write(TFIDF1.substring(1, TFIDF1.length() - 1)+",3"+"\n");
			    	for(int i = 0;i<TFIDF.size();i++){
			    		sum3[i] += TFIDF.get(i);
			    	}
			    	}
			    else if(k >= 240 && k <= 319){
			    	//filewriter.write(TFIDF1.substring(1, TFIDF1.length() - 1)+",4"+"\n");
			    	for(int i = 0;i<TFIDF.size();i++){
			    		sum4[i] += TFIDF.get(i);
			    	}
			    	}
			    else if(k >= 320 && k <= 399){
			    	//filewriter.write(TFIDF1.substring(1, TFIDF1.length() - 1)+",5"+"\n");
			    	for(int i = 0;i<TFIDF.size();i++){
			    		sum5[i] += TFIDF.get(i);
			    	}
			    	}
			    
			    }
			
			//  求算术平均值作为类向量
			//  并且添加类标签
			for(int i = 0;i<sum1.length;i++){
				meanvector1.add(i, sum1[i]/80);
				}
			//meanvector1.add(sum1.length, 1.0);
			
			for(int i = 0;i<sum2.length;i++){
				meanvector2.add(i, sum2[i]/80);
				}
			//meanvector2.add(sum2.length, 2.0);
			
			for(int i = 0;i<sum3.length;i++){
				meanvector3.add(i, sum3[i]/80);
				}
			//meanvector3.add(sum3.length, 3.0);
			
			for(int i = 0;i<sum4.length;i++){
				meanvector4.add(i, sum4[i]/80);
				}
			//meanvector4.add(sum4.length, 4.0);
			
			for(int i = 0;i<sum5.length;i++){
				meanvector5.add(i, sum5[i]/80);
				}
			//meanvector5.add(sum5.length, 5.0);
			
			System.out.println("第一类别向量:"+meanvector1);
			System.out.println("第二类别向量:"+meanvector2);
			System.out.println("第三类别向量:"+meanvector3);
			System.out.println("第四类别向量:"+meanvector4);
			System.out.println("第五类别向量:"+meanvector5);
			
			//filewriter.close();
			
			// 读入新文本 all_tfidf [[]]
			Vector<Vector<Double>> all_tfidf = new Vector<Vector<Double>> ();
			for(int i = 0;i<newfiles(vector,wordDF).size();i++){
				
				all_tfidf.add(i, newfiles(vector,wordDF).get(i));
			}
			
			cossimilarity(meanvector1,meanvector2,meanvector3,meanvector4,meanvector5,all_tfidf);
				
		} catch (Exception ex) {
			System.out.println("main");
		}
	}
	//计算待分类的样本数据与各个类中心向量的cos相似性
	public static void cossimilarity(Vector<Double> meanvector1,Vector<Double> meanvector2,Vector<Double> meanvector3,Vector<Double> meanvector4,Vector<Double> meanvector5,Vector<Vector<Double>> all_tfidf){
		
		double modul1 = 0.0; //类别向量的模
		double modul2 = 0.0;
		double modul3 = 0.0;
		double modul4 = 0.0;
		double modul5 = 0.0;
		//double vectorproduct12 = 0.0;
		//double vectorproduct13 = 0.0;
		//double cos12;
		//double cos13;
		int yes = 0;
		int no = 0;
		double precision;
		
		for(int i = 0;i<meanvector1.size();i++){
			
			modul1 += meanvector1.get(i)*meanvector1.get(i);
			modul2 += meanvector2.get(i)*meanvector2.get(i);
			modul3 += meanvector3.get(i)*meanvector3.get(i);
			modul4 += meanvector4.get(i)*meanvector4.get(i);
			modul5 += meanvector5.get(i)*meanvector5.get(i);			
			//vectorproduct12 += meanvector1.get(i)*meanvector2.get(i);
			//vectorproduct13 += meanvector1.get(i)*meanvector3.get(i);
		}
		
		for(int i = 0;i<all_tfidf.size();i++){
			
			double modul_f = 0.0;
			double vectorproduct_1 = 0.0;
			double vectorproduct_2 = 0.0;
			double vectorproduct_3 = 0.0;
			double vectorproduct_4 = 0.0;
			double vectorproduct_5 = 0.0;
			double cos_1;
			double cos_2;
			double cos_3;
			double cos_4;
			double cos_5;
			double cos_max;
			double cos[];
			cos = new double[5];
			///////////////////////////////////////////
			for(int j = 0;j<all_tfidf.get(i).size() - 1;j++){
				
				modul_f += all_tfidf.get(i).get(j)*all_tfidf.get(i).get(j);
				vectorproduct_1 += meanvector1.get(j)*all_tfidf.get(i).get(j);
				vectorproduct_2 += meanvector2.get(j)*all_tfidf.get(i).get(j);
				vectorproduct_3 += meanvector3.get(j)*all_tfidf.get(i).get(j);
				vectorproduct_4 += meanvector4.get(j)*all_tfidf.get(i).get(j);
				vectorproduct_5 += meanvector5.get(j)*all_tfidf.get(i).get(j);
			}
			cos_1 = vectorproduct_1/(Math.sqrt(modul1)*Math.sqrt(modul_f));
			cos_2 = vectorproduct_2/(Math.sqrt(modul2)*Math.sqrt(modul_f));
			cos_3 = vectorproduct_3/(Math.sqrt(modul3)*Math.sqrt(modul_f));
			cos_4 = vectorproduct_4/(Math.sqrt(modul4)*Math.sqrt(modul_f));
			cos_5 = vectorproduct_5/(Math.sqrt(modul5)*Math.sqrt(modul_f));
			
			cos[0] = cos_1;
			cos[1] = cos_2;
			cos[2] = cos_3;
			cos[3] = cos_4;
			cos[4] = cos_5;
			cos_max = cos_1;
			int tag = 1000;
			for(int m = 0;m<cos.length;m++){
				
				if(cos[m] >= cos_max){
					cos_max = cos[m];
					tag = m + 1;
				}
			}
			switch (tag){
				
				case 1:System.out.println("预测为第1类");break;
				case 2:System.out.println("预测为第2类");break;
				case 3:System.out.println("预测为第3类");break;
				case 4:System.out.println("预测为第4类");break;
				case 5:System.out.println("预测为第5类");break;
				default:break;
				}
			// 统计 分类正确 和 错误的个数
			if(tag == all_tfidf.get(i).get(all_tfidf.get(i).size() - 1)){
				
				yes++;
			}
			else 
			{
				no++;
			}
			int lei = (int) all_tfidf.get(i).get(all_tfidf.get(i).size() - 1).doubleValue(); // Double 转化为 double	
			
			System.out.println("cos_max:"+cos_max);
			System.out.println("实际为第"+lei+"类\n");
		}
		System.out.println("Right:"+yes);
		System.out.println("False:"+no);
		precision = yes*1.0/(yes + no);
		System.out.println("precision:"+precision);
		
		//cos12 = vectorproduct12/(Math.sqrt(modul1)*Math.sqrt(modul2));
		//cos13 = vectorproduct13/(Math.sqrt(modul1)*Math.sqrt(modul3));		
		//System.out.println(cos12);
		//System.out.println(cos13);
				
		return ;
	}
	//读入待分类的新数据
	public static Vector<Vector<Double>> newfiles(Vector<String> vector,Vector<Integer> wordDF){
		
		Vector <Vector<Double>> all_tfidf = new Vector <Vector<Double>>();
		try{
			String dirname = ".\\test\\Sampless\\test_100\\100_5\\";
		    File dir = new File(dirname);
		    File file[] = dir.listFiles();
		    double idf;
			double w_tfidf;
			double tf;
			int word_DF;
			int f_length;
			double fstr_length;
	        //Vector <Vector<Double>> all_tfidf = new Vector <Vector<Double>>();
	        
	        for(int k = 0;k<file.length;k++){
				FileInputStream fis3 = new FileInputStream(file[k]);
				InputStreamReader isr3 = new InputStreamReader(fis3);
			    BufferedReader br3 = new BufferedReader(isr3);
			    String hasread = "";
			    String str1 = "";
			    String filestr[] = null;
			    while ((hasread = br3.readLine()) != null){
			    	str1 += hasread; 
			    	}
			    filestr = str1.split("\\s+");
			    Vector<Integer> wordCount = new Vector<Integer>();//
			    Vector<Double> wordFre = new Vector<Double>();
			    Vector<Double> TFIDF = new Vector<Double>();
			    
			    for (int i = 0; i < vector.size(); i++) {
			    	// 存放每个样本中的词语，在该对比文本中出现的次数
			    	String word = vector.get(i);
				    int w = 0;

				    for(int j = 0;j<filestr.length;j++){
				    	if(filestr[j].equals(word)){
				    		w++;
				    		//
				    		}
				    	//
				    	}
				    fstr_length = filestr.length;
				    tf = w/fstr_length;   // TF

				    wordCount.add(i, w);   // term count
				    wordFre.add(i, tf);   // term frequency
				    f_length = file.length;
				    word_DF = wordDF.get(i);
				    idf = Math.log10(f_length*1.0/(word_DF));   // IDF  分母不能为0 (word_DF + 1)
				    //w_tfidf = w*Math.log10(file.length/wordDF.get(i));
				    w_tfidf = tf*idf;
				    TFIDF.add(i, w_tfidf);   // TFIDF
				    }
			    
			    // 添加类标签
			    if(k >= 0 && k <= 19){
			    	TFIDF.add(vector.size(), 1.0);
			    }
			    else if(k >= 20 && k <= 39){
			    	TFIDF.add(vector.size(), 2.0);
			    }
			    else if(k >= 40 && k <= 59){
			    	TFIDF.add(vector.size(), 3.0);
			    }
			    else if(k >= 60 && k <= 79){
			    	TFIDF.add(vector.size(), 4.0);
			    }
			    else if(k >= 80 && k <= 99){
			    	TFIDF.add(vector.size(), 5.0);
			    }
			    
			    			    
			    all_tfidf.add(k, TFIDF);
	        }
	        
	        
	        } catch (Exception ex){
	        	System.out.println("newfiles");
	        	}
		//System.out.println(all_tfidf.size());
		return all_tfidf;
	}
	
}

package cn.free.textparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Vector;
import java.lang.Math;


public class CHI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			int k = 1000;  //取前  k 个特征
			String dirname = ".\\test\\Sampless\\train_400\\CHI_education\\education";
			String dirname1 = ".\\test\\Sampless\\train_400\\CHI_education\\except_education";
			String featurename = ".\\test\\Sampless\\train_400\\CHI_education\\total_education.txt";
			String featureWeightFile = ".\\test\\Sampless\\train_400\\CHI_education\\CHI_education.txt";
			String topFeatureFile = ".\\test\\Sampless\\train_400\\CHI_education\\CHI_top" +k+ "_education.txt";
			
			File dir = new File(dirname); //本类
			File files[] = dir.listFiles();
			
			File dir1 = new File(dirname1);  //外类
			File files1[] = dir1.listFiles();
			String features[];
			//包含的文档数
			//int w[];  // A
			//int w1[];  // B
			//不包含的文档数
			//int ww[];  // C
			//int ww1[]; // D
			
			double value_ka[];  //  卡方值
			
			FileInputStream fis = new FileInputStream(featurename);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String hasread = "";
			String hasread1 = "";
			//hasread = br.readLine();
			while((hasread1 = br.readLine()) != null){
				hasread += hasread1;
			}
			br.close();
			isr.close();
			fis.close();

			features = hasread.split("\\s+");
			value_ka = new double[features.length];
			
			df_count(files,files1,features,value_ka);// count df
			
			FileOutputStream fos = new FileOutputStream(featureWeightFile);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			for(int i=0;i<features.length;i++){
				bw.write(features[i]+" , ");
				bw.write(String.valueOf(value_ka[i]));
				bw.newLine();
			}
			bw.close();
			osw.close();
			fos.close();
			
			//对《特征，权重》进行排序
			top_feature(features,value_ka);
			
			//输出最高的K个《特征，权重》对
			FileOutputStream fos1 = new FileOutputStream(topFeatureFile);
			OutputStreamWriter osw1 = new OutputStreamWriter(fos1);
			BufferedWriter bw1 = new BufferedWriter(osw1);
			for(int i=0;i<k;i++){
				bw1.write(features[i]+"   ");
				bw1.write(String.valueOf(value_ka[i]));
				bw1.newLine();
			}
			bw1.close();
			osw1.close();
			fos1.close();
			
		} catch (Exception e){
			System.out.println("main exception");
		}

	}
	
	public static void df_count(File[] files,File[] files1,String[] features,double value_ka[]){
		boolean isContain=false;
		//包含的文档数
		int w[];  // A
		int w1[];  // B
		//不包含的文档数
		int ww[];  // C
		int ww1[]; // D
		w = new int[features.length];
		w1 = new int[features.length];
		ww = new int[features.length];
		ww1 = new int[features.length];
		//String tempString = null;
		String hasread2 = "";
		try {
		for(int i=0;i<features.length;i++){
			//本类
			for(int j=0;j<files.length;j++){
				//char[] tempFile = new char[1024] ;
				FileInputStream fis1 = new FileInputStream(files[j].toString());
				InputStreamReader isr1 = new InputStreamReader(fis1);
				BufferedReader br1 = new BufferedReader(isr1);
			    //tempString = br1.readLine();
				String tempString = "";
				while((hasread2 = br1.readLine()) != null){
					tempString += hasread2;
				}
				br1.close();
				isr1.close();
				fis1.close();
				String tempFile[] = tempString.split("\\s+");
				//isContain=tempString.contains(features[i]);
				for(int k = 0;k<tempFile.length;k++){
					
					isContain = tempFile[k].equals(features[i]);
					if(isContain){
					w[i]++;
					break;
					}
				}
				
			}
			
			//外类
			for(int m = 0;m < files1.length;m++){
				
				FileInputStream fis2 = new FileInputStream(files1[m].toString());
				InputStreamReader isr2 = new InputStreamReader(fis2);
				BufferedReader br2 = new BufferedReader(isr2);
				String tempString1 = "";
				while((hasread2 = br2.readLine()) != null){
					tempString1 += hasread2;
				}
				br2.close();
				isr2.close();
				fis2.close();
				String tempFile1[] = tempString1.split("\\s+");
				for(int n = 0;n<tempFile1.length;n++){
					
					isContain = tempFile1[n].equals(features[i]);
					if(isContain){
						w1[i]++;
						break;
					}
					
				}
			}
			
			System.out.println(i);
			
			ww[i] = files.length - w[i];
			ww1[i] = files1.length - w1[i];
			// 卡方值的计算
			value_ka[i] = (Math.pow(w[i]*ww1[i] - w1[i]*ww[i], 2))/((w[i] + w1[i])*(ww[i] + ww1[i]));	
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ;
	}
	
	    // 对《特征，权重》进行排序
		public static void top_feature(String[] features, double[] value_ka){
			for (int i = 0; i < value_ka.length - 1; i++) {
				int m = i;
				for (int j = i + 1; j < value_ka.length; j++) {
					if (value_ka[j] > value_ka[m]){
						m = j;
					}
				}
				if (i != m)
					swap(features,value_ka, i, m);  // 交换两个数组中的索引为i、m的元素
			}		
			return ;
		}
		
		 // 交换两个数组中的索引为i、j的元素
		 private static void swap(String[] features, double[] value_ka, int i, int j) {
		  double tempw;
		  String tempfeature;
		  tempw = value_ka[i];
		  tempfeature = features[i];
		  value_ka[i] = value_ka[j];
		  features[i]=features[j];
		  value_ka[j] = tempw;
		  features[j]=tempfeature;
		 } 

}

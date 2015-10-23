package VSM;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;

public class DF {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int featureNum = 10;  //词的数目，也就是没选择前特征数目
		String [] features;  //用来记录每个词
		String featureFile=".\\DF\\total.txt"; //要求先分好词，存放词的文件，每一行一个词
		int [] w ; //用来记录每个词的权重
		int k=3; //取权重最高的前K个feature
		String featureWeighFile=".\\DF\\featureWeight.txt";  //用于保存全部《特征 ，权重》对
		String topFeatureFile=".\\DF\\top" + k + "Feature.txt";  //保存前k个《特征 ，权重》对
		String dirName=".\\DF\\texts\\";  //存放所有语料集的目录地址，如C：//
		
		//初始化w
		w=new int[featureNum];
		//把keywords导进来保存在features数组里面
		features = new String[featureNum];

		//把语料集全部列出来
		File dir = new File(dirName);  
		File[] files = dir.listFiles(); 
		
		try {
			FileInputStream fis2 = new FileInputStream(featureFile);
			InputStreamReader isr2 = new InputStreamReader(fis2);
			BufferedReader br2 = new BufferedReader(isr2);

			String hasread = "";
			while ((hasread = br2.readLine()) != null){
				String info[] = hasread.split(" ");
				for (int i = 0;i < info.length;i++){
					features[i] = info[i];
				}
				
			}
			//for(int i=0;i<featureNum;i++){
				//features[i] = br2.readLine();
			//}
			br2.close();
			isr2.close();
			fis2.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
				
		//使用DF方法进行特征选择
		df_count(files,features,w);	
		
		//输出《特征，权重》对
		try {
			FileOutputStream fos = new FileOutputStream(featureWeighFile);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			for(int i=0;i<featureNum;i++){
				bw.write(features[i]+" , ");
				bw.write(String.valueOf(w[i]));
				bw.newLine();
			}
			bw.close();
			osw.close();
			fos.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
		//对《特征，权重》进行排序
		top_feature(features,w);
		
		//输出最高的K个《特征，权重》对
		try {
			FileOutputStream fos = new FileOutputStream(topFeatureFile);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			for(int i=0;i<k;i++){
				bw.write(features[i]+" , ");
				bw.write(String.valueOf(w[i]));
				bw.newLine();
			}
			bw.close();
			osw.close();
			fos.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
		System.out.println("end DF!!!");

	}
	
	//使用DF方法进行特征选择
	public static void df_count(File[] files,String[] features, int[] w){
		boolean isContain=false;
		int Contains = -1;
		String tempString;
		try {
		for(int i=0;i<features.length;i++){
			for(int j=0;j<files.length;j++){
				char[] tempFile = new char[1024] ;
				FileInputStream fis = new FileInputStream(files[j].toString());
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
				br.read(tempFile);
				
				br.close();
				isr.close();
				fis.close();
				
				tempString= new String(tempFile);
				isContain=tempString.contains(features[i]);
				if(isContain){
					w[i]++;
				}
			}
			System.out.println(i);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ;
	}
	
	//对《特征，权重》进行排序
	public static void top_feature(String[] features, int[] w){
		for (int i = 0; i < w.length - 1; i++) {
			int m = i;
			for (int j = i + 1; j < w.length; j++) {
				if (w[j] > w[m]){
					m = j;
				}
			}
			if (i != m)
				swap(features,w, i, m);  // 交换两个数组中的索引为i、m的元素
		}		
		return ;
	}
	
	// 交换两个数组中的索引为i、j的元素
	 private static void swap(String[] features, int[] w, int i, int j) {
	  int tempw;
	  String tempfeature;
	  tempw = w[i];
	  tempfeature = features[i];
	  w[i] = w[j];
	  features[i]=features[j];
	  w[j] = tempw;
	  features[j]=tempfeature;
	 } 
}

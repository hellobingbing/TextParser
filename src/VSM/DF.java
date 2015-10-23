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
		int featureNum = 10;  //�ʵ���Ŀ��Ҳ����ûѡ��ǰ������Ŀ
		String [] features;  //������¼ÿ����
		String featureFile=".\\DF\\total.txt"; //Ҫ���ȷֺôʣ���Ŵʵ��ļ���ÿһ��һ����
		int [] w ; //������¼ÿ���ʵ�Ȩ��
		int k=3; //ȡȨ����ߵ�ǰK��feature
		String featureWeighFile=".\\DF\\featureWeight.txt";  //���ڱ���ȫ�������� ��Ȩ�ء���
		String topFeatureFile=".\\DF\\top" + k + "Feature.txt";  //����ǰk�������� ��Ȩ�ء���
		String dirName=".\\DF\\texts\\";  //����������ϼ���Ŀ¼��ַ����C��//
		
		//��ʼ��w
		w=new int[featureNum];
		//��keywords������������features��������
		features = new String[featureNum];

		//�����ϼ�ȫ���г���
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
				
		//ʹ��DF������������ѡ��
		df_count(files,features,w);	
		
		//�����������Ȩ�ء���
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
		
		//�ԡ�������Ȩ�ء���������
		top_feature(features,w);
		
		//�����ߵ�K����������Ȩ�ء���
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
	
	//ʹ��DF������������ѡ��
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
	
	//�ԡ�������Ȩ�ء���������
	public static void top_feature(String[] features, int[] w){
		for (int i = 0; i < w.length - 1; i++) {
			int m = i;
			for (int j = i + 1; j < w.length; j++) {
				if (w[j] > w[m]){
					m = j;
				}
			}
			if (i != m)
				swap(features,w, i, m);  // �������������е�����Ϊi��m��Ԫ��
		}		
		return ;
	}
	
	// �������������е�����Ϊi��j��Ԫ��
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

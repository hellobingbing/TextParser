package cn.free.textparser.segandfilter;

//import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

//import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.Scanner;

import cn.free.textparser.segandfilter.ReadFiles;

public class MySegmenter {
	
	
	
	public String evaluate(String context){

		try {
			BasicSegmenter segmenter = BasicSegmenter.getInstance();
			List<String> segmented = segmenter.segmentString(context);
			StringBuffer resultBuf = new StringBuffer();
			
			String[] content_seged_filted = ReadFiles.filterWord(segmented);
			for (String word : content_seged_filted) {
				resultBuf.append(word + " ");
			}
			String result = resultBuf.toString();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "seg failed...";
		}
	/*
	private String textFilter(String ncontext) {
		
		String str=ncontext.replaceAll("<[.[^<]]*>","");
		return str;
		}
	*/
	public static void main (String args[]) throws FileNotFoundException, IOException {
		//String content="<p>����������4��23�յ�������������������ԡ��Ų����ֻ�Ӧ�����(Uber)�������������˿��ṩ�˲��ٱ�������Ҳ��ΪһЩ��ȫ����Ϊ��ڸ�������һλŦԼŮ�Ӿ;�����һ�������ľ���ս�ġ��Ų���֮�á�</p><p>����3��28���������&#8226;������Ҫ��ȥ�������гǣ����õ绰����һ�����Ų�����������û���뵽���ǣ���������7Ӣ��(Լ11����)·�̣������ǿ����á�����֮�á������ݡ�</p><p>����һ·�ϣ�˾��һ�߿���һ�߽��绰����ʱ��ʱ����������ԡ��ü��Σ�������ʮ��Σ�յ�������л���������һ�����������˹����������ϡ��ŵò���Ľ���Ҫ��˾������ͣ������û��Ŀ�ĵأ��㽻Ǯ�������˳���</p><p>�������������飬��ԶԶû�н�����֮�����д�ʼ������Ų����ͷ����ģ���Թ˾��������·����������Ǯ������֮�󣬡��Ų����Ŀͷ���Ա���ų�ŵ���˻���������ȡ��15��Ԫ��</p><p>�������ǵڶ��죬���ǵ�û�յ��Լ���15��Ԫ�˿�����յ������ԡ��Ų�����һ��1.6����Ԫ���˵���������æ��ϵ���Ų�������Ū�壬ԭ������һ�������¼���</p><p>�������Ų��������˱�ʾ�����ž޶��˵�����Ϊ��С��������ֶ���ɵġ����Ų��������ʾ��Ǹ������ŵ�����׳���ȫ���˻���Ȼ������ȴ�������ˣ�����ʾ�������ܲ������á��Ų����ķ����ˡ�</p>";
		//System.out.println(new MySegmenter().evaluate(content));
		segFiles();
	}
	
	public static void segFiles() throws IOException, FileNotFoundException{
		MySegmenter segmenter = new MySegmenter();
		
		//String srcDir = "." + File.separator + "test" + File.separator + "src"; 
  			
		//String desDir = "." + File.separator + "test" + File.separator + "des";
  			
		String srcDir = "." + File.separator + "description" + File.separator +
				"text" + File.separator + "science";
		String desDir = "." + File.separator + "description" + File.separator +
				"seged" + File.separator + "science";
		List<File> filesList = new TextFiles().getFileSortByName(srcDir);
		//File files = new File(srcDir);
		//File srcFiles[] = files.listFiles();
		int index = 1;
		
		for(int i = 0;i<filesList.size();i++){
			StringBuffer sb = null;
			sb = new StringBuffer();
			//BufferedReader srcFileBr = new BufferedReader(
					//new InputStreamReader(new FileInputStream(srcFiles[i]),"GBK"));
			//if(srcFileBr.readLine() != null){
				//sb.append(srcFileBr.readLine());
			//}
			Scanner scan = new Scanner(filesList.get(i),"GBK");
			while(scan.hasNextLine()){
				sb.append(scan.nextLine());
			}
			String result = segmenter.evaluate(sb.toString());
			
			File desFile = new File(desDir + File.separator + index + ".txt");
			BufferedWriter destFileBw = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(desFile)));
			destFileBw.write(result);
			destFileBw.newLine();
			
			index++;
			destFileBw.close();
          //srcFileBr.close();
			scan.close();
		}
		System.out.println("the number of texts is : " + (index - 1));
	}

}

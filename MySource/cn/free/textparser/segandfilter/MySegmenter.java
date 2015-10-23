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
		//String content="<p>　　中新网4月23日电据美国中文网报道，自“优步”手机应用软件(Uber)面世以来，给乘客提供了不少便利，但也因为一些安全隐患为人诟病。最近一位纽约女子就经历了一次让她心惊胆战的“优步”之旅。</p><p>　　3月28日中午杰米&#8226;赫塞尔要赶去曼哈顿中城，她用电话叫了一辆“优步”。可是她没有想到的是，接下来的7英里(约11公里)路程，几乎是可以用“地狱之旅”来形容。</p><p>　　一路上，司机一边开车一边讲电话，还时不时检查语音留言。好几次，更是在十分危险的情况下切换车道，有一次甚至开上了公交车车道上。吓得不轻的杰米要求司机靠边停车，还没到目的地，便交钱提早下了车。</p><p>　　可是这事情，还远远没有结束。之后杰米写邮件到“优步”客服中心，抱怨司机绕了弯路并多收了她钱。几天之后，“优步”的客服人员回信承诺将退还她被多收取的15美元。</p><p>　　可是第二天，她非但没收到自己的15美元退款，反而收到了来自“优步”的一张1.6万美元的账单。杰米连忙联系“优步”，才弄清，原来这是一场乌龙事件。</p><p>　　“优步”发言人表示，这张巨额账单是因为不小心输错数字而造成的。“优步”方面表示道歉，并承诺将杰米车费全款退还。然而杰米却不卖账了，她表示，今后可能不会再用“优步”的服务了。</p>";
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
